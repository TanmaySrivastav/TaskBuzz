package com.taskbuzz.services;

import com.taskbuzz.entities.Priority;

public class LowPriorityImpl extends PriorityImpl{

	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}

}
