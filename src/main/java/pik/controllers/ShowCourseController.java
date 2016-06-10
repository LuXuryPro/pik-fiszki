package pik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pik.JSONDao.DoCourseControlerClientAnswer;
import pik.JSONDao.DoCourseControlerFishe;
import pik.JSONDao.DoCourseControllerClientFicheRequest;
import pik.Util.FacebookHelper;
import pik.dto.QuestionInfo;
import pik.exceptions.CourseAccessException;

import java.math.BigInteger;
import java.security.Principal;
import java.util.List;

@Controller
public class ShowCourseController
{
    private QuestionsController questionsController;
    private CourseController courseController;

    @Autowired
    public ShowCourseController(QuestionsController questionsController, CourseController courseController) {
        this.questionsController = questionsController;
        this.courseController = courseController;
    }

    @RequestMapping("/show-course/{courseID}")
    public String showCourseInfo(@PathVariable(value = "courseID") BigInteger courseId, Principal principal, Model model) {
        FacebookHelper facebookHelper = new FacebookHelper(principal);
        try {
            List<QuestionInfo> questions = questionsController.getAllQuestions(facebookHelper.getId(), courseId);
            model.addAttribute("questions", questions);
        } catch (Exception e) {
        }
        String courseName = courseController.getCourseNameById(courseId);
        String courseDescription = courseController.getCourseDescriptionById(courseId);
        model.addAttribute("userId", facebookHelper.getId());
        model.addAttribute("courseId", courseId);
        model.addAttribute("courseName", courseName);
        model.addAttribute("courseDescription", courseDescription);
        return "coursePage";
    }


        @RequestMapping(value = "/add-question")
        public String doQuestion(Model model, Principal principal, @RequestParam("id") BigInteger id ,
            @RequestParam("question") String question, @RequestParam("answer") String answer)
        {
            FacebookHelper f = new FacebookHelper(principal);
            try {
                questionsController.addQuestion(answer, question, id, f.getId());
            }catch(Exception e){}

            return "redirect:/show-course/" + id;
        }
}