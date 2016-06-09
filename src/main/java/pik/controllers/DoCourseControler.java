package pik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@Controller
public class DoCourseControler {
    private QuestionsController questionsController;

    @Autowired
    public DoCourseControler(QuestionsController questionsController) {
        this.questionsController = questionsController;
    }

    @RequestMapping("/do-course/{courseID}")
    public String doCourse(@PathVariable(value = "courseID") String courseId, Principal principal, Model model) {
        FacebookHelper facebookHelper = new FacebookHelper(principal);
        model.addAttribute("userId", facebookHelper.getId());
        model.addAttribute("courseId", courseId);
        return "do-course";
    }

    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    public ResponseEntity processAnswer(@RequestBody DoCourseControlerClientAnswer doCourseControlerClientAnswer, Principal principal) {
        FacebookHelper facebookHelper = new FacebookHelper(principal);
        int mark = doCourseControlerClientAnswer.getMark();
        BigInteger questionId = doCourseControlerClientAnswer.getQuestionId();
        BigInteger courseId = doCourseControlerClientAnswer.getCourseId();
        QuestionInfo questionInfo = new QuestionInfo();
        questionInfo.setId(questionId);
        questionInfo.setCourseId(courseId);
        this.questionsController.markQuestion(questionInfo, facebookHelper.getId(), mark);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/getfishe", method = RequestMethod.POST)
    @ResponseBody
    public DoCourseControlerFishe processRequest(@RequestBody DoCourseControllerClientFicheRequest doCourseControllerClientFicheRequest) {
        String userId = doCourseControllerClientFicheRequest.getUserId();
        BigInteger courseId = doCourseControllerClientFicheRequest.getCourseId();
        QuestionInfo questionInfo = null;
        try {
            questionInfo = questionsController.getFirstQuestion(userId, courseId);
        } catch (CourseAccessException e) {
            e.printStackTrace();
        }
        return new DoCourseControlerFishe(questionInfo.getQuestion(),
                questionInfo.getAnswer(),
                courseId,
                questionInfo.getId());
    }
}
