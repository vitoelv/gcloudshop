package com.jcommerce.core.test.case9;

import javax.jdo.annotations.PersistenceAware;
import javax.jdo.annotations.Persistent;

@PersistenceAware
public abstract class BaseModel {
	
	@Persistent
	protected Long longId;

	public Long getLongId() {
		return longId;
	}

	public void setLongId(Long longId) {
		this.longId = longId;
	}
	
	
}
