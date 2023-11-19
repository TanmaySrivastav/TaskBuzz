package com.taskbuzz.services;

import com.taskbuzz.entities.Priority;

public class HighPriorityImpl extends PriorityImpl{

	@Override
	public Priority getPriority() {
		return Priority.HIGH;
	}

}
