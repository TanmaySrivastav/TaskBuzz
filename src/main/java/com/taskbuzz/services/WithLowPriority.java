package com.taskbuzz.services;

import com.taskbuzz.entities.Priority;

public class WithLowPriority extends PriorityDecorator{

	public WithLowPriority(PriorityImpl priorityLevel) {
		super(priorityLevel);
	}

	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}
	
}

