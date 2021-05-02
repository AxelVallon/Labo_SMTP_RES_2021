/**
 * Auteurs: Lev POZNIAKOFF, Axel VALLON
 */
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
    @Getter
    List<Victim> recipients;

    /**
     * Constructeur
     * @param sender (ne peux pas etre null)
     * @param recipients (> 2)
     * @throws Exception si les paramètres sont invalides
     */
    public Group(Victim sender, List<Victim> recipients) throws Exception {
        if (recipients.size() < 2)
            throw new IllegalArgumentException("Recipients must be at least 2");
        if (sender == null)
            throw new NullPointerException("sender can't be null");
        this.sender = sender;
        this.recipients = recipients;
    }

    public Group(){}

    /**
     * Setter
     * @param recipients
     * @throws IllegalArgumentException si la liste est de taille inférieur à 2
     */
    public void setRecipients(List<Victim> recipients) throws IllegalArgumentException{
        if (recipients.size() < 2)
            throw new IllegalArgumentException("Recipients must be at least 2");
        this.recipients = recipients;
    }

}
