package com.vinylrecordsstore.services;

import com.vinylrecordsstore.dto.UserDTO;
import com.vinylrecordsstore.entities.User;
import com.vinylrecordsstore.enums.Role;
import com.vinylrecordsstore.exceptions.NotFoundException;
import com.vinylrecordsstore.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Transactional
    public void registerUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(Role.USER);
        User saved = userRepository.save(user);
        log.info("Registered new user: {}", saved.getLogin());
    }

    public boolean existsByLogin(String login) {
        return userRepository.existsByLoginIgnoreCase(login);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmailIgnoreCase(email);
    }

    @Transactional(readOnly = true)
    public List<User> getAllCustomers() {
        return userRepository.findAllByRole(Role.USER);
    }

    @Transactional
    @CacheEvict(value = "ordersByUser", allEntries = true)
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден: " + userId));

        // Удалить все заказы этого пользователя
        orderService.deleteOrdersByUser(user);

        userRepository.delete(user);
        log.info("Deleted user {} and all their orders", user.getLogin());
    }

    /**
     * Для Spring Security: поиск пользователя по логину или email.
     */
    public UserDetails getUserByLoginOrEmail(String loginOrEmail) {
        return userRepository
                .findByLoginIgnoreCaseOrEmailIgnoreCase(loginOrEmail, loginOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + loginOrEmail));
    }
}