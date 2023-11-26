package com.taskbuzz.services;

import com.taskbuzz.entities.Priority;

public class WithHighPriority extends PriorityDecorator{

	public WithHighPriority(PriorityImpl priorityLevel) {
		super(priorityLevel);
	}

	@Override
	public Priority getPriority() {
		return Priority.HIGH;
	}
	
}
