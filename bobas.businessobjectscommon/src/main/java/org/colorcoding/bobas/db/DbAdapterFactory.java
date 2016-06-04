package org.colorcoding.bobas.db;

import java.util.HashMap;

import org.colorcoding.bobas.MyConfiguration;
import org.colorcoding.bobas.configuration.ConfigurableFactory;
import org.colorcoding.bobas.core.BOFactory;
import org.colorcoding.bobas.core.BOFactoryException;
import org.colorcoding.bobas.i18n.i18n;
import org.colorcoding.bobas.messages.RuntimeLog;

/**
 * 数据库适配器工厂 根据配置创建适配器
 */
public class DbAdapterFactory extends ConfigurableFactory {
	private DbAdapterFactory() {

	}

	/**
	 * 默认适配器，配置文件指定
	 */
	volatile private static IDbAdapter defaultDbAdapter = null;
	/**
	 * 可用适配器列表，加载过的
	 */
	volatile private static HashMap<String, IDbAdapter> dbAdapters = null;

	/**
	 * 创建适配器实例
	 * 
	 * @param dbType
	 *            数据库类型（要求小写字母）
	 * @return
	 * @throws BOFactoryException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	protected static IDbAdapter newAdapter(String dbType)
			throws BOFactoryException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		String className = null;
		String packageName = String.format("%s.%s", DbAdapterFactory.class.getPackage().getName(), dbType);
		className = String.format("%s.DbAdapter", packageName);
		Class<?> adapterClass = BOFactory.create().getClass(className);
		if (adapterClass == null) {
			throw new ClassNotFoundException("msg_bobas_not_found_db_adapter");
		}
		IDbAdapter adapter = (IDbAdapter) adapterClass.newInstance();// 数据库适配器
		if (adapter == null) {
			throw new NullPointerException("msg_bobas_not_found_db_adapter");
		}
		RuntimeLog.log(RuntimeLog.MSG_DB_ADAPTER_CREATED, className);
		return adapter;
	}

	/**
	 * 创建适配器
	 */
	public synchronized static IDbAdapter createAdapter() throws DbException {
		if (defaultDbAdapter == null) {
			synchronized (DbAdapterFactory.class) {
				if (defaultDbAdapter == null) {
					// 尝试初始化
					try {
						defaultDbAdapter = newAdapter(MyConfiguration
								.getConfigValue(MyConfiguration.CONFIG_ITEM_DB_TYPE, "MSSQL").toLowerCase());
					} catch (Exception e) {
						throw new DbException(i18n.prop("msg_bobas_create_db_adapter_falid"), e);
					}
					if (defaultDbAdapter == null) {
						// 初始化后仍然无效
						throw new DbException(i18n.prop("msg_bobas_create_db_adapter_falid"));
					}
				}
			}
		}
		return defaultDbAdapter;

	}

	public synchronized static IDbAdapter createAdapter(String type) throws DbException {
		if (type == null || type.equals("")) {
			// 空或没有指定，则返回默认
			return createAdapter();
		}
		String dbType = type.toLowerCase();// 转为小写
		if (dbAdapters == null) {
			synchronized (DbAdapterFactory.class) {
				if (dbAdapters == null) {
					dbAdapters = new HashMap<String, IDbAdapter>();
				}
			}
		}
		if (dbAdapters.containsKey(dbType)) {
			return dbAdapters.get(dbType);
		} else {
			synchronized (DbAdapterFactory.class) {
				try {
					IDbAdapter dbAdapter = newAdapter(dbType);
					if (dbAdapter == null) {
						// 初始化后仍然无效
						throw new DbException(i18n.prop("msg_bobas_create_db_adapter_falid"));
					}
					dbAdapters.put(dbType, dbAdapter);
					return dbAdapter;
				} catch (Exception e) {
					throw new DbException(e);
				}
			}
		}
	}
}
