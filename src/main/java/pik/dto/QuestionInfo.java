package pik.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;


/**
 * Created by Michał on 01.06.2016
 */

@Document(collection="question")
@Data
public class QuestionInfo {

    @Id
    private BigInteger id;

    private BigInteger courseId;

    private String question;

    private String answer;


    @Override
    public String toString() {
        return "Question: "+question+"Answer: "+answer;
    }

}

