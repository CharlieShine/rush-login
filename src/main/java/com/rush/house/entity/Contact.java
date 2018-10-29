package com.rush.house.entity;

import java.util.Date;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author licl
 * @since 2018-10-28
 */
public class Contact {

    private static final long serialVersionUID = 1L;

	private Long id;
	private Date insertTime;
	private Date updateTime;
	private String mobile;
	private String name;
	private String remark;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "Contact{" +
			", id=" + id +
			", insertTime=" + insertTime +
			", updateTime=" + updateTime +
			", mobile=" + mobile +
			", name=" + name +
			", remark=" + remark +
			"}";
	}
}
