package eu.mnhtrieu.rpsonline.dto.request;

import lombok.Data;

@Data
public class SendMoveRequest {

    private RequestMoveRequest from;
    private RequestMoveRequest to;
}
