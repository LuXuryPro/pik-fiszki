package pik.JSONDao;

import java.math.BigInteger;

public class DoCourseControlerClientAnswer {
    public DoCourseControlerClientAnswer(int mark) {
        this.mark = mark;
    }

    public DoCourseControlerClientAnswer() {
    }

    public int getMark() {

        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    int mark;
    BigInteger courseId;

    public DoCourseControlerClientAnswer(BigInteger courseId, BigInteger questionId, int mark) {
        this.courseId = courseId;
        this.questionId = questionId;
        this.mark = mark;
    }

    public BigInteger getQuestionId() {

        return questionId;
    }

    public void setQuestionId(BigInteger questionId) {
        this.questionId = questionId;
    }

    public BigInteger getCourseId() {
        return courseId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    BigInteger questionId;
}
