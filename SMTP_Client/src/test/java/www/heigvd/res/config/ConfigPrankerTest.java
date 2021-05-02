/**
 * Auteurs: Lev POZNIAKOFF, Axel VALLON
 *
 * Test de la classe ConfigPranker
 */

package www.heigvd.res.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigPrankerTest {

    @Test
    public void groupWithOneRecipientShouldNotBePossible(){
        try {
            ConfigPranker cp = ConfigPranker.loadFromConfig("src/test/files/groupWithOneRecipient.yaml");
        } catch  (Exception e){
            return;
        }
        fail("No Exception catched");
    }

    @Test
    public void configWithNoHostName(){
        try {
            ConfigPranker cp = ConfigPranker.loadFromConfig("src/test/files/missingHostName.yaml");
        } catch  (Exception e){
            return;
        }
        fail("No Exception catched");
    }

    @Test
    public void configWithNoGroups(){
        try {
            ConfigPranker cp = ConfigPranker.loadFromConfig("src/test/files/noGroups.yaml");
        } catch  (Exception e){
            return;
        }
        fail("No Exception catched");
    }

    @Test
    public void configWithNoPort(){
        try {
            ConfigPranker cp = ConfigPranker.loadFromConfig("src/test/files/noPort.yaml");
        } catch  (Exception e){
            return;
        }
        fail("No Exception catched");
    }

    @Test
    public void configWithInvalidPort(){
        try {
            ConfigPranker cp = ConfigPranker.loadFromConfig("src/test/files/invalidPort.yaml");
        } catch  (Exception e){
            return;
        }
        fail("No Exception catched");
    }



}