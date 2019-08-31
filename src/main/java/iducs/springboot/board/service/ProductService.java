package iducs.springboot.board.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import iducs.springboot.board.domain.Product;

public interface ProductService {
	Product getProductByName(String name);
	List<Product> getProduct();
	List<Product> getProduct(PageRequest pageRequest);
}
