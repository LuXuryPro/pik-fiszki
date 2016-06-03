package dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Michał on 01.06.2016
*/

@Document(collection="course")
@Data
public class CourseInfo {

    @Id
    private BigInteger id;

    private String ownerId;

    @Indexed
    private String name;

    private String description;


    @Override
    public String toString() {
        return "Course: "+id;
    }

}

