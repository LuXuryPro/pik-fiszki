package pik.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pik.dto.UserInfo;


import java.util.List;

public interface UserDao {

    UserInfo create(UserInfo user);

    UserInfo read(UserInfo user);

    List<UserInfo> readAll();

    Page<UserInfo> readAll(Pageable pageable);

    UserInfo update(UserInfo user);

    Boolean delete(UserInfo user);

    Boolean IdExists(String userId);

    Boolean UserNameExists(String username);
}

