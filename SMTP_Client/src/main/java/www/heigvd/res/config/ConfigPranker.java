/**
 * Auteurs: Axel VALLON, Lev POZNIAKOFF
 *
 * Description: POJO de la configuration du pranker
 * la config contient, le port, l'ip, et les groupes
 */
package www.heigvd.res.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ConfigPranker {
    private int port;
    private String hostname;
    private List<Group> groups;

    /**
     * Constructeur par défaut
     * utile pour jackson
     */
    public ConfigPranker(){}

    /**
     * Constructeur
     * @param port le port (1 <= port <= 65535
     * @param hostname (taille > 0)
     * @param groups (taille > 0)
     * @throws IllegalArgumentException si les paramètres sont invalides
     */
    public ConfigPranker(int port, String hostname, List<Group> groups) throws IllegalArgumentException{
        if(hostname.length() == 0)
            throw new IllegalArgumentException("hostname can't be empty");
        if(groups.isEmpty())
            throw new IllegalArgumentException("Minimum size for groups is 1");
        if((port < 1) || (port > 65535))
            throw new IllegalArgumentException("Port is invalid");
        this.hostname = hostname;
        this.port = port;
        this.groups = groups;
    }

    /**
     * setter
     * @param hostname
     * @throws IllegalArgumentException si le hostname est vide
     */
   public void setHostname(String hostname) throws IllegalArgumentException{
        if(hostname == null)
            throw new IllegalArgumentException("hostname can't be empty");
        this.hostname = hostname;
   }

    /**
     * setter
     * @param groups
     * @throw si la liste en paramètre est vide
     */
   public void setGroups(List<Group> groups) throws IllegalArgumentException{
        if(groups.size() == 0)
            throw new IllegalArgumentException("Groups can't be empty, minimum size is 1");
        if(this.groups == null)
            this.groups = new ArrayList<>();
        this.groups.addAll(groups);
   }

    /**
     * setter
     * @param port
     * @throws IllegalArgumentException si le port est invalide
     */
   public void setPort(int port){
       if((port < 1) || (port > 65535))
           throw new IllegalArgumentException("Port is invalid");
       this.port = port;
   }

    /**
     * Charge une config dans un object ConfigPranker
     * @param path, le chemin vers la config
     * @return le ConfigPranker nouvellement créé avec la config
     * @throws IOException
     */
    public static ConfigPranker loadFromConfig(String path) throws IOException {
        ObjectMapper mapperConfig = new ObjectMapper(new YAMLFactory());
        return mapperConfig.readValue(new File(path), ConfigPranker.class);
    }
}
