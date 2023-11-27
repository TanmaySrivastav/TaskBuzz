package com.taskbuzz.entities;

public enum Priority {
	LOW(3),
	HIGH(1),
	MEDIUM(2);
	
	private int priorityLevel;
	
	Priority(int priorityLevel) {
		this.priorityLevel = priorityLevel;
	}
	
	public int getPriorityLevel() {
		return priorityLevel;
	}

}