package who.reconsystem.app.dialog;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DialogAlert {
    private String title;
    private String header;
    private String content;
}
