package org.colorcoding.bobas.db;

import org.colorcoding.bobas.common.ConditionOperation;
import org.colorcoding.bobas.common.ConditionRelationship;
import org.colorcoding.bobas.common.ISqlQuery;
import org.colorcoding.bobas.common.SortType;
import org.colorcoding.bobas.common.SqlQuery;
import org.colorcoding.bobas.i18n.i18n;
import org.colorcoding.bobas.mapping.db.DbFieldType;
import org.colorcoding.bobas.util.StringBuilder;

public class SqlScripts implements ISqlScripts {

	@Override
	public ISqlQuery getServerTimeScript() {
		return new SqlQuery("SELECT GETDATE() 'NOW'");
	}

	@Override
	public String getDbObjectSign() {
		return "\"%s\"";
	}

	@Override
	public String getScriptBreakSign() {
		return "";
	}

	@Override
	public String getNullValue() {
		return "NULL";
	}

	@Override
	public String getFieldValueCastType(DbFieldType dbFieldType) {
		String result = "%s";
		if (dbFieldType != null) {
			if (dbFieldType == DbFieldType.db_Alphanumeric) {
				result = "CAST(%s AS NVARCHAR)";
			} else if (dbFieldType == DbFieldType.db_Date) {
				result = "CAST(%s AS DATETIME)";
			} else if (dbFieldType == DbFieldType.db_Numeric) {
				result = "CAST(%s AS INT)";
			} else if (dbFieldType == DbFieldType.db_Decimal) {
				result = "CAST(%s AS NUMERIC)";
			}
		}
		return result;
	}

	@Override
	public DbFieldType getDbFieldType(String dbType) {
		if (dbType.equals("nvarchar")) {
			return DbFieldType.db_Alphanumeric;
		} else if (dbType.equals("int")) {
			return DbFieldType.db_Numeric;
		} else if (dbType.equals("datetime")) {
			return DbFieldType.db_Date;
		} else if (dbType.equals("numeric")) {
			return DbFieldType.db_Decimal;
		}
		return DbFieldType.db_Unknown;
	}

	@Override
	public String getFieldBreakSign() {
		return ", ";
	}

	@Override
	public String getAndSign() {
		return " AND ";
	}

	@Override
	public String getSqlString(ConditionRelationship value) throws SqlScriptsException {
		if (value == ConditionRelationship.cr_AND) {
			return "AND";
		} else if (value == ConditionRelationship.cr_OR) {
			return "OR";
		} else if (value == ConditionRelationship.cr_NONE) {
			return this.getFieldBreakSign();
		}
		throw new SqlScriptsException(i18n.prop("msg_bobas_value_can_not_be_resolved", value.toString()));
	}

	@Override
	public String getSqlString(ConditionOperation value) throws SqlScriptsException {
		return this.getSqlString(value, null);
	}

	@Override
	public String getSqlString(ConditionOperation value, String opValue) throws SqlScriptsException {
		if (value == ConditionOperation.co_CONTAIN) {
			return String.format("LIKE N'%%%s%%'", opValue);
		} else if (value == ConditionOperation.co_NOT_CONTAIN) {
			return String.format("NOT LIKE N'%%%s%%'", opValue);
		} else if (value == ConditionOperation.co_END) {
			return String.format("LIKE N'%%%s'", opValue);
		} else if (value == ConditionOperation.co_START) {
			return String.format("LIKE N'%s%%'", opValue);
		} else if (value == ConditionOperation.co_EQUAL) {
			return "= %s";
		} else if (value == ConditionOperation.co_NOT_EQUAL) {
			return "<> %s";
		} else if (value == ConditionOperation.co_GRATER_EQUAL) {
			return ">= %s";
		} else if (value == ConditionOperation.co_GRATER_THAN) {
			return "> %s";
		} else if (value == ConditionOperation.co_IS_NULL) {
			return "IS NULL";
		} else if (value == ConditionOperation.co_NOT_NULL) {
			return "IS NOT NULL";
		} else if (value == ConditionOperation.co_LESS_EQUAL) {
			return "<= %s";
		} else if (value == ConditionOperation.co_LESS_THAN) {
			return "< %s";
		}
		throw new SqlScriptsException(i18n.prop("msg_bobas_value_can_not_be_resolved", value.toString()));
	}

	@Override
	public String getSqlString(String value) throws SqlScriptsException {
		if (value == null) {
			return this.getNullValue();
		}
		return String.format("N'%s'", value);
	}

	@Override
	public String getSqlString(SortType value) throws SqlScriptsException {
		if (value == SortType.st_Ascending) {
			return "ASC";
		} else if (value == SortType.st_Descending) {
			return "DESC";
		}
		throw new SqlScriptsException(i18n.prop("msg_bobas_value_can_not_be_resolved", value.toString()));
	}

	@Override
	public String groupSelectQuery(String partSelect, String table, String partWhere, String partOrder, int result)
			throws SqlScriptsException {
		StringBuilder stringBuilder = new StringBuilder();
		if (result >= 0) {
			stringBuilder.appendFormat("SELECT TOP %s %s", result, partSelect);
		} else {
			stringBuilder.appendFormat("SELECT %s", partSelect);
		}
		stringBuilder.appendFormat(" FROM %s", table);
		if (partWhere != null && !partWhere.equals("")) {
			stringBuilder.appendFormat(" WHERE %s", partWhere);
		}
		if (partOrder != null && !partOrder.equals("")) {
			stringBuilder.appendFormat(" ORDER BY %s", partOrder);
		}
		return stringBuilder.toString();
	}

	@Override
	public String groupDeleteQuery(String table, String partWhere) throws SqlScriptsException {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.appendFormat("DELETE FROM %s WHERE %s", table, partWhere);
		return stringBuilder.toString();
	}

	@Override
	public String groupUpdateQuery(String table, String partFieldValues, String partWhere) throws SqlScriptsException {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.appendFormat("UPDATE %s SET %s WHERE %s", table, partFieldValues, partWhere);
		return stringBuilder.toString();
	}

	@Override
	public String groupInsertQuery(String table, String partFields, String partValues) throws SqlScriptsException {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.appendFormat("INSERT INTO %s (%s) VALUES (%s)", table, partFields, partValues);
		return stringBuilder.toString();
	}

	@Override
	public String getBOPrimaryKeyQuery(String boCode) throws SqlScriptsException {
		return String.format("SELECT \"AutoKey\" FROM \"CC_SYS_ONNM\" WITH (UPDLOCK) WHERE \"ObjectCode\" = '%s'",
				boCode);
	}

	@Override
	public String getUpdateBOPrimaryKeyScript(String boCode) throws SqlScriptsException {
		return String.format("UPDATE \"CC_SYS_ONNM\" SET \"AutoKey\" = \"AutoKey\" + 1 WHERE \"ObjectCode\" = '%s'",
				boCode);
	}

	@Override
	public String groupMaxValueQuery(String field, String table, String partWhere) throws SqlScriptsException {
		return String.format("SELECT MAX(%s) FROM %s WHERE %s", field, table, partWhere);
	}

	@Override
	public String getBOTransactionNotificationScript(String boCode, String type, int keyCount, String keyNames,
			String keyValues) throws SqlScriptsException {
		return String.format("EXEC \"CC_SP_TRANSACTION_NOTIFICATION\" N'%s', N'%s', %s, N'%s', N'%s'", boCode, type,
				keyCount, keyNames, keyValues);
	}

}
