package pik.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pik.JSONDao.DoCourseControlerClientAnswer;
import pik.JSONDao.DoCourseControlerFishe;
import pik.JSONDao.DoCourseControllerClientFicheRequest;

@Controller
public class DoCourseControler {
    /*
     User answers the question
     */
    @RequestMapping("answer")
    @ResponseBody
    public DoCourseControlerClientAnswer processAnswer(@RequestBody DoCourseControlerClientAnswer doCourseControlerClientAnswer) {
        return new DoCourseControlerClientAnswer();
    }
    @RequestMapping("get-fiche")
    @ResponseBody
    public DoCourseControlerFishe processRequest(@RequestBody DoCourseControllerClientFicheRequest doCourseControllerClientFicheRequest) {
        return new DoCourseControlerFishe();
    }

}
