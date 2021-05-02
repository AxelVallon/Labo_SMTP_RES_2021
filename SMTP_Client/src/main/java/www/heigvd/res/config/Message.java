package www.heigvd.res.config;

import lombok.Getter;
import lombok.Setter;


/**
 * Classe représentant 1 message
 * Celui-ci a 1 contenu et 1 sujet
 */
@Getter
@Setter
public class Message {
    private String content;
    private String subject;

    public Message(){}
}
