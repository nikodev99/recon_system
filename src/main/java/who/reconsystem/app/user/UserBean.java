package who.reconsystem.app.user;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBean {
    private SimpleLongProperty id;
    private SimpleStringProperty userId;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleStringProperty email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
