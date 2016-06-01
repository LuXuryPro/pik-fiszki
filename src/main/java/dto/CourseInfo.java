package dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Michał on 01.06.2016
*/

@Document(collection="course")
@Data
public class CourseInfo {

    @Id
    private long id;

    @DBRef (db="user")
    private UserInfo owner;

    @Indexed
    private String name;

    private String description;

    @DBRef (db="question")
    private  List<QuestionInfo> questions = new ArrayList<>();

    public CourseInfo()
    {}

    @PersistenceConstructor
    public CourseInfo(long id, String name, String description) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Course: "+id;
    }

}

