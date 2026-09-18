package amitab.skube.dto;

import amitab.skube.entity.Role;
import amitab.skube.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String fullName,
        String email,
        Role role,
        Instant createdAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}
