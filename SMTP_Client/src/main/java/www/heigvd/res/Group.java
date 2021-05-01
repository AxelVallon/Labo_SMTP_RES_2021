package www.heigvd.res;

import java.util.List;

public class Group {
    Victim sender;
    List<Victim> recipients;

    public Group(Victim sender, List<Victim> recipients) throws Exception {
        if(recipients.size() < 2)
            throw new Exception();
        this.sender = sender;
        this.recipients = recipients;
    }
    public Group(){}

    public Victim getSender() {
        return sender;
    }

    public List<Victim> getRecipients() {
        return recipients;
    }

    public void setSender(Victim sender) {
        this.sender = sender;
    }

    public void setRecipients(List<Victim> recipients) {
        this.recipients = recipients;
    }
}
