package pik.JSONDao;

import java.math.BigInteger;

public class DoCourseControlerFishe {
    public DoCourseControlerFishe(String face, String back, BigInteger courseId, BigInteger questionId) {
        this.face = face;
        this.back = back;
        this.courseId = courseId;
        this.questionId = questionId;
    }

    public String getFace() {

        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    String face;
    String back;

    public BigInteger getCourseId() {
        return courseId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public BigInteger getQuestionId() {
        return questionId;
    }

    public void setQuestionId(BigInteger questionId) {
        this.questionId = questionId;
    }

    BigInteger courseId;
    BigInteger questionId;
}
