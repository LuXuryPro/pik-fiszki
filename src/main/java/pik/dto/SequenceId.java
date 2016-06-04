package pik.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

/*
 db.sequence.insert({_id: "course",seq: 0})
 */
@Document(collection = "sequence")
public class SequenceId {

    @Id
    private String id;

    private BigInteger seq;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigInteger getSeq() {
        return seq;
    }

    public void setSeq(BigInteger seq) {
        this.seq = seq;
    }

    @Override
    public String toString() {
        return "SequenceId [id=" + id + ", seq=" + seq + "]";
    }

}
