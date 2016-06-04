package org.colorcoding.bobas.bo;

import org.colorcoding.bobas.data.emBOStatus;
import org.colorcoding.bobas.data.emDocumentStatus;
import org.colorcoding.bobas.data.emYesNo;

/**
 * 单据行
 * 
 * @author Niuren.Zhu
 *
 */
public interface IBODocumentLine extends IBusinessObject, IBOLine, IBOStorageTag {

	/**
	 * 获取-单据编号 主键
	 * 
	 * @return
	 */
	Integer getDocEntry();

	/**
	 * 设置-单据编号 主键
	 * 
	 * @param value
	 */
	void setDocEntry(Integer value);

	/**
	 * 获取-行编号 主键
	 * 
	 * @return
	 */
	Integer getLineId();

	/**
	 * 设置-行编号 主键
	 * 
	 * @param value
	 */
	void setLineId(Integer value);

	/**
	 * 获取-顺序号
	 * 
	 * @return
	 */
	Integer getVisOrder();

	/**
	 * 设置-顺序号
	 * 
	 * @param value
	 */
	void setVisOrder(Integer value);

	/**
	 * 获取-取消
	 * 
	 * @return
	 */
	emYesNo getCanceled();

	/**
	 * 设置-取消
	 * 
	 * @param value
	 */
	void setCanceled(emYesNo value);

	/**
	 * 获取-状态
	 * 
	 * @return
	 */
	emBOStatus getStatus();

	/**
	 * 设置-状态
	 * 
	 * @param value
	 */
	void setStatus(emBOStatus value);

	/**
	 * 获取-单据状态
	 * 
	 * @return
	 */
	emDocumentStatus getLineStatus();

	/**
	 * 设置-单据状态
	 * 
	 * @param value
	 */
	void setLineStatus(emDocumentStatus value);
}
