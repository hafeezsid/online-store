package com.api.swed.estore.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.swed.estore.dao.CategoryDAO;
import com.api.swed.estore.dao.ProductDAO;
import com.api.swed.estore.models.Category;
import com.api.swed.estore.models.Product;

@RestController
@RequestMapping("/manage")
public class ManagementController {

	private static final Logger logger = LoggerFactory.getLogger(ManagementController.class);

	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private CategoryDAO categoryDAO;		


	@RequestMapping("/{id}/product")
	public Product manageProductEdit(@PathVariable int id) {		

		// Product nProduct = new Product();	
			
		return productDAO.get(id);
		
	}
	
	
	@PostMapping("/add/product")
	public Map<String, Boolean> managePostProduct(@Valid @RequestBody Product mProduct) {
			System.out.println("add/product");
		boolean status;
			status=productDAO.add(mProduct);
		
		 Map<String, Boolean> response = new HashMap<String, Boolean>();
		    response.put("Product Add Status", status);
		    return response;
	}

	
	@GetMapping("/product/{id}/activation")
	public String managePostProductActivation(@PathVariable int id) {		
		Product product = productDAO.get(id);
		boolean isActive = product.isActive();
		product.setActive(!isActive);
		productDAO.update(product);		
		return (isActive)? "Product Dectivated Successfully!": "Product Activated Successfully";
	}
			

	@PostMapping("/category")
	public String managePostCategory(@ModelAttribute("category") Category mCategory, HttpServletRequest request) {					
		categoryDAO.add(mCategory);		
		return "redirect:" + request.getHeader("Referer") + "?success=category";
	}
			
	
	
	@ModelAttribute("categories") 
	public List<Category> modelCategories() {
		return categoryDAO.list();
	}
	
	@ModelAttribute("category")
	public Category modelCategory() {
		return new Category();
	}
	
	
}

	
