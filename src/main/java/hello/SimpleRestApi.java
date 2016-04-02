package hello;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

class Fiszka {
    public long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;
    private String question;
    private String answer;
    public Fiszka() {
    }

    public Fiszka(long id, String question, String answer) {
        this.id = id;
        this.question = question;
        this.answer = answer;
    }
}

@RestController
public class SimpleRestApi {
    private final AtomicLong counter = new AtomicLong();


    @RequestMapping("/getall")
    public @ResponseBody  Fiszka getall() {
        return new Fiszka(counter.incrementAndGet(),"Tak", "Nie");
    }
}
