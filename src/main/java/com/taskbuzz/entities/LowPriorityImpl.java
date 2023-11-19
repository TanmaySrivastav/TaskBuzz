package com.taskbuzz.entities;

public class LowPriorityImpl extends PriorityImpl{

	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}

}
