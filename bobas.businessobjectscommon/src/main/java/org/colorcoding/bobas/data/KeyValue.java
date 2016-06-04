package org.colorcoding.bobas.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.colorcoding.bobas.MyConsts;

/**
 * 键值
 * 
 * @author Niuren.Zhu
 *
 */
@XmlType(name = "KeyValue", namespace = MyConsts.NAMESPACE_BOBAS_DATA)
@XmlRootElement(name = "KeyValue", namespace = MyConsts.NAMESPACE_BOBAS_DATA)
public class KeyValue {
	public KeyValue() {

	}

	public KeyValue(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * 键
	 */
	@XmlElement(name = "Key")
	public String key;
	/**
	 * 值
	 */
	@XmlElement(name = "Value")
	public Object value;
}
