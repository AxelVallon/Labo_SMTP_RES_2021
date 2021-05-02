package www.heigvd.res.client;

import www.heigvd.res.config.ConfigPranker;
import www.heigvd.res.config.Group;
import www.heigvd.res.config.Messages;
import www.heigvd.res.config.Victim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ClientSMPT {
    private final String DEFAULT_CONFIG = "config/config.yaml";
    private final String DEFAULT_MESSAGES = "config/mails.yaml";
    private ConfigPranker config;
    private Messages messages;

    public ClientSMPT() throws IOException {
        //TODO init param config, à voir si tu veux le passer par le main
        config = ConfigPranker.loadFromConfig(DEFAULT_CONFIG);
        messages = Messages.loadFromYAML(DEFAULT_MESSAGES);

    }

    public void sendMail() throws IOException {
        messages.shuffleMessages();
        //Pacours des groupes
        for(int groupIndex=0; groupIndex < config.getGroups().size(); ++groupIndex) {
            Socket socket = new Socket(config.getHostname(), config.getPort());
            PrintWriter os =
                    new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8 ));
            startWith(in.readLine(), "220 ");
            os.println("EHLO server");
            //attente sur 250 HELP
            String tmp;
            do {
                tmp = in.readLine();
                startWith(tmp, "250");
            } while (!tmp.startsWith("250 "));

            List<Victim> recipients = config.getGroups().get(groupIndex).getRecipients();
            os.println("MAIL FROM: <" + config.getGroups().get(groupIndex).getSender().getEmail() + ">");
            startWith(in.readLine(), "250 ");

            for (int RCPTIndex = 0; RCPTIndex < recipients.size(); ++RCPTIndex) {
                os.println("RCPT TO: <" + recipients.get(RCPTIndex).getEmail() + ">");
                startWith(in.readLine(), "250 ");
            }
            os.println("DATA");
            startWith(in.readLine(), "354 ");
            //TODO send data, forge mail
            os.println(messages.get(groupIndex % messages.getMessages().size()));
            os.println("\r\n.\r\n");
            startWith(in.readLine(), "250 ");
            os.println("QUIT");
            startWith(in.readLine(), "221 ");
            socket.close();
            in.close();
            os.close();
        }
    }

    private String forgeEmailContent(Group group){
        StringBuilder forgedEmail = new StringBuilder();
        forgedEmail.append("From: ").append(group.getSender().getEmail()).append('\n');
        forgedEmail.append("To : ");
        
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
