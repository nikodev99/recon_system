package who.reconsystem.app.drive;

import com.google.api.client.util.DateTime;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoogleDriveFileFields {

    private String fileId;

    private String fileName;

    private String  fileMimeType;

    private DateTime fileCreationOn;

    private DateTime fileModifyOn;

    private long fileSize;
}
