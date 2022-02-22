package com.fara.userapp.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class OptimisticLockedEntity {

	@Version
	@Column(
			name = "VERSION_NUMBER",
			nullable = false
	)
	private Integer versionNumber;

	public Integer getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Integer versionNumber) {
		this.versionNumber = versionNumber;
	}
}
