package www.heigvd.res.config;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Group {
    @Getter @Setter
    Victim sender;
    @Getter @Setter
    List<Victim> recipients;

    public Group(Victim sender, List<Victim> recipients) throws Exception {
        if(recipients.size() < 2)
            throw new Exception();
        this.sender = sender;
        this.recipients = recipients;
    }
    public Group(){}
}
