/**
 * Auteurs: Lev POZNIAKOFF, Axel VALLON.
 */
package www.heigvd.res.client;

import www.heigvd.res.config.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class ClientSMPT {
    private final String DEFAULT_CONFIG = "config/config.yaml";
    private final String DEFAULT_MESSAGES = "config/mails.yaml";
    private ConfigPranker config;
    private MessageSet messageSet;

    public ClientSMPT() throws IOException {
        //TODO init param config, à voir si tu veux le passer par le main
        config = ConfigPranker.loadFromConfig(DEFAULT_CONFIG);
        messageSet = MessageSet.loadFromYAML(DEFAULT_MESSAGES);

    }

    /**
     * Permet d'envoyer les mails à tous les groupes configuré avec des contenu de mail alléatoires
     * @throws IOException
     */
    public void sendMails() throws IOException {
        Random rand = new Random();

        //Parcours des groupes, pour chaque groupe un email est envoyé
        for(Group group : config.getGroups()) {
            /** Configuration des entrées sorties **/
            Socket socket = new Socket(config.getHostname(), config.getPort());
            PrintWriter os =
                    new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8 ));
            /** Fin configuration entrées sorties **/

            /** Convertation d'envoi de mail avec le serveur SMTP **/
            startWith(in.readLine(), "220 ");
            os.println("EHLO server");
            //attente sur 250 HELP
            String tmp;
            do {
                tmp = in.readLine();
                startWith(tmp, "250");
            } while (!tmp.startsWith("250 "));

            os.println("MAIL FROM: <" + group.getSender().getEmail() + ">");
            startWith(in.readLine(), "250 ");

            for (Victim recipient : group.getRecipients()) {
                os.println("RCPT TO: <" + recipient.getEmail() + ">");
                startWith(in.readLine(), "250 ");
            }
            os.println("DATA");
            startWith(in.readLine(), "354 ");

            // Séléection aléatoire d'un message
            Message message = messageSet.getRandomMessage();

            os.println(forgeEmailContent(group, message));
            os.println("\r\n.\r\n");
            startWith(in.readLine(), "250 ");
            os.println("QUIT");
            startWith(in.readLine(), "221 ");
            /** Fin de conversation avec le serveur SMTP **/

            socket.close();
            in.close();
            os.close();
        }
    }

    /**
     * Forge le contenu du message à envoyer
     * @param group Groupe ciblé par le mail
     * @param message Message à envoyer
     * @return le contenu du mail formaté
     */
    private String forgeEmailContent(Group group, Message message){
        StringBuilder forgedEmail = new StringBuilder();
        forgedEmail.append("From: ").append(group.getSender().getEmail()).append('\n');
        forgedEmail.append("To : ");
        for (Victim recipient : group.getRecipients()){
            forgedEmail.append(recipient.getEmail()).append(",");
        }
        // On change la dernière virgule avec un saut à la ligne
        if (group.getRecipients().size() > 0)
            forgedEmail.replace(forgedEmail.length() - 1, forgedEmail.length(), "\n");
        forgedEmail.append("Subject : ").append(message.getSubject()).append("\n\n");
        forgedEmail.append(message.getContent());
        System.out.println(forgedEmail);
        
        return forgedEmail.toString();
    }

    /**
     * Throw une exception si un string ne commence pas avec la key
     * @param source mot source
     * @param key mot à rechercher au début de source
     * @throws IOException Exception lancée quand source ne commence pas par key
     */
    private void startWith(String source, String key) throws IOException {
        System.out.println(source);
        if (!source.startsWith(key)){
            throw new IOException("Erreur lors de l'envoi du mail");
        }
    }
}
