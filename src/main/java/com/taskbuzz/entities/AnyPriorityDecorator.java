package com.taskbuzz.entities;

public class AnyPriorityDecorator extends PriorityDecorator{

	public AnyPriorityDecorator(PriorityImpl priorityLevel) {
		super(priorityLevel);
	}

	@Override
	public Priority getPriority() {
		return super.getPriority();
	}
	
}
