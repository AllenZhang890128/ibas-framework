package org.colorcoding.bobas.db.pgsql.test;

import org.colorcoding.bobas.db.pgsql.DbAdapter;

import org.colorcoding.bobas.db.DataTable;
import org.colorcoding.bobas.db.DbConnection;
import org.colorcoding.bobas.db.IDbCommand;
import org.colorcoding.bobas.db.IDbConnection;
import org.colorcoding.bobas.db.IDbDataReader;
import junit.framework.TestCase;

/**
 * Unit test for simple Amp.
 */
public class testDbConnect extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public testDbConnect() {
	}

	public void testforDbAdapter() {
		String server = "192.168.3.53:5432";
		String dbName = "ibas_demo";
		String userName = "postgres";
		String userPwd = "1q2w3e";
		String sql = "SELECT * FROM \"AVA_TT_ORDR\" ";
		try {
			DbAdapter dbAdapter = new DbAdapter();
			IDbConnection con = new DbConnection(dbAdapter.createConnection(server, dbName, userName, userPwd, ""));
			// 创建执行语句
			IDbCommand comm = con.createCommand();
			IDbDataReader Idr = comm.executeReader(sql);
			DataTable dTable = new DataTable();
			// dTable.fill(Idr);
			System.out.println(dTable.getRows().size());
			System.out.println(dTable.getColumns().size());
			// for (DataTableColumn dataTableColumn :
			// dTable.getColumns().getColumns()) {
			// System.out.println(dataTableColumn.getColumnName());
			// }
			con.close();
			Idr.close();
		} catch (Exception e) {
			System.out.println(e.toString());

		}
	}
}
