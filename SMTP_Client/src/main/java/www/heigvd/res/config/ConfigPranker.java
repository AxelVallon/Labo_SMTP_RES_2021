/**
 * @Author: Axel VALLON, Lev POZNIAKOFF
 *
 * Description: POJO de la configuration du pranker
 * la config contient, le port, l'ip, et les groupes
 */
package www.heigvd.res.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;


import lombok.Getter;
import lombok.Setter;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ConfigPranker {
    @Setter private int port;
    private String hostname;
    private List<Group> groups;

    public ConfigPranker(){
        hostname = "";
        groups = new ArrayList<>();
    }

    public ConfigPranker(int port, String hostname, List<Group> groups) {
        if(hostname.isEmpty())
            throw new IllegalArgumentException("hostname can't be empty");
        if(groups.isEmpty())
            throw new IllegalArgumentException("Minimum size for groups is 1");
        this.hostname = hostname;
        this.port = port;
        this.groups = groups;
    }

   public void setHostname(String hostname){
        if(hostname.isEmpty())
            throw new IllegalArgumentException("hostname can't be empty");
        this.hostname = hostname;
   }

   public void setGroups(List<Group> groups){
        if(groups.isEmpty())
            throw new IllegalArgumentException("Groups can't be empty, minimum size is 1");
        this.groups.addAll(groups);
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
