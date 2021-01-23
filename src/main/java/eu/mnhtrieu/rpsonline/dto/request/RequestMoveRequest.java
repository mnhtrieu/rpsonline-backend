package eu.mnhtrieu.rpsonline.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestMoveRequest {
    int x;
    int y;
}
