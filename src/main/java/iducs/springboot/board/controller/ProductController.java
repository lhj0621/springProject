package iducs.springboot.board.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import iducs.springboot.board.domain.Product;
import iducs.springboot.board.service.ProductService;


@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired ProductService productService;
	
	@GetMapping("")
	public String getpage(Model model) {


		return "/product/list";
	}
	@PostMapping("")
	public String createAnswer(@Valid Product formProduct,Model model) {

		productService.saveProduct(formProduct);
		model.addAttribute("prodcut",formProduct);
		return "redirect:/";
	}
}
