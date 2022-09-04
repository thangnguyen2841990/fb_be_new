package com.example.demo.service.user;

import com.example.demo.model.dto.PasswordCorrect;
import com.example.demo.model.dto.UserPrincipal;
import com.example.demo.model.dto.UsernameExist;
import com.example.demo.model.entity.Role;
import com.example.demo.model.entity.User;
import com.example.demo.repository.IRoleRepository;
import com.example.demo.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUSerService{
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Page<User> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public void remove(Long id) {
            this.userRepository.deleteById(id);
    }

    @Override
    public User saveAdmin(User user) {
        Optional<Role> roleOptional = this.roleRepository.findByNameContaining("ROLE_ADMIN");
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(roleOptional.get().getId(), roleOptional.get().getName()));
        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public User saveUser(User user) {
        Optional<Role> roleOptional = this.roleRepository.findByNameContaining("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(roleOptional.get().getId(), roleOptional.get().getName()));
        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public User saveHost(User user) {
        Optional<Role> roleOptional = this.roleRepository.findByNameContaining("ROLE_HOST");
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(roleOptional.get().getId(), roleOptional.get().getName()));
        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public UsernameExist isUsernameExist(String username) {
        List<User> users = this.userRepository.findAll();
        UsernameExist usernameExist = new UsernameExist();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                usernameExist.setStatus(true);
                break;
            }
        }
        return usernameExist;
    }

    @Override
    public PasswordCorrect isPasswordCorrect(String username ,String password) {
        List<User> users = this.userRepository.findAll();
        PasswordCorrect passwordCorrect = new PasswordCorrect();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                if (passwordEncoder.matches(password, users.get(i).getPassword())) {
                    passwordCorrect.setStatus(true);
                    break;
                }
            }
        }
        return passwordCorrect;
    }

    @Override
    public List<User> findAllUserOtherCurrenUserAndFriends(Long currenUserId) {
        return this.userRepository.findAllUserOtherCurrenUserAndFriends(currenUserId);
    }

    @Override
    public List<User> searchByFullName(String name) {
        String fullName = "%" + name + "%";
        List<User> users = this.userRepository.searchByFullName(fullName);
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).get();
        return UserPrincipal.build(user);
    }
}
