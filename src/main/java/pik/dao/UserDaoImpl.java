package pik.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pik.dto.CourseInfo;
import pik.dto.MarkInfo;
import pik.dto.QuestionInfo;
import pik.dto.UserInfo;
import pik.repositories.CourseRepository;
import pik.repositories.MarkRepository;
import pik.repositories.QuestionRepository;
import pik.repositories.UserRepository;

@Service
@ComponentScan(basePackages = {"repositories"})
public class UserDaoImpl implements UserDao{

	private UserRepository userRepository;

    private MarkRepository markRepository;

	private CourseRepository courseRepository;

	private QuestionRepository questionRepository;

	@Autowired
	public UserDaoImpl(UserRepository userRepository, MarkRepository markRepository, CourseRepository courseRepository,
					   QuestionRepository questionRepository) {
		this.userRepository = userRepository;
		this.markRepository = markRepository;
		this.courseRepository = courseRepository;
		this.questionRepository = questionRepository;
	}

	public UserInfo create(UserInfo user) {
		return userRepository.save(user);
	}

	public UserInfo getById(String Id){
		return userRepository.findByUserId(Id);
	}

    public List<UserInfo> readAll() {
        return userRepository.findAll();
    }

	public Page<UserInfo> readAll(Pageable pageable) {
        return userRepository.findAll(pageable);
	}

	public UserInfo update(UserInfo user) {
		UserInfo existingUser = userRepository.findByUserId(user.getUserId());

		if (existingUser == null) {
			return null;
		}

		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());
		existingUser.setMarks(user.getMarks());
		existingUser.setSubscribedCourses(user.getSubscribedCourses());
		markRepository.save(existingUser.getMarks());
		return userRepository.save(existingUser);
	}

	public Boolean delete(UserInfo user) {
		UserInfo existingUser = userRepository.findByUserId(user.getUserId());

		if (existingUser == null) {
			return false;
		}
		List<MarkInfo> marks = existingUser.getMarks();

		for(MarkInfo mark : marks) {
			markRepository.delete(mark);
		}
		userRepository.delete(existingUser);
		return true;
	}

	public Boolean idExists(String userId)
	{
		return userRepository.exists(userId);
	}


	public Boolean subscribe(UserInfo user, BigInteger courseId)
	{
		CourseInfo course = courseRepository.findById(courseId);

		if(course == null)
			return false;

		List<BigInteger> ids = user.getSubscribedCourses();

		for(BigInteger id : ids)
			if (id.equals(courseId))
				return false;

		ids.add(courseId);
		user.setSubscribedCourses(ids);


		List<MarkInfo> marks = user.getMarks();
		if(marks == null)
			marks = new ArrayList<MarkInfo>();

		List<QuestionInfo> quests = questionRepository.findByCourseId(courseId);
		if(quests != null && quests.size() > 0){
			for(QuestionInfo q : quests){
				MarkInfo mark = new MarkInfo();
				mark.setQuestionId(q.getId());
				mark.setEf(2.5f);
				mark.setCourseId(courseId);
				mark.setDate(new Date());
				mark.setInterval(0L);
				mark.setCounter(0);
				marks.add(mark);
			}

		}

		user.setMarks(marks);
		markRepository.save(marks);

		if( userRepository.save(user) == null)
			return false;

		else
			return true;
	}

	public Boolean unsubscribe(UserInfo user, BigInteger courseId)
	{
		CourseInfo course = courseRepository.findById(courseId);

		List<BigInteger> ids = user.getSubscribedCourses();
		int index = 0;

		Boolean found = false;
		for(BigInteger id : ids) {
			if (id.equals(courseId)) {
				found = true;
				break;
			}
			++index;
		}
		if(!found)
			return false;


		ids.remove(index);

		List<MarkInfo> marks = user.getMarks();

		if(marks != null && marks.size() >0){
			List<MarkInfo> toRemove = new ArrayList<MarkInfo>();
			for(MarkInfo mark : marks){
				if(mark.getCourseId().equals(courseId))
					toRemove.add(mark);
			}

			if(marks.size() > 0)
				marks.removeAll(toRemove);

		}

		user.setMarks(marks);
		markRepository.save(marks);

		if( userRepository.save(user) == null)
			return false;

		else
			return true;

	}
}

