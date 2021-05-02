/**
 * Auteurs: Lev POZNIAKOFF, Axel VALLON
 *
 * Test de la classe Group
 */
package www.heigvd.res.config;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {

    @Test
    public void groupWithOneRecipient() throws Exception {
        ArrayList <Victim> toTest = new ArrayList<>();
        toTest.add(new Victim("blabla"));
        try
        {

            Group group = new Group(new Victim("blablabla"), toTest);
        } catch(IllegalArgumentException e) {
            return;
        }
        fail("No Exception catched");
    }

    @Test
    public void groupWithNullSender() throws Exception {
        ArrayList <Victim> toTest = new ArrayList<>();
        toTest.add(new Victim("blabla"));
        toTest.add(new Victim("blabla2"));
        try
        {

            Group group = new Group(null, toTest);
        } catch(NullPointerException e) {
            return;
        }
        fail("No Exception catched");
    }



}