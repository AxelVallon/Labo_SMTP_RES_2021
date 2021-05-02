package www.heigvd.res;

import www.heigvd.res.client.ClientSMPT;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            new ClientSMPT(ClientSMPT.DEFAULT_CONFIG, ClientSMPT.DEFAULT_MESSAGES).sendMails();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("erreur");
        }

    }
}
