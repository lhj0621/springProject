package iducs.springboot.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import iducs.springboot.board.domain.Product;
import iducs.springboot.board.domain.User;
import iducs.springboot.board.service.ProductService;


@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired ProductService productService;
	
	@GetMapping("")
	public String getRroduct(Model model,HttpSession session) {
		List<Product> products = productService.getProduct();
		model.addAttribute("product",products);
		return "/product/list";
	}
	@PostMapping("")
	public String createAnswer(@Valid Product formProduct,Model model) {

		productService.saveProduct(formProduct);
		model.addAttribute("prodcut",formProduct);
		return "redirect:/";
	}
	
	@GetMapping("/{id}")
	public String getProductName(@PathVariable(value = "name") String name, Model model) {
		Product product = productService.getProductByName(name);
		model.addAttribute("product", product);
		return "/users/info";
	}
	@GetMapping("{id}/delete")
	public String deleteProducId(@PathVariable(value = "id") Long id, @Valid Product formProduct, Model model,HttpSession session) {
	
		return "redirect:/";
	}
}
