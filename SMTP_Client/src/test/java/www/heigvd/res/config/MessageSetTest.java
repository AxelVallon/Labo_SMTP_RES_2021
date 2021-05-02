/**
 * Auteurs: Lev POZNIAKOFF, Axel VALLON
 *
 * Tests de la classe messageSet
 */
package www.heigvd.res.config;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MessageSetTest {
    @Test
    public void messageListCantBeEmpty(){
        ArrayList<Message> test = new ArrayList<>();
        try
        {
            MessageSet ms = new MessageSet(test);
        } catch(IllegalArgumentException e) {
            return;
        }
    }
}
