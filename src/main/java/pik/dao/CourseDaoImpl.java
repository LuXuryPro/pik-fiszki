package pik.dao;

import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import pik.dto.CourseInfo;
import pik.dto.MarkInfo;
import pik.dto.QuestionInfo;
import pik.dto.UserInfo;
import pik.exceptions.SequenceException;
import pik.repositories.CourseRepository;
import pik.repositories.QuestionRepository;
import pik.repositories.MarkRepository;
import pik.repositories.UserRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Michał on 04.06.2016.
 */
@Service
@ComponentScan(basePackages = {"repositories"})
public class CourseDaoImpl implements CourseDao{

    private static final String COURSE_SEQ_KEY = "course";

    @Autowired
    private SequenceDao sequenceDao;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private MarkRepository markRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoOperations mongoOperation;


    public CourseInfo create(CourseInfo course){
        try {
            course.setId(sequenceDao.getNext(COURSE_SEQ_KEY));
        }
        catch(SequenceException seq){
            sequenceDao.insert(COURSE_SEQ_KEY);
            course.setId(sequenceDao.getNext(COURSE_SEQ_KEY));
        }

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
        BigInteger courseId = existingCourse.getId();
        //List<MarkInfo> marks = markRepository.findByCourseId(courseId);
        List<QuestionInfo> quest = questionRepository.findByCourseId(courseId);

        List<BigInteger> ids = Arrays.asList(courseId);

        //Query removeQuery = Query.query(Criteria.where("marks.name").in(ids));

        /*mongoOperation.upsert(
                new Query(),
                new Update().pull("marks.courseId", courseId),
                UserInfo.class);*/

        mongoOperation.updateMulti(new Query(),
            new Update().pull("marks", Query.query(Criteria.where("courseId").is(courseId))), "user");


        questionRepository.delete(quest);
        //markRepository.delete(marks);
        courseRepository.delete(existingCourse);
        return true;
    }

    public CourseInfo get(BigInteger id){
        return courseRepository.findById(id);
    }

    public CourseInfo get(String userId, String courseName){
        return courseRepository.findByOwnerIdAndName(userId,courseName);
    }

    public List<CourseInfo> getOwnedCourses(String userId){
        return courseRepository.findByOwnerId(userId);
    }

    public List<CourseInfo> getSubscribedCourses(UserInfo user){
        List<BigInteger> courseIDs = user.getSubscribedCourses();

        Iterable<CourseInfo> courseIt = courseRepository.findAll(courseIDs);

        List<CourseInfo> courses = new ArrayList<CourseInfo>();
        for (CourseInfo item : courseIt) {
            courses.add(item);
        }


        if(courseIDs.size() > courses.size()){
            List<BigInteger> toDelete = new ArrayList<BigInteger>();

            for(BigInteger id : courseIDs){
                boolean found = false;
                for(CourseInfo course: courses) {
                    if (course.getId().equals(id)) {
                        found = true;
                        break;
                    }
                }

                if(!found){
                    toDelete.add(id);
                }

            }

            for(BigInteger id : toDelete)
                courseIDs.remove(id);

            user.setSubscribedCourses(courseIDs);

            userRepository.save(user);
        }

        return courses;
    }


    public Boolean exists(BigInteger id){
        return (courseRepository.findById(id) != null);
    }

    public Boolean exists(String username, String name){
        return (courseRepository.findByOwnerIdAndName(username,name)!=null);
    }

    private static List<CourseInfo> makeCollection(Iterable<CourseInfo> iter) {
        List<CourseInfo> list = new ArrayList<CourseInfo>();
        for (CourseInfo item : iter) {
            list.add(item);
        }
        return list;
    }
}
