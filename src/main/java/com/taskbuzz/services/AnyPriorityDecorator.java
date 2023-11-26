package com.taskbuzz.services;

import com.taskbuzz.entities.Priority;

public class AnyPriorityDecorator extends PriorityDecorator{

	public AnyPriorityDecorator(PriorityImpl priorityLevel) {
		super(priorityLevel);
	}

	@Override
	public Priority getPriority() {
		return super.getPriority();
	}
	
}
