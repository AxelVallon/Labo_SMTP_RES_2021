package www.heigvd.res.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientSMPT {
    //TODO : modifier avec fichier de config
    private String hostName = "localhost";
    private int portNumber = 25;
    private String mailSource = "axel.vallon@heig-vd.ch";
    private String mailDestination = "axel.vallon@heig-vd.ch";

    public ClientSMPT(){
        //TODO init param config, à voir si tu veux le passer par le main
    }

    public void sendMail() throws IOException {
        Socket socket = new Socket(hostName, portNumber);
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
        os.println("MAIL FROM:<" + mailSource + ">");
        startWith(in.readLine(), "250 ");
        //TODO: ajouter boucle pour plusieurs RCPT TO
        os.println("RCPT TO:<" + mailDestination + ">");
        startWith(in.readLine(), "250 ");
        os.println("DATA");
        startWith(in.readLine(), "354 ");
        //TODO send data, forge mail
        os.println("Je suis un drôle de mail");
        os.println("\r\n.\r\n");
        startWith(in.readLine(), "250 ");
        os.println("QUIT");
        startWith(in.readLine(), "221 ");
        socket.close();
        in.close();
        os.close();
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
