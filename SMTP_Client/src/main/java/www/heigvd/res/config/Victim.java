/**
 * Auteurs: Lev POZNIAKOFF, Axel VALLON
 */
package www.heigvd.res.config;
import lombok.Getter;
import lombok.Setter;

/**
 * Représente une victime
 * Celle-ci a un email
 */
@Getter @Setter
public class Victim {
    private String email;

    public Victim(String email){
        this.email = email;
    }
    public Victim(){this.email = "";}
}
