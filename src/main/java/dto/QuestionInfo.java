package dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * Created by Michał on 01.06.2016
 */

@Document(collection="question")
@Data
public class QuestionInfo {

    @Id
    private long id;

    private String question;

    private String answer;

    public QuestionInfo() {
    }

    @PersistenceConstructor
    public QuestionInfo(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Question: "+question+"Answer: "+answer;
    }

}

