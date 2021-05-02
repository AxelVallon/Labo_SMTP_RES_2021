package www.heigvd.res.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    private String content;
    private String subject;

    public Message(){}
}
