package www.heigvd.res.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Messages {
    @Getter
    List<Message> messages;


    public Messages(){
        this.messages = new ArrayList<>();

    }

    public Messages(List<String> messages, String subject){
        this();
        checkListValidity(messages);
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Message get(int i){
        return messages.get(i);
    }
    public int size(){return messages.size();}

    /**
     * Verifie la validité d'une liste de message (pas vide, aucun message vide)
     * @param list, la liste à vérifier
     */
    private void checkListValidity(List<String> list){
        if(list.isEmpty())
            throw new IllegalArgumentException("List is empty");
        for(String s: list)
            if(s.isEmpty())
                throw new IllegalArgumentException("One of the strings is empty");
    }

    public static Messages loadFromYAML(String path) throws IOException {
        if(path.isEmpty())
            throw new IllegalArgumentException("Path to config can't be empty");
        ObjectMapper mapper =  new ObjectMapper(new YAMLFactory());
        return mapper.readValue(new File(path), Messages.class);
    }

    public static void main(String[] args) throws IOException {
        Messages m = loadFromYAML("config/mails.yaml");

    }
}
