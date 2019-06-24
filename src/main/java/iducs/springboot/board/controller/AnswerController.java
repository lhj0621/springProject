package iducs.springboot.board.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import iducs.springboot.board.domain.Answer;
import iducs.springboot.board.domain.Question;
import iducs.springboot.board.domain.User;
import iducs.springboot.board.service.AnswerService;
import iducs.springboot.board.service.QuestionService;
import iducs.springboot.board.utils.HttpSessionUtils;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
	@Autowired AnswerService answerService; // 의존성 주입(Dependency Injection) @@@@@@
	@Autowired QuestionService questionService;
		
	@PostMapping("")
	public String createAnswer(@PathVariable Long questionId, String contents,HttpSession session) {
		User sessionUser = (User) session.getAttribute("user");
		Question question = questionService.getQuestionById(questionId);
		Answer newAnswer = new Answer(sessionUser, question, contents);
		answerService.saveAnswer(newAnswer);
		return String.format("redirect:/questions/%d", questionId);
	}
	
	@GetMapping("/{id}/update")
	public String questionupdateForm(@PathVariable(value = "id") Long id, HttpSession session, Model model) {
		if(HttpSessionUtils.isEmpty(session, "user")){
			return "redirect:/users/login-form";
		}
		Answer answer = answerService.getAnswerById(id);
		model.addAttribute("answer", answer);
		return "/answer/update";
	}	

	@GetMapping("/{id}/delete")
	public String deleteUserById(@PathVariable(value = "id") Long id, Model model) {
		Answer answer = answerService.getAnswerById(id);
		answerService.deleteAnswer(answer);
		return "redirect:/";
	}

	@PostMapping("/{id}/update2")
	public String updateQuestionById(@PathVariable(value = "id") Long id, String contents, Model model) {
		Answer answer = answerService.getAnswerById(id);
		answer.setContents(contents);
		answerService.updateAnswer(answer);
		return "redirect:/";
	}
}
