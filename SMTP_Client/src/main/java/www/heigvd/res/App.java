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
            new ClientSMPT().sendMail();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
