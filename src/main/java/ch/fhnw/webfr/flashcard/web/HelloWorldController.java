package ch.fhnw.webfr.flashcard.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ch.fhnw.webfr.flashcard.persistence.QuestionnaireRepository;

@Controller
@RequestMapping("/hello")
public class HelloWorldController {
    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @RequestMapping(method = RequestMethod.GET)
    public void helloWorld(@RequestParam("name") String name, HttpServletResponse response, HttpServletRequest request) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.append("<html lang='en'><head><title>Example</title></head><body>");
        writer.append("<h2>Hello " + name + "</h2>");
        writer.append("<p>You have <em>" + questionnaireRepository.count() + "</em> questionnaires in your repo.</p>");
        writer.append("</body></html>");
    }
}
