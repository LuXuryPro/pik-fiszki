package pik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pik.JSONDao.DoCourseControlerClientAnswer;
import pik.JSONDao.DoCourseControlerFishe;
import pik.JSONDao.DoCourseControllerClientFicheRequest;
import pik.dto.QuestionInfo;
import pik.exceptions.CourseAccessException;

import java.math.BigInteger;

@Controller
public class DoCourseControler {
    private QuestionsController questionsController;

    @Autowired
    public DoCourseControler(QuestionsController questionsController) {
        this.questionsController = questionsController;
    }

    @RequestMapping("answer")
    public void processAnswer(@RequestBody DoCourseControlerClientAnswer doCourseControlerClientAnswer) {
        return new DoCourseControlerClientAnswer();
    }
    @RequestMapping("get-fiche")
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
                questionInfo.getCourseId(),
                questionInfo.getId());
    }

}
