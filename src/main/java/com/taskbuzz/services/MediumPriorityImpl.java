package com.taskbuzz.services;

import com.taskbuzz.entities.Priority;

public class MediumPriorityImpl extends PriorityImpl{

	@Override
	public Priority getPriority() {
		return Priority.MEDIUM;
	}

}
