package www.heigvd.res.config;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Victim {
    private String email;

    public Victim(String email){
        this.email = email;
    }
    public Victim(){this.email = "";}
}
