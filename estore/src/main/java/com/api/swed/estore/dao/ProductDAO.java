package com.api.swed.estore.dao;

import java.util.List;

import com.api.swed.estore.models.Product;

public interface ProductDAO {

	Product get(int productId);
	List<Product> list();	
	Product add(Product product);
	boolean update(Product product);
	boolean delete(Product product);

	List<Product> getProductsByParam(String param, int count);	
	
	
	// business methods
	List<Product> listActiveProducts();	
	List<Product> listActiveProductsByCategory(int categoryId);
	List<Product> getLatestActiveProducts(int count);
	
}
