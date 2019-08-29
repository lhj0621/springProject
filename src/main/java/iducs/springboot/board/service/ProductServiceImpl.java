package iducs.springboot.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iducs.springboot.board.domain.Product;
import iducs.springboot.board.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository repository;
	
	@Override
	public Product getProductByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProduct() {
		// TODO Auto-generated method stub
		return null;
	}

}
