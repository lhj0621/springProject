package iducs.springboot.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
		// TODO Auto-generated method stub
		return null;
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

}
