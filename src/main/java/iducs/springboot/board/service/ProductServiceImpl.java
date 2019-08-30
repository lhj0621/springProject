package iducs.springboot.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Product> getProduct() {
		List<Product> products = new ArrayList<Product>();
		List<ProductEntity> entities = repository.findAll();
		for (ProductEntity entity : entities) {
			Product product = entity.buildDomain();
			products.add(product);
		}
		return products;
	}

}
