package who.reconsystem.app.root.auth;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionBinding {
    @SerializedName(value = "name")
    private String code;

    @SerializedName(value = "lastActivity")
    private long lastActivity;

    @SerializedName(value = "isLogged")
    private boolean isLogged;

    @SerializedName(value = "creationDate")
    private Instant creationDate;
}
