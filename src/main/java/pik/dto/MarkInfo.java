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

    public BigInteger getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(BigInteger question_id) {
        this.question_id = question_id;
    }

    public Float getEf() {
        return ef;
    }

    public void setEf(Float ef) {
        this.ef = ef;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }
}
