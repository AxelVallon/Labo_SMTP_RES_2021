package www.heigvd.res.config;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Représente un groupe de victime
 * Celui-ci possède un envoyeur et une liste de récepteur ( 2 minimum)
 */
public class Group {
    @Getter @Setter
    Victim sender;
    @Getter @Setter
    List<Victim> recipients;

    public Group(Victim sender, List<Victim> recipients) throws Exception {
        if(recipients.size() < 2)
            throw new IllegalArgumentException("Recipients must be at least 2");
        if(sender == null)
            throw new NullPointerException("sender can't be null");
        this.sender = sender;
        this.recipients = recipients;
    }

    public Group(){}

}
