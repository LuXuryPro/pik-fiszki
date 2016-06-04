package pik.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by Michał on 01.06.2016.
 */
@Data
@Document(collection="mark")
public class MarkInfo {
    private BigInteger question_id;

    private Float ef;

    private Integer counter;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date;

    private Integer interval;

}
