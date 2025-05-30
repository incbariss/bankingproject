package task.ing.model.dto.response;

import java.util.List;

public record UserResponseDto(
        String name,

        String email,

        List<AccountResponseDto> accounts

) {
}
