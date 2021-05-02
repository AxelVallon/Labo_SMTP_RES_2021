/***
 * Auteurs: Lev POZNIAKOFF, Axel VALLON
 */
package www.heigvd.res.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.Getter;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Représente un ensemble de message, cette classe est utile à la déserialisation du fichier mails.yaml
 */
public class MessageSet {
    @Getter
    List<Message> messages;

    /**
     * Constructeur par défaut
     */
    public MessageSet(){}

    /**
     * Constructeur
     * @param messages la liste de message
     */
    public MessageSet(List<Message> messages){
        if(messages.isEmpty())
            throw new IllegalArgumentException("Message list can't be empty");
        this.messages = new ArrayList<>(messages);
    }

    /**
     * Setter
     * @param messages
     */
    public void setMessages(List<Message> messages) {
        if(messages.isEmpty())
            throw new IllegalArgumentException("Message list can't be empty");
        this.messages = messages;
    }

    /**
     * @return un message aléatoirement parmi ceux dans la liste message
     */
    public Message getRandomMessage(){
        Random rand = new Random();
        return messages.get(rand.nextInt(messages.size()));
    }

    /**
     * Utilise le chemin en paramètre pour créer un MessageSet
     * @param path, le chemin vers le YAML correspondant
     * @return le MessageSet nouvellement créé
     * @throws IOException
     */
    public static MessageSet loadFromYAML(String path) throws IOException {
        if(path.isEmpty())
            throw new IllegalArgumentException("Path to config can't be empty");
        ObjectMapper mapper =  new ObjectMapper(new YAMLFactory());
        return mapper.readValue(new File(path), MessageSet.class);
    }
}
