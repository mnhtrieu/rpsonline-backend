package eu.mnhtrieu.rpsonline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerNamesResponse {
    private String host;
    private String join;
}
