package ch.fhnw.webfr.flashcard.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.fhnw.webfr.flashcard.domain.Questionnaire;
import ch.fhnw.webfr.flashcard.persistence.QuestionnaireRepository;


@Controller
@RequestMapping("/questionnaires")
public class QuestionnaireController {
    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String findAll(Model model) {
        model.addAttribute("questionnaires", questionnaireRepository.findAll());
        return "questionnaires/list";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String findById(@PathVariable String id, Model model) {
        if(questionnaireRepository.existsById(id)) {
            Questionnaire questionnaire = questionnaireRepository.findById(id).get();
            model.addAttribute("questionnaire", questionnaire);
            return "questionnaires/show";
        } else {
            return "404";
        }
        
    }

    @RequestMapping(method = RequestMethod.GET, params = {"form"})
    public String form(Model model) {
        model.addAttribute("questionnaire", new Questionnaire());
        return "questionnaires/create";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Questionnaire questionnaire, BindingResult bindingResult, Model model) { 
        if (bindingResult.hasErrors()) {
            model.addAttribute("questionnaire", questionnaire);
            return "questionnaires/create";
        }
        Questionnaire q = questionnaireRepository.save(questionnaire);
        return "redirect:questionnaires/" + q.getId();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String delete(@PathVariable String id) {
        if(questionnaireRepository.existsById(id)) {
            questionnaireRepository.deleteById(id);
            return "redirect:/questionnaires";
        } else {
            return "redirect:404";
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, params = {"edit"})
    public String edit(@PathVariable String id, Model model) {
        if(questionnaireRepository.existsById(id)) {
            model.addAttribute("questionnaire", questionnaireRepository.findById(id).get());
            return "questionnaires/update";
        } else {
            return "404";
        }
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable String id, @Valid Questionnaire questionnaire, BindingResult bindingResult, Model model) {
        if(questionnaireRepository.existsById(id)) {
            if(bindingResult.hasErrors()) {
                //model.addAttribute("questionnaire", questionnaire);
                return "questionnaires/update";
            }
            Questionnaire q = questionnaireRepository.findById(id).get();
            q.setTitle(questionnaire.getTitle());
            q.setDescription(questionnaire.getDescription());
            questionnaireRepository.save(q);
            return "redirect:/questionnaires";
        } else {
            return "redirect:404";
        }
    }
}
