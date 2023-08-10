package who.reconsystem.app.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import who.reconsystem.app.root.config.Functions;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBean {
    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserBean populate(List<String> data) {
        return UserBean.builder()
                .id(Long.parseLong(data.get(0)))
                .userId(data.get(1))
                .firstName(data.get(2))
                .lastName(data.get(3))
                .username(data.get(4))
                .email(data.get(5))
                .createdAt(Functions.dateTime(data.get(6)))
                .updatedAt(Functions.dateTime(data.get(7)))
                .password(data.get(8))
                .build();
    }
}
