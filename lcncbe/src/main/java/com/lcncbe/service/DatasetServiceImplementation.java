package com.lcncbe.service;

import com.lcncbe.model.*;
import com.lcncbe.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class DatasetServiceImplementation implements DatasetService {

	@Autowired
	private DatasetRepository datasetRepo;
	@Autowired
	private DatasetTableRepository tableRepo;
	@Autowired
	private DatasetColumnRepository columnRepo;
	@Autowired
	private DatasetJoinRepository joinRepo;
	@Autowired
	private DatasetConditionRepository conditionRepo;
	@Autowired
	private DatasetParameterRepository paramRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// Standard CRUD Implementations (Simplified for brevity)
	public Dataset saveDataset(Dataset ds) {
		return datasetRepo.save(ds);
	}

	public void deleteDataset(Long id) {
		datasetRepo.deleteById(id);
	}

	public DatasetTable saveTable(DatasetTable t) {
		return tableRepo.save(t);
	}

	public void deleteTable(Long id) {
		tableRepo.deleteById(id);
	}

	public DatasetColumn saveColumn(DatasetColumn c) {
		return columnRepo.save(c);
	}

	public void deleteColumn(Long id) {
		columnRepo.deleteById(id);
	}

	public DatasetJoin saveJoin(DatasetJoin j) {
		return joinRepo.save(j);
	}

	public void deleteJoin(Long id) {
		joinRepo.deleteById(id);
	}

	public DatasetCondition saveCondition(DatasetCondition c) {
		return conditionRepo.save(c);
	}

	public void deleteCondition(Long id) {
		conditionRepo.deleteById(id);
	}

	public DatasetParameter saveParameter(DatasetParameter p) {
		return paramRepo.save(p);
	}

	public void deleteParameter(Long id) {
		paramRepo.deleteById(id);
	}

	// --- DYNAMIC QUERY ENGINE ---

	@Override
	public Object executeDynamicQuery(Long datasetId, String queryType, Map<String, Object> runtimeParams,
			Map<String, Object> bodyData) {
		Dataset ds = datasetRepo.findById(datasetId).orElseThrow();

		switch (queryType.toUpperCase()) {
		case "SELECT":
			return handleSelect(ds, runtimeParams);

		case "INSERT":
			return handleInsert(ds, runtimeParams, bodyData);

		case "UPDATE":
			return handleUpdate(ds, runtimeParams, bodyData);

		case "DELETE":
			return handleDelete(ds, runtimeParams);

		default:
			throw new IllegalArgumentException("Unsupported query type: " + queryType);
		}
	}

	// --- 1. SELECT LOGIC ---
	private List<Map<String, Object>> handleSelect(Dataset ds, Map<String, Object> params) {
		StringBuilder sql = new StringBuilder("SELECT ");
		String cols = ds.getSelectedColumns().stream()
				.map(c -> c.getDatasetTable().getTableAlias() + "." + c.getDataColumn().getColumnName())
				.collect(Collectors.joining(", "));
		sql.append(cols.isEmpty() ? "*" : cols);

		sql.append(buildFromAndJoins(ds));
		
		// Collect parameters in the EXACT order they are appended in the WHERE clause
		List<Object> values = new ArrayList<>();
		sql.append(buildWhereClause(ds, params, values)); 

		return jdbcTemplate.queryForList(sql.toString(), values.toArray());
	}

	// --- 2. INSERT LOGIC ---
	private Map<String, Object> handleInsert(Dataset ds, Map<String, Object> queryParams, Map<String, Object> bodyData) {
		// 1. Identify the target table (usually the base table t1)
		DatasetTable baseTable = ds.getTables().stream()
				.filter(t -> t.getTableOrder() == 1)
				.findFirst()
				.orElseThrow(() -> new RuntimeException("No base table defined for insert"));

		String tableName = baseTable.getDataTable().getTableName();

		// 2. Build Column names and Placeholders from metadata
		List<String> colNames = new ArrayList<>();
		List<String> placeholders = new ArrayList<>();
		List<Object> jdbcValues = new ArrayList<>();

		Map<String, Object> caseInsensitiveData = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		caseInsensitiveData.putAll(bodyData);

		for (DatasetColumn dsCol : ds.getSelectedColumns()) {
			String dbColumnName = dsCol.getDataColumn().getColumnName();

			// Check against the case-insensitive map
			if (caseInsensitiveData.containsKey(dbColumnName)) {
				colNames.add(dbColumnName);
				placeholders.add("?");
				jdbcValues.add(caseInsensitiveData.get(dbColumnName));
			}
		}

		if (colNames.isEmpty()) {
			throw new RuntimeException("No valid columns provided in payload for insert");
		}

		String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", 
				tableName, String.join(", ", colNames), String.join(", ", placeholders));

		int rows = jdbcTemplate.update(sql, jdbcValues.toArray());

		return Map.of("status", "success", "affected_rows", rows);
	}

	// --- 3. UPDATE LOGIC ---
	private Map<String, Object> handleUpdate(Dataset ds, Map<String, Object> queryParams, Map<String, Object> bodyData) {
		// 1. Get the base table and its alias
		DatasetTable base = ds.getTables().stream()
				.filter(t -> t.getTableOrder() == 1)
				.findFirst()
				.orElseThrow();
		
		// 2. Start UPDATE with Alias: "UPDATE Products t1 SET "
		StringBuilder sql = new StringBuilder("UPDATE ")
				.append(base.getDataTable().getTableName())
				.append(" ")
				.append(base.getTableAlias())
				.append(" SET ");
				
		List<Object> jdbcValues = new ArrayList<>();

		// Use a Set to track processed columns and avoid duplicates
		Set<String> processedColumns = new HashSet<>();

		String sets = ds.getSelectedColumns().stream()
			.filter(c -> bodyData.containsKey(c.getDataColumn().getColumnName()))
			.filter(c -> processedColumns.add(c.getDataColumn().getColumnName())) // Only unique cols
			.map(c -> {
				String colName = c.getDataColumn().getColumnName();
				jdbcValues.add(bodyData.get(colName)); 
				// In the SET part, use the alias: t1.column_name = ?
				return base.getTableAlias() + "." + colName + " = ?";
			}).collect(Collectors.joining(", "));
		
		if (sets.isEmpty()) throw new RuntimeException("No data provided for update");
		
		sql.append(sets);

		// 3. WHERE PART 
		sql.append(buildWhereClause(ds, queryParams, jdbcValues));

		int rows = jdbcTemplate.update(sql.toString(), jdbcValues.toArray());
		return Map.of("affected_rows", rows, "status", "success");
	}

	// --- 4. DELETE LOGIC ---
	private Map<String, Object> handleDelete(Dataset ds, Map<String, Object> params) {
		DatasetTable base = ds.getTables().get(0);
		StringBuilder sql = new StringBuilder("DELETE ").append(base.getTableAlias()).append(" FROM ")
				.append(base.getDataTable().getTableName()).append(" ").append(base.getTableAlias());

		List<Object> values = new ArrayList<>();
		sql.append(buildWhereClause(ds, params, values));

		int count = jdbcTemplate.update(sql.toString(), values.toArray());
		return Map.of("affected_rows", count);
	}

	// --- HELPERS ---
	private String buildFromAndJoins(Dataset ds) {
		DatasetTable base = ds.getTables().stream().filter(t -> t.getTableOrder() == 1).findFirst().orElseThrow();
		StringBuilder sb = new StringBuilder(" FROM ").append(base.getDataTable().getTableName()).append(" ")
				.append(base.getTableAlias());
		for (DatasetJoin join : ds.getJoins()) {
			sb.append(" ").append(join.getJoinType()).append(" JOIN ")
					.append(join.getRightDatasetTable().getDataTable().getTableName()).append(" ")
					.append(join.getRightDatasetTable().getTableAlias()).append(" ON ")
					.append(join.getLeftDatasetTable().getTableAlias()).append(".")
					.append(join.getLeftColumn().getColumnName()).append(" = ")
					.append(join.getRightDatasetTable().getTableAlias()).append(".")
					.append(join.getRightColumn().getColumnName());
		}
		return sb.toString();
	}

	// --- DYNAMIC WHERE CLAUSE BUILDER ---
	private String buildWhereClause(Dataset ds, Map<String, Object> params, List<Object> valueCollector) {
		if (ds.getConditions() == null || ds.getConditions().isEmpty()) {
			return "";
		}

		// 1. Sort conditions by order so sequence is respected
		List<DatasetCondition> sortedConditions = ds.getConditions().stream()
			.sorted(Comparator.comparing(c -> c.getConditionOrder() == null ? 0 : c.getConditionOrder()))
			.collect(Collectors.toList());

		StringBuilder sb = new StringBuilder(" WHERE ");

		for (int i = 0; i < sortedConditions.size(); i++) {
			DatasetCondition cond = sortedConditions.get(i);

			// 2. Add Logical Operator (AND/OR) if it's NOT the first condition
			if (i > 0) {
				String logOp = (cond.getLogicalOperator() != null && !cond.getLogicalOperator().trim().isEmpty()) 
								? cond.getLogicalOperator() : "AND";
				sb.append(" ").append(logOp).append(" ");
			}

			// 3. Add Opening Parentheses
			if (cond.getOpenParenCount() != null && cond.getOpenParenCount() > 0) {
				sb.append("(".repeat(cond.getOpenParenCount()));
			}

			// 4. Build the actual condition (t1.col_name >= ?)
			String left = cond.getLeftDatasetTable().getTableAlias() + "." + cond.getLeftColumn().getColumnName();
			
			if ("PARAMETER".equals(cond.getRightOperandType())) {
				sb.append(left).append(" ").append(cond.getOperator()).append(" ?");
				valueCollector.add(params.get(cond.getRightOperandValue()));
			} else {
				// Static VALUE
				sb.append(left).append(" ").append(cond.getOperator()).append(" ").append(cond.getRightOperandValue());
			}

			// 5. Add Closing Parentheses
			if (cond.getCloseParenCount() != null && cond.getCloseParenCount() > 0) {
				sb.append(")".repeat(cond.getCloseParenCount()));
			}
		}

		return sb.toString();
	}
}