package ch.fhnw.webfr.flashcard.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        Questionnaire questionnaire = questionnaireRepository.findById(id).isPresent() ? questionnaireRepository.findById(id).get() : null;
        model.addAttribute("questionnaire", questionnaire);
        return "questionnaires/show";
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
}
