package org.colorcoding.bobas.db.mysql;

import org.colorcoding.bobas.common.ISqlQuery;
import org.colorcoding.bobas.common.SqlQuery;
import org.colorcoding.bobas.db.SqlScriptsException;
import org.colorcoding.bobas.mapping.db.DbFieldType;
import org.colorcoding.bobas.util.StringBuilder;

public class SqlScripts extends org.colorcoding.bobas.db.SqlScripts {

	/**
	 * 无参构造函数
	 */
	public SqlScripts() {

	}

	@Override
	public String getDbObjectSign() {
		return "`%s`";
	}

	/**
	 * 获取当前日期时间
	 */
	@Override
	public ISqlQuery getServerTimeScript() {
		return new SqlQuery("now()");
	}

	/**
	 * 将字段转换成条件字段的类型
	 */
	@Override
	public String getFieldValueCastType(DbFieldType dbFieldType) {
		String result = "%s";
		if (dbFieldType != null) {
			if (dbFieldType == DbFieldType.db_Alphanumeric) {
				result = "CAST(%s AS CHAR)";
			} else if (dbFieldType == DbFieldType.db_Date) {
				result = "CAST(%s AS DATETIME)";
			} else if (dbFieldType == DbFieldType.db_Numeric) {
				result = "CAST(%s AS SIGNED)";
			} else if (dbFieldType == DbFieldType.db_Decimal) {
				result = "CAST(%s AS DECIMAL)";
			}
		}
		return result;
	}

	@Override
	public DbFieldType getDbFieldType(String dbType) {
		if (dbType.equals("varchar")) {
			return DbFieldType.db_Alphanumeric;
		} else if (dbType.equals("int")) {
			return DbFieldType.db_Numeric;
		} else if (dbType.equals("datetime")) {
			return DbFieldType.db_Date;
		} else if (dbType.equals("decimal")) {
			return DbFieldType.db_Decimal;
		}
		return DbFieldType.db_Unknown;
	}

	/**
	 * 获取特定行数的数据
	 */
	@Override
	public String groupSelectQuery(String partSelect, String table, String partWhere, String partOrder, int result)
			throws SqlScriptsException {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.appendFormat("SELECT %s FROM %s", partSelect, table);
		if (partWhere != null && !partWhere.equals("")) {
			stringBuilder.appendFormat(" WHERE %s", partWhere);
		}
		if (partOrder != null && !partOrder.equals("")) {
			stringBuilder.appendFormat(" ORDER BY %s", partOrder);
		}
		if (result >= 0) {
			stringBuilder.appendFormat(" LIMIT %s", result);
		}
		return stringBuilder.toString();
	}

	/**
	 * 获得主键
	 */
	@Override
	public String getBOPrimaryKeyQuery(String boCode) throws SqlScriptsException {
		return String.format("SELECT `AutoKey` FROM `AVA_SYS_ONNM` WHERE `ObjectCode` = '%s' FOR UPDATE", boCode);
	}

	/**
	 * 更新主键
	 */
	@Override
	public String getUpdateBOPrimaryKeyScript(String boCode) throws SqlScriptsException {
		return String.format("UPDATE `AVA_SYS_ONNM` SET `AutoKey` = `AutoKey` + 1 WHERE `ObjectCode` = '%s'", boCode);
	}

	/**
	 * 获取某一列的最大值，如果为空返回1
	 */
	@Override
	public String groupMaxValueQuery(String field, String table, String partWhere) throws SqlScriptsException {
		return String.format("SELECT IFNULL(MAX(%s),0) FROM %s WHERE %s", field, table, partWhere);
	}

	@Override
	public String getBOTransactionNotificationScript(String boCode, String type, int keyCount, String keyNames,
			String keyValues) throws SqlScriptsException {
		return String.format("CALL `AVA_SP_TRANSACTION_NOTIFICATION`( N'%s', N'%s', %s, N'%s', N'%s')", boCode, type,
				keyCount, keyNames, keyValues);
	}

}
