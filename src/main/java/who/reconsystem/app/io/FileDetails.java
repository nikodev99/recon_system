package who.reconsystem.app.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDetails {
    private Instant lastModifiedTime;
    private Instant creationDate;
    private boolean isSameFile;
    private long size;
    private String name;
    private String parent;
}
