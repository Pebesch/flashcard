package ch.fhnw.webfr.flashcard.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import ch.fhnw.webfr.flashcard.domain.Questionnaire;

@Component
public class QuestionnaireRepository {
	
	private static QuestionnaireRepository instance;
	
	private static Log logger = LogFactory.getLog(QuestionnaireRepository.class);
	
	private List<Questionnaire> questionnaires = new ArrayList<Questionnaire>();
	
	private QuestionnaireRepository() {}
	
	public static QuestionnaireRepository getInstance() {
		
		if (instance == null) {
			instance = new QuestionnaireRepository();
		}
		
		return instance;
	}
	
	public Long save(Questionnaire q) {
		
		Long id = Long.valueOf(questionnaires.size());
		q.setId(id);
		questionnaires.add(q);
		
		return id;
	}
	
	public List<Questionnaire> findAll() {
		
		return questionnaires;
	}
	
	/**
	 * Returns the questionnaire with the ID, or null if not found.
	 * 
	 * @param id  The ID
	 * @return  The questionnaire with the ID, or null if not found
	 */
	public Questionnaire findById(Long id) {
		
		Questionnaire q = null;
		
		if(id < questionnaires.size()) {
			
			q = questionnaires.get(id.intValue());
		}
		
		return q;
	}
	
	public void clear() {
		
		questionnaires = new ArrayList<Questionnaire>();
	}
	
	@PostConstruct
	public void initRepoWithTestData() {
		
		save(new Questionnaire("Test Questionnaire 1", 
				"Lorem ipsum dolor sit amet..."));
		
		save(new Questionnaire("Test Questionnaire 2", 
				"Lorem ipsum dolor sit amet..."));
		
		save(new Questionnaire("i18n Test fünf", 
				"Lorem ipsum dolor sit amet..."));
		
		logger.debug("Questionnaires initialized successfully");
	}

}
