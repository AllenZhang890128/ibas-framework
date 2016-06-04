package org.colorcoding.bobas.core;

import java.io.Serializable;

import org.colorcoding.bobas.common.ICriteria;

/**
 * 业务对象基础
 */
public interface IBusinessObjectBase extends ITrackStatus, Cloneable, Serializable {
	/**
	 * 获取对象查询条件
	 * 
	 * @return 当前的查询条件
	 */
	ICriteria getCriteria();

	/**
	 * 转换为字符串
	 * 
	 * @return
	 */
	@Override
	String toString();

	/**
	 * 转换为字符串
	 * 
	 * @param type
	 *            类型
	 * @return
	 */
	String toString(String type);

	/**
	 * 获取格式
	 * 
	 * @param type
	 *            类型
	 * @return
	 */
	String getSchema(String type);
}
