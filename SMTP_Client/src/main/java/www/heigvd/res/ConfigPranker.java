package www.heigvd.res;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigPranker {
    private int port;
    private String ip;
    private List<String> messages;
    private List<Group> groups;



    public ConfigPranker(){}

    public ConfigPranker(int port, String ip, List<String> messages, List<Group> groups) {
        this.port = port;
        this.ip = ip;
        this.messages = messages;
        this.groups = groups;
    }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

    public List<String> getMessages() {
        return messages;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public static void loadFromConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ConfigPranker cp = mapper.readValue(new File("config.yaml"), ConfigPranker.class);
        System.out.println(1);
    }

    public static void main (String[] args) throws IOException {
        loadFromConfig();
    }




}
