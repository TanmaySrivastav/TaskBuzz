package com.taskbuzz.services;

public class CategoryFactory {

	public String getCategoryType(String categoryType)
	{
		switch(categoryType.toLowerCase())
		{
		case "grocery": return new Grocery().getCategory(categoryType);
		
		case "assignment": return new Assignment().getCategory(categoryType);
		
		case "fitness": return new Fitness().getCategory(categoryType);
		
		default: return new Others().getCategory(categoryType);
		
		}
			
	}
}
