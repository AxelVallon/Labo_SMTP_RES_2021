package www.heigvd.res.config;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigPrankerTest {

    @Test
    public void groupWithOneRecipientShouldNotBePossible(){
        try{
            ConfigPranker.loadFromConfig("src/test/groupWithOneRecipien.yaml");
        } catch (Exception e){
            assertTrue(true, "Exception catched");
        }
        fail("Exception not catched !");
    }



}