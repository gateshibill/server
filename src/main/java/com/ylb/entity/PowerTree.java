package com.ylb.entity;
/**
 * 权限树结构
 * @author Administrator
 *
 */
public class PowerTree {
	private String name;
	private String value;
	private String checked;
	private String disabled;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	@Override
	public String toString() {
		return "PowerTree [name=" + name + ", value=" + value + ", checked=" + checked + ", disabled=" + disabled
				+ ", getName()=" + getName() + ", getValue()=" + getValue() + ", getChecked()=" + getChecked()
				+ ", getDisabled()=" + getDisabled() + "]";
	}
}
