package iducs.springboot.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import iducs.springboot.board.domain.Question;
import iducs.springboot.board.domain.User;
import iducs.springboot.board.service.QuestionService;
import iducs.springboot.board.utils.HttpSessionUtils;

@Controller
@RequestMapping("/questions")
public class QuerstionController {
	@Autowired
	QuestionService questionService; // 의존성 주입(Dependency Injection) :
	
	@GetMapping("")
	public String getAllUsers(Model model, HttpSession session, Long pageNo) {
		List<Question> questions = questionService.getQuestions(pageNo);
		model.addAttribute("questions", questionService.getQuestions(pageNo));
		return "/questions/list";
	}
	/*
	@GetMapping("")
	public String getAllUser(Model model, HttpSession session) {
		List<Question> questions = questionService.getQuestions();
		model.addAttribute("questions", questions);
		return "/questions/list";
	}
	*/
	@PostMapping("")
	// public String createUser(Question question, Model model, HttpSession session)
	// {
	public String createUser(String title, String contents, Model model, HttpSession session) {
		User sessionUser = (User) session.getAttribute("user");
		Question newQuestion = new Question(title, sessionUser, contents);
		// Question newQuestion = new Question(question.getTitle(), writer,
		// question.getContents());
		questionService.saveQuestion(newQuestion);
		return "redirect:/questions?pageNo=1"; // get 방식으로 리다이렉션 - Controller를 통해 접근
	}

	@GetMapping("/{id}")
	public String getQuestionById(@PathVariable(value = "id") Long id, Model model, HttpSession session) {
		if (HttpSessionUtils.isEmpty(session, "user"))
			return "redirect:/users/login-form";
		
		Question question = questionService.getQuestionById(id);
		
		if (HttpSessionUtils.isSameUser((User) session.getAttribute("user"), question.getWriter())) {
			model.addAttribute("same", question.getWriter());
		}
		
		
		/*
		 * for(Answer answer : question.getAnswers())
		 * System.out.println(answer.getContents());
		 */
		model.addAttribute("question", question);
		return "/questions/info";
	}

	
	@GetMapping("/{id}/form")
	public String getUpdateForm(@PathVariable(value = "id") Long id, Model model) {
		Question question = questionService.getQuestionById(id);
		model.addAttribute("question", question);
		return "/questions/info";
	}
	
	@GetMapping("/{id}/update")
	public String questionupdateForm(@PathVariable(value = "id") Long id, HttpSession session, Model model) {
		if(HttpSessionUtils.isEmpty(session, "user")){
			return "redirect:/users/login-form";
		}
		User writer = (User) session.getAttribute("user");
		model.addAttribute("writer", writer);
		Question question = questionService.getQuestionById(id);
		model.addAttribute("question", question);
		return "/questions/update";
	}	
	
	@PostMapping("/{id}/update2")
	public String updateQuestionById(@PathVariable(value = "id") Long id,@Valid Question formquestion , String title, String contents, Model model) {
		Question question = questionService.getQuestionById(id);
		question.setTitle(title);
		question.setContents(contents);
		questionService.updateQuestion(question);
		return "redirect:/questions/" + id;
	}

	@GetMapping("/{id}/delete")
	public String deleteQuestionById(@PathVariable(value = "id") Long id, Model model) {
		Question question = questionService.getQuestionById(id);
		questionService.deleteQuestion(question);
		return "redirect:/questions?pageNo=1";
	}
}
