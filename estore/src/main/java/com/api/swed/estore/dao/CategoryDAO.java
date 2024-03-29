package com.api.swed.estore.dao;

import java.util.List;

import com.api.swed.estore.models.Category;

public interface CategoryDAO {

	
	
	Category get(int id);
	List<Category> list();
	Category add(Category category);
	boolean update(Category category);
	boolean delete(Category category);
	
	
}
