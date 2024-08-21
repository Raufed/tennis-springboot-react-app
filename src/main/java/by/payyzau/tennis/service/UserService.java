package by.payyzau.tennis.service;

import by.payyzau.tennis.entity.Role;
import by.payyzau.tennis.entity.User;
import by.payyzau.tennis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public User createUser(User user) {
        if(userRepository.existsByEmail(user.getEmail()))
            throw new RuntimeException("Пользователь с таким именем уже существует");

        if (userRepository.existsByEmail(user.getEmail()))
            throw new RuntimeException("Пользователь с таким email уже существует");

        return save(user);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }
    public UserDetailsService userDetailsService() {
        return this::getByEmail;
    }

    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByEmail(username);
    }

    @Deprecated
    public void getAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }

}