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


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ConfigPranker {
    private int port;
    private String ip;
    private List<Group> groups;

    public ConfigPranker(){
        this.groups = new ArrayList<>();
        this.ip = "";
    }

    public ConfigPranker(int port, String ip, List<String> messages, List<Group> groups) {
        this.port = port;
        if(!ipValid(ip))
            throw new IllegalArgumentException("IP is not valid");
        this.ip = ip;
        if(groups.isEmpty())
            throw new IllegalArgumentException("Minimum size for groups is 1");
        this.groups = groups;
    }

   public void setPort(int port){
        this.port = port; //voir condition de fail
   }

   public void setIp(String ip){
        if(!ipValid(ip))
            throw new IllegalArgumentException("IP is not valid");
        this.ip = ip;
   }

   public void setGroups(List<Group> groups){
        if(groups.isEmpty())
            throw new IllegalArgumentException("Groups can't be empty, minimum size is 1");
        this.groups.addAll(groups);
   }

    /**
     * Validate an IPv4 address
     * @param ip to validate
     * @return true if the ip is valid
     */
    private static boolean ipValid(final String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        return ip.matches(PATTERN);
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


    public static void main (String[] args) throws IOException {
        ConfigPranker cp = loadFromConfig("config/config.yaml");
    }
}
