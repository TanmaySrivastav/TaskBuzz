package com.taskbuzz.entities;

public class PriorityDecorator extends PriorityImpl{

	
	PriorityImpl prioritylevel;
	
	public PriorityDecorator(PriorityImpl priorityLevel) {
		this.prioritylevel = priorityLevel;
	}
	
	public Priority getPriority() {
		return prioritylevel.getPriority();
	}

}
