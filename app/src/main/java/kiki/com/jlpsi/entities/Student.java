package kiki.com.jlpsi.entities;

import java.io.Serializable;

/**
 * Created by Frederick on 2/6/2015.
 */

public class Student implements Serializable {

	private String id;
	private String name, regNo, picUrl, status, compSerial, assignedBy,
			compDescription, schoolId;
	private boolean assigned;
	private long assignedTime;

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getCompSerial() {
		return compSerial;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAssignedBy() {
		return assignedBy;
	}

	public String getCompDescription() {
		return compDescription;
	}

	public void setCompDescription(String compDescription) {
		this.compDescription = compDescription;
	}

	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}

	public long getAssignedTime() {
		return assignedTime;
	}

	public void setAssignedTime(long assignedTime) {
		this.assignedTime = assignedTime;
	}

	public void setCompSerial(String compSerial) {
		this.compSerial = compSerial;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}
}
