package com.example.demo.service.user;

import com.example.demo.model.dto.PasswordCorrect;
import com.example.demo.model.dto.UsernameExist;
import com.example.demo.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface IUSerService extends UserDetailsService {
    Page<User> findAll(Pageable pageable);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    User save(User user);

    void remove(Long id);

    User saveAdmin(User user);

    User saveUser(User user);

    User saveHost(User user);

    UsernameExist isUsernameExist(String username);

    PasswordCorrect isPasswordCorrect(String username ,String password);

    List<User> findAllUserOtherCurrenUserAndFriends(Long currenUserId);

    List<User> searchByFullName(String name);

}
