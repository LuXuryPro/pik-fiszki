package pik.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pik.dto.MarkInfo;
import pik.dto.UserInfo;
import pik.repositories.MarkRepository;
import pik.repositories.UserRepository;

@Service
@ComponentScan(basePackages = {"repositories"})
public class UserDaoImpl implements UserDao{

	private UserRepository userRepository;

    private MarkRepository markRepository;

	@Autowired
	public UserDaoImpl(UserRepository userRepository, MarkRepository markRepository) {
		this.userRepository = userRepository;
		this.markRepository = markRepository;
	}

	public UserInfo create(UserInfo user) {
		return userRepository.save(user);
	}

	public UserInfo read(UserInfo user) {
		return user;
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
		existingUser.setUserName(user.getUserName());
		existingUser.setSubscribedcourses(user.getSubscribedcourses());
		// We must save both separately since there is no cascading feature
		// in Spring Data MongoDB (for now)
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

	public Boolean IdExists(String userId)
	{
		return userRepository.exists(userId);
	}

	public Boolean UserNameExists(String username)
	{
		UserInfo user = userRepository.findByUserName(username);
		return (user !=null);
	}
}

