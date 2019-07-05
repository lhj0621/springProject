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
import org.springframework.web.bind.annotation.RequestParam;

import iducs.springboot.board.domain.Answer;
import iducs.springboot.board.domain.Question;
import iducs.springboot.board.domain.User;
import iducs.springboot.board.service.AnswerService;
import iducs.springboot.board.service.QuestionService;
import iducs.springboot.board.utils.HttpSessionUtils;
import iducs.springboot.board.utils.PageInfo;

@Controller
@RequestMapping("/questions")
public class QuerstionController {
	@Autowired QuestionService questionService; // 의존성 주입(Dependency Injection) :
	@Autowired AnswerService answerService;
	/*
	@GetMapping("")
	public String getAllUsers(Model model, HttpSession session, @RequestParam(defaultValue="1") Long pageNo) {
		List<Question> questions = questionService.getQuestions(pageNo);
		model.addAttribute("questions", questions);
		return "/questions/list";
	}
	*/
	@GetMapping("")
	public String getpage(Model model, HttpSession session, @RequestParam(defaultValue="1") int pageNo,@RequestParam(defaultValue="3") int size ) {

		List<Question> questions = questionService.getQuestionsByPage(pageNo,size);
		PageInfo pageinfo = new PageInfo(pageNo,questionService.getQuestions().size()/size+1);
		pageinfo.setting(2);
		model.addAttribute("questions", questions);
		model.addAttribute("pageinfo", pageinfo);	
		
		System.out.println("총 개시글 수 "+questionService.getQuestions().size());
		System.out.println("시작 페이지 "+pageinfo.getStartPage());
		System.out.println("끝 페이지 "+pageinfo.getEndPage());
		System.out.println("현제 페이지 "+pageinfo.getCurPage());
		System.out.println("첫 번호 "+pageinfo.getStartCut());
		System.out.println("끝 번호 "+pageinfo.getEndCut());
		System.out.println("이전 페이지 여부 "+pageinfo.isPrevPage());
		System.out.println("다음 페이지 여부 "+pageinfo.isNextPage());
		
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
	public String getQuestionById(
			@PathVariable(value = "id") Long id, Model model,
			HttpSession session ,
			@RequestParam(defaultValue="1") int pageNo,
			@RequestParam(defaultValue="3") int size ) {
		if (HttpSessionUtils.isEmpty(session, "user"))
			return "redirect:/users/login-form";
		
		Question question = questionService.getQuestionById(id);
		List<Answer> answers = answerService.getAnswers(pageNo,id);
		
		if (HttpSessionUtils.isSameUser((User) session.getAttribute("user"), question.getWriter())) {
			model.addAttribute("same", question.getWriter());
		}
		PageInfo pageinfo = new PageInfo(pageNo,(question.getAnswers().size()+size-1)/size);
		pageinfo.setting(2);
		
		/*
		 * for(Answer answer : question.getAnswers())
		 * System.out.println(answer.getContents());
		 */
		model.addAttribute("question", question);
		model.addAttribute("answers", answers);
		model.addAttribute("pageinfo", pageinfo);	
		
		System.out.println("총 댓글 수 "+question.getAnswers().size());
		System.out.println("시작 페이지 "+pageinfo.getStartPage());
		System.out.println("끝 페이지 "+pageinfo.getEndPage());
		System.out.println("현제 페이지 "+pageinfo.getCurPage());
		System.out.println("첫 번호 "+pageinfo.getStartCut());
		System.out.println("끝 번호 "+pageinfo.getEndCut());
		System.out.println("이전 페이지 여부 "+pageinfo.isPrevPage());
		System.out.println("다음 페이지 여부 "+pageinfo.isNextPage());
		
		
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
