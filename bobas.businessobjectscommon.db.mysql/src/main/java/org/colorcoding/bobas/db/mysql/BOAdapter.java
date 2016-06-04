package org.colorcoding.bobas.db.mysql;

import org.colorcoding.bobas.db.BOAdapter4Db;
import org.colorcoding.bobas.db.IBOAdapter4Db;
import org.colorcoding.bobas.db.ISqlScripts;

public class BOAdapter extends BOAdapter4Db implements IBOAdapter4Db {

	private ISqlScripts sqlScripts = null;

	@Override
	protected ISqlScripts getSqlScripts() {
		if (this.sqlScripts == null) {
			this.sqlScripts = new SqlScripts();
		}
		return this.sqlScripts;
	}

}
