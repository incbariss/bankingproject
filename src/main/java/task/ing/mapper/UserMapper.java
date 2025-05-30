package task.ing.mapper;

import task.ing.model.dto.request.UserRequestDto;
import task.ing.model.dto.response.AccountResponseDto;
import task.ing.model.dto.response.UserResponseDto;
import task.ing.model.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public interface UserMapper {

    static User toEntity(UserRequestDto dto) {
        User user = new User();
        user.setName(dto.name());
        user.setSurname(dto.surname());
        user.setTckno(dto.tckno());
        user.setEmail(dto.email());
        return user;
    }

    static UserResponseDto toDto(User user) {
        return toDto(user, false);
    }


    static UserResponseDto toDto(User user, boolean includeDeletedAccounts) {
        List<AccountResponseDto> accountDtos = user.getAccounts() != null
                ? user.getAccounts().stream()
                .filter(account -> account.isDeleted() == includeDeletedAccounts)
                .map(AccountMapper::toDto)
                .collect(Collectors.toList()) : List.of();
        return new UserResponseDto(user.getName(), user.getEmail(), accountDtos);
    }
}
