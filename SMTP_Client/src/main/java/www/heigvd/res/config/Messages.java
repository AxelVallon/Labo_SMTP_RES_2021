package www.heigvd.res.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Messages {
    @Getter
    List<Message> messages;

    public Messages(List<Message> messages){
        this.messages = new ArrayList<>(messages);
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Message getRandomMessage(){
        Random rand = new Random();
        return messages.get(rand.nextInt(messages.size()));
    }
    public Message get(int i){
        return messages.get(i);
    }

    public int size(){return messages.size();}

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
