package pik.JSONDao;

import java.math.BigInteger;

public class DoCourseControllerClientFicheRequest {
    BigInteger courseId;

    public DoCourseControllerClientFicheRequest(BigInteger courseId, String userId) {
        this.courseId = courseId;
        this.userId = userId;
    }

    public DoCourseControllerClientFicheRequest() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigInteger getCourseId() {
        return courseId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    String userId;
}
