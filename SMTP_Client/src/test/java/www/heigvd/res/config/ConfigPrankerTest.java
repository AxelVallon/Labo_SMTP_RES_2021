package www.heigvd.res.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigPrankerTest {

    @Test
    public void groupWithOneRecipientShouldNotBePossible(){
        try{
            ConfigPranker.loadFromConfig("src/test/groupWithOneRecipien.yaml");
            fail("Exception not caught !");
        } catch (Exception e){
            assertTrue(true, "Exception catched");
        }
    }



}