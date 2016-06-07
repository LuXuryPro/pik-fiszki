package pik.JSONDao;

public class DoCourseControlerClientAnswer {
    public DoCourseControlerClientAnswer(int mark) {
        this.mark = mark;
    }

    public int getMark() {

        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    int mark;
    int courseId;
    int questionId;
}
