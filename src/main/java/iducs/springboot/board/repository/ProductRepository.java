package iducs.springboot.board.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import iducs.springboot.board.entity.ProductEntity;


public interface ProductRepository 
	extends JpaRepository<ProductEntity, Long> {	
	ProductEntity findByName(String name);
	ProductEntity findById(long id);
}
