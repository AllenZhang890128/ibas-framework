package org.colorcoding.bobas.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.colorcoding.bobas.MyConsts;

/**
 * 配置项类
 * 
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "ConfigurationElement", namespace = MyConsts.NAMESPACE_BOBAS_CONFIGURATION)
@XmlRootElement(name = "ConfigurationElement", namespace = MyConsts.NAMESPACE_BOBAS_CONFIGURATION)
public class ConfigurationElement implements IConfigurationElement {

	public ConfigurationElement() {

	}

	public ConfigurationElement(String key, String value) {
		this.key = key;
		this.value = value;
	}

	private String key = "";

	@Override
	@XmlAttribute(name = "key")
	public String getKey() {
		return this.key;
	}

	@Override
	public void setKey(String key) {
		this.key = key;
	}

	private String value = "";

	@Override
	@XmlAttribute(name = "value")
	public String getValue() {
		return this.value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

}
