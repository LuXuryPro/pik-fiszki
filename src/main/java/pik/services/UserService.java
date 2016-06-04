package pik.services;

import java.util.List;
;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pik.dto.UserInfo;
import pik.repositories.MarkRepository;
import pik.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

    @Autowired
    private MarkRepository markRepository;


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

	/*public UserInfo update(UserInfo user) {
		UserInfo existingUser = userRepository.findByUsername(user.getUsername());

		if (existingUser == null) {
			return null;
		}

		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.getRole().setRole(user.getRole().getRole());

		// We must save both separately since there is no cascading feature
		// in Spring Data MongoDB (for now)
		roleRepository.save(existingUser.getRole());
		return userRepository.save(existingUser);
	}

	public Boolean delete(User user) {
		User existingUser = userRepository.findByUsername(user.getUsername());

		if (existingUser == null) {
			return false;
		}

		// We must delete both separately since there is no cascading feature
		// in Spring Data MongoDB (for now)
		roleRepository.delete(existingUser.getRole());
		userRepository.delete(existingUser);
		return true;
	}*/
}

