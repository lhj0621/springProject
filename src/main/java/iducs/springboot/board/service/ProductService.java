package iducs.springboot.board.service;

import java.util.List;

import iducs.springboot.board.domain.Product;

public interface ProductService {
	Product getProductByName(String name);
	List<Product> getProduct();
}
