package pik.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pik.dto.CourseInfo;
import pik.repositories.CourseRepository;
import pik.repositories.QuestionRepository;
import pik.repositories.MarkRepository;

/**
 * Created by Michał on 04.06.2016.
 */
@Service
public class CourseDaoImpl implements CourseDao{

    private static final String COURSE_SEQ_KEY = "course";
    private SequenceDao sequenceDao;


    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private MarkRepository markRepository;


    public CourseInfo create(CourseInfo course){
        course.setId(sequenceDao.getNext(COURSE_SEQ_KEY));
        return courseRepository.save(course);
    }

    public CourseInfo update(CourseInfo course){
        CourseInfo existingCourse = courseRepository.findById(course.getId());

        existingCourse.setName(course.getName());
        existingCourse.setDescription(course.getDescription());

        return courseRepository.save(existingCourse);
    }

    public Boolean delete(CourseInfo course){
        CourseInfo existingCourse = courseRepository.findById(course.getId());

        if (existingCourse == null) {
            return false;
        }


        courseRepository.delete(existingCourse);
        return true;
    }
}
