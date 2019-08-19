package iducs.springboot.board.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


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
	public String createUser(@Valid User formUser, Model model, @RequestPart MultipartFile files)throws Exception {

        String sourceFileName = files.getOriginalFilename();  //파일명

        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase(); //확장자
        System.out.println(sourceFileNameExtension);
        File destinationFile; 
        String destinationFileName;
        String fileUrl = "D:\\lhjspring\\spring\\springProject\\src\\main\\resources\\static\\uploadFiles\\"; //파일 저장 위치
        
        do { 
            destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;  //파일 명 변경
            destinationFile = new File(fileUrl + destinationFileName); 
        } while (destinationFile.exists()); 
        
        destinationFile.getParentFile().mkdirs();  
        files.transferTo(destinationFile); // 저장
        formUser.setImage(destinationFileName); 
		userService.saveUser(formUser); 
		model.addAttribute("user", formUser);
		return "redirect:/";
	}	
	@GetMapping("")
	public String getUsers(Model model, HttpSession session, @RequestParam(name = "pageNo",defaultValue = "1") int pageNo,@RequestParam(defaultValue="6") int size) { //@PathVariable(value = "pageNo") Long pageNo) {
		List<User> users = userService.getUsersByPage(pageNo,size);
		PageInfo pageinfo = new PageInfo(pageNo,userService.getUsers().size()/size+1);
		pageinfo.setting(2);
		int user= userService.getUsers().size();
		model.addAttribute("user", user);
		model.addAttribute("users", users);
		model.addAttribute("pageinfo", pageinfo);
		return "/users/list";
	}
 
	
	@GetMapping("/{id}")
	public String getUserById(@PathVariable(value = "id") Long id, Model model) {
		User user = userService.getUserById(id);
		model.addAttribute("user", user);
		return "/users/info";
	}
	
	@PutMapping("/{id}")
	public String updateUserById(@PathVariable(value = "id") Long id, @Valid User formUser, Model model, HttpSession session)throws Exception {
		User user = userService.getUserById(id);
		user.setUserPw(formUser.getUserPw());
		user.setName(formUser.getName());
		user.setCompany(formUser.getCompany());
		user.setImage(formUser.getImage());
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
	
	@GetMapping("/find")
	public String UserFindByName(String userId,@RequestParam(defaultValue="1") int pageNo,@RequestParam(defaultValue="5") int size, Model model) {
		List<User> users = userService.getQuestionsByUserId(userId, pageNo, size);
		PageInfo pageinfo = new PageInfo(pageNo,userService.getQuestionsByUserId(userId).size()/size);
		pageinfo.setting(2);
		
		model.addAttribute("users",users);
		model.addAttribute("pageinfo",pageinfo);
		model.addAttribute("usersize",userService.getQuestionsByUserId(userId).size());
		model.addAttribute("userId", userId);
		
		return "/users/findlist";
	}
	
}
