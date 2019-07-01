package iducs.springboot.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import iducs.springboot.board.domain.Answer;
import iducs.springboot.board.domain.Question;
import iducs.springboot.board.entity.AnswerEntity;
import iducs.springboot.board.entity.QuestionEntity;
import iducs.springboot.board.entity.UserEntity;
import iducs.springboot.board.repository.AnswerRepository;

@Service("answerService")
public class AnswerServiceImpl implements AnswerService {
	@Autowired 
	private AnswerRepository repository;
	
	@Override
	public Answer getAnswerById(long id) {
		AnswerEntity entity = repository.findById(id).get();
		Answer answer = entity.buildDomain();
		return answer;
		
	}

	@Override
	public List<Answer> getAnswers() {
		List<AnswerEntity> entities = repository.findAll(new Sort(Sort.Direction.DESC, "createTime"));

		List<Answer> questions = new ArrayList<Answer>();
		for (AnswerEntity entity : entities) {
			Answer answer = entity.buildDomain();
			questions.add(answer);
		}
		return questions;
	}

	@Override
	public void saveAnswer(Answer answer) {
		AnswerEntity entity = new AnswerEntity();
		entity.buildEntity(answer);
		repository.save(entity);
	}

	@Override
	public void updateAnswer(Answer answer) {
		AnswerEntity entity = new AnswerEntity();
		entity.buildEntity(answer);
		repository.save(entity);
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAnswer(Answer answer) {
		// TODO Auto-generated method stub
		AnswerEntity entity = new AnswerEntity();
		entity.buildEntity(answer);
		repository.delete(entity);
	}

	@Override
	public List<Answer> getAnswersByPage(int index, int size) {
		PageRequest pageRequest = PageRequest.of((int) (index - 1), size, new Sort(Sort.Direction.DESC, "id"));
		Page<AnswerEntity> entities = repository.findAll(pageRequest);
		List<Answer> questions = new ArrayList<Answer>();
		for (AnswerEntity entity : entities) {
			Answer answer = entity.buildDomain();
			
			questions.add(answer);
		}
		return questions;
	}

	@Override
	public List<Answer> getAnswers(int pageNo,long questionId) {
		PageRequest pageRequest = PageRequest.of((int) (pageNo - 1), 3, new Sort(Sort.Direction.DESC, "id"));
		Page<AnswerEntity> entities = repository.findByQuestionId(pageRequest,questionId);

		List<Answer> answers = new ArrayList<Answer>();
		for (AnswerEntity entity : entities) {
			Answer answer = entity.buildDomain();
			answers.add(answer);
		}
		return answers;
	}

}
