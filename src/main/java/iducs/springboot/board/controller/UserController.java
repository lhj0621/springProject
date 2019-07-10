package iducs.springboot.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import iducs.springboot.board.domain.Question;
import iducs.springboot.board.domain.User;
import iducs.springboot.board.service.UserService;
import iducs.springboot.board.utils.PageInfo;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired UserService userService; 
	// 의존성 주입(Dependency Injection)
	// @Component, @Controller, @Repository, @Service 표시된 클래스형 빈 객체를 스프링이 스캔하여 등록하고, @Autowired 등 요청시 주입 	
	
	@PostMapping("")
	public String createUser(@Valid User formUser, Model model) {
		userService.saveUser(formUser); 
		model.addAttribute("user", formUser);
		return "redirect:/";
	}	
	@GetMapping("")
	public String getUsers(Model model, HttpSession session, @RequestParam(name = "pageNo",defaultValue = "1") int pageNo,@RequestParam(defaultValue="8") int size) { //@PathVariable(value = "pageNo") Long pageNo) {
		List<User> users = userService.getUsersByPage(pageNo,size);
		PageInfo pageinfo = new PageInfo(pageNo,userService.getUsers().size()/size+1);
		pageinfo.setting(2);
		int user= userService.getUsers().size();
		model.addAttribute("user", user);
		model.addAttribute("users", users);
		model.addAttribute("pageinfo", pageinfo);
		return "/users/list";
	}
	/*
	@GetMapping("")
	public String getUsers(Model model, HttpSession session, @RequestParam(name = "pageNo",defaultValue = "1") Long pageNo) { //@PathVariable(value = "pageNo") Long pageNo) {
		System.out.println(pageNo);
		model.addAttribute("users", userService.getUsers(pageNo));
		return "/users/list";
	}
	*/	
	@GetMapping("/{id}")
	public String getUserById(@PathVariable(value = "id") Long id, Model model) {
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		return "/users/info";
	}
	
	@PutMapping("/{id}")
	public String updateUserById(@PathVariable(value = "id") Long id, @Valid User formUser, Model model, HttpSession session) {
		User user = userService.getUserById(id);
		user.setUserPw(formUser.getUserPw());
		user.setName(formUser.getName());
		user.setCompany(formUser.getCompany());
		userService.updateUser(user);		
		model.addAttribute("user", user);
		session.setAttribute("user", user);
		return "/users/info";
	}	
	@GetMapping("{id}/delete")
	public String deleteUserById(@PathVariable(value = "id") Long id, @Valid User formUser, Model model,HttpSession session) {
		userService.deleteUser(formUser);
		session.removeAttribute("user");
		return "redirect:/";
	}
	
	/*
	@PatchMapping("/users/{id}")
	//@RequestBody 사용하는 경우 
	public ResponseEntity<User> patchUserById(@PathVariable(value = "id") Long userId, @Valid  User userDetails, Model model)
			throws ResourceNotFoundException {
		// orElseThrow() : 해당 값이 존재하는 경우 반환하고, 없는 경우 제공자(supplier)에 의해 제공되는 예외 반환
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		user.setName(userDetails.getName());
		//user.setCompany(userDetails.getCompany());
		User userUpdate = userRepo.save(user);
		return ResponseEntity.ok(userUpdate);
		// 
		//return ResponseEntity.ok().body(user);
	}

	@GetMapping("/users/n")
	public String getEmployeeByName(@Param(value = "name") String name, Model model)
			throws ResourceNotFoundException {
		List<User> users = userRepo.findByNameOrderByIdAsc(name);
		model.addAttribute("users", users);
		return "user-list";
	}
	@GetMapping("/users/c")
	public String getUserByCompany(@Param(value = "company") String company, Model model)
			throws ResourceNotFoundException {
		List<User> users = userRepo.findByCompany(company);
		model.addAttribute("users", users);
		return "user-list";
	}
	*/
}
