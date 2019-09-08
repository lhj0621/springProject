package iducs.springboot.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import iducs.springboot.board.domain.Product;
import iducs.springboot.board.entity.ProductEntity;
import iducs.springboot.board.exception.ResourceNotFoundException;
import iducs.springboot.board.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository repository;

	@Override
	public Product getProductByName(String name) {
		ProductEntity productEntity = null;
		try {
			productEntity = repository.findByName(name);
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productEntity.buildDomain();
	}
	
	@Override
	public Product getProductById(long id) {
		ProductEntity productEntity = null;
		try {
			productEntity = repository.findById(id);
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productEntity.buildDomain();
	}
	
	@Override
	public List<Product> getProduct() {
		List<Product> products = new ArrayList<Product>();
		List<ProductEntity> entities = repository.findAll();
		for (ProductEntity entity : entities) {
			Product product = entity.buildDomain();
			products.add(product);
		}
		return products;
	}

	@Override
	public List<Product> getProduct(PageRequest pageRequest) {
		List<Product> products = new ArrayList<Product>();
		Page<ProductEntity> entities = repository.findAll(pageRequest);
		for (ProductEntity entity : entities) {
			Product product = entity.buildDomain();
			products.add(product);
		}
		return products;
	}

	@Override
	public void saveProduct(Product product) {
		ProductEntity entity = new ProductEntity();
		entity.buildEntity(product);
		repository.save(entity);
		
	}

	
	

}
