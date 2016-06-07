package pik.exceptions;

import java.math.BigInteger;

/**
 * Created by adrian on 07.06.16.
 */
public class CourseAccessException extends AccessException{
    private BigInteger courseId;
    private String userId;

    public CourseAccessException(String message, BigInteger courseId, String userId) {
        this.setMessage(message);
        this.courseId = courseId;
        this.userId = userId;
    }

    public void setCourseId(BigInteger courseId) {
        this.courseId = courseId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigInteger getCourseId() {
        return courseId;
    }

    public String getUserId() {
        return userId;
    }
}