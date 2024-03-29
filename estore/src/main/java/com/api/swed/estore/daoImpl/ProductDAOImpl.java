package com.api.swed.estore.daoImpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.api.swed.estore.dao.ProductDAO;
import com.api.swed.estore.models.Product;


@Repository("productDAO")
@Transactional
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	/*
	 * SINGLE
	 * */
	
	@Override
	public Product get(long productId) {
		Product p=null;
		try {			
			p=(Product) sessionFactory
					.getCurrentSession()
						.get(Product.class,Long.valueOf(productId));			
		}
		catch(Exception ex) {		
			ex.printStackTrace();			
		}
		return p;
	}

	/*
	 * LIST
	 * */
	
	@Override
	public List<Product> list() {
		return sessionFactory
				.getCurrentSession()
					.createQuery("FROM Product" , Product.class)
						.getResultList();
	}

	/*
	 * INSERT
	 * */
	@Override
	public Product add(Product product) {
		try {			
			sessionFactory
					.getCurrentSession()
						.persist(product);
		
			return product;
		}
		catch(Exception ex) {		
			ex.printStackTrace();			
		}		
		return product;
	}

	/*
	 * UPDATE
	 * */
	@Override
	public boolean update(Product product) {
		try {			
			sessionFactory
					.getCurrentSession()
						.update(product);
			return true;
		}
		catch(Exception ex) {		
			ex.printStackTrace();			
		}		
		return false;		
	}

	
	/*
	 * DELETE
	 * */
	@Override
	public boolean delete(Product product) {
		try {
			
			product.setActive(false);
			// call the update method
			return this.update(product);
		}
		catch(Exception ex) {		
			ex.printStackTrace();			
		}		
		return false;			
	}

	@Override
	public List<Product> listActiveProducts() {
		String selectActiveProducts = "FROM Product WHERE active = :active";
		return sessionFactory
				.getCurrentSession()
					.createQuery(selectActiveProducts, Product.class)
						.setParameter("active", true)
							.getResultList();
	}

	@Override
	public List<Product> listActiveProductsByCategory(int categoryId) {
		String selectActiveProductsByCategory = "FROM Product WHERE active = :active AND categoryId = :categoryId";
		return sessionFactory
				.getCurrentSession()
					.createQuery(selectActiveProductsByCategory, Product.class)
						.setParameter("active", true)
						.setParameter("categoryId",categoryId)
							.getResultList();
	}

	@Override
	public List<Product> getLatestActiveProducts(int count) {
		return sessionFactory
				.getCurrentSession()
					.createQuery("FROM Product WHERE active = :active ORDER BY id", Product.class)
						.setParameter("active", true)
							.setFirstResult(0)
							.setMaxResults(count)
								.getResultList();					
	}

	@Override
	public List<Product> getProductsByParam(String param, int count) {
		
		String query = "FROM Product WHERE active = true ORDER BY " + param + " DESC";
		
		return sessionFactory
					.getCurrentSession()
					.createQuery(query,Product.class)
					.setFirstResult(0)
					.setMaxResults(count)
					.getResultList();
					
		
	}

}
