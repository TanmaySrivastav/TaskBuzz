package com.taskbuzz.services;

import com.taskbuzz.entities.Priority;

public class PriorityDecorator extends PriorityImpl{

	
	private PriorityImpl prioritylevel;
	
	public PriorityDecorator(PriorityImpl priorityLevel) {
		this.prioritylevel = priorityLevel;
	}
	
	public Priority getPriority() {
		return prioritylevel.getPriority();
	}

}
