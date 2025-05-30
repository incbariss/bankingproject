package task.ing.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task.ing.mapper.UserMapper;
import task.ing.model.dto.request.UserRequestDto;
import task.ing.model.dto.response.UserResponseDto;
import task.ing.model.entity.User;
import task.ing.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto createUser(UserRequestDto dto) {
        User user = UserMapper.toEntity(dto);
        user = userRepository.save(user);
        return UserMapper.toDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAllByIsDeletedFalse();
        return users.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserById(int id) {
        Optional<User> user = userRepository.findByIdAndIsDeletedFalse(id);
        return user.map(UserMapper::toDto).orElse(null);
    }

    public UserResponseDto updateUser(int id, UserRequestDto dto) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(dto.name());
            user.setSurname(dto.surname());
            user.setTckno(dto.tckno());
            user.setEmail(dto.email());
            user = userRepository.save(user);
            return UserMapper.toDto(user);
        }
        return null;
    }

    @Transactional
    public void deleteUser(int id) {
        User user = userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setDeleted(true);

        if (user.getAccounts() != null) {
            user.getAccounts().forEach(account -> account.setDeleted(true));
        }
        userRepository.save(user);
    }

    @Transactional
    public UserResponseDto restoreUser(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!user.isDeleted()) {
            throw new IllegalStateException("user is not deleted");
        }
        user.setDeleted(false);

        if (user.getAccounts() != null) {
            user.getAccounts().forEach(account -> account.setDeleted(false));
        }

        user = userRepository.save(user);
        return UserMapper.toDto(user, false);
    }

    public List<UserResponseDto> getDeletedUsers() {
        List<User> deletedUsers = userRepository.findAllByIsDeletedTrue();

        return deletedUsers.stream()
                .map(user -> UserMapper.toDto(user, true))
                .collect(Collectors.toList());
    }
}
