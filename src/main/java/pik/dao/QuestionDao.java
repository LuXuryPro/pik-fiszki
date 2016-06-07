package pik.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pik.dto.CourseInfo;
import pik.dto.QuestionInfo;
import pik.dto.UserInfo;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by Michał on 04.06.2016.
 */
public interface QuestionDao {
    QuestionInfo add(QuestionInfo questionInfo);
    QuestionInfo update(QuestionInfo questionInfo);
    Boolean remove(QuestionInfo questionInfo);
    List<QuestionInfo> getCourseQuestions(BigInteger courseId);
    Page<QuestionInfo> getQuestionToAnswer(UserInfo user, CourseInfo course, Pageable page);
    Long countQuestions(BigInteger courseId);
}
