
package Daniel.lab3;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class NamingServer {

    HashMap<Integer, String> nodeNames;    //hash(node) : ipaddres
    HashMap<Integer, List<String>> fileNames;    //hash(filename) : [nodeNames,...]
    private final String filename = "Database.json";

    public NamingServer() {
        this.loadFromJson(filename);
    }

    public boolean registerNode(String nodeName,String ipAddress) {
        int hashNode = nodeName.hashCode();
        if (nodeNames.containsKey(hashNode)) {  //if the name is already in the map=>fuck you
            return false;
        }
        nodeNames.put(hashNode, ipAddress);
        this.saveToJson(filename);
        return true;
    }

    public boolean registerFile(String fileName,String nodeName) {
        int hashFile = fileName.hashCode();
        if (fileNames.containsKey(hashFile)) {
            List<String> fileList = fileNames.get(hashFile);
            fileList.add(nodeName);
        }
        else {
            List<String> fileList = new ArrayList<>();
            fileList.add(nodeName);
            fileNames.put(hashFile, fileList);
        }
        this.saveToJson(filename);
        return true;
    }

    /*
    Returns IP address of corresponding nodename
     */
    public String getIP(String nodeName) {
        Integer hashNode = nodeName.hashCode();
        if  (nodeNames.containsKey(hashNode)) {
            return nodeNames.get(hashNode);
        }
        throw new RuntimeException("Node not found");
    }

    /*
    Returns list of nodenames which contain this file
     */
    public List<String> getNode_s_(String fileName) {
        Integer hashFile = fileName.hashCode();
        if (fileNames.containsKey(hashFile)) {
            return fileNames.get(hashFile);
        }
        throw new RuntimeException("File not found");
    }


    public void saveToJson(String filePath) {
        ObjectMapper mapper = new ObjectMapper();

        NamingServerData data = new NamingServerData();
        data.nodeNames = this.nodeNames;
        data.fileNames = this.fileNames;

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadFromJson(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath);

        try {
            if (!file.exists() || file.length() == 0) {
                this.nodeNames = new HashMap<>();
                this.fileNames = new HashMap<>();
                return;
            }
            NamingServerData data = mapper.readValue(file, NamingServerData.class);
            this.nodeNames = (data.nodeNames != null) ? data.nodeNames : new HashMap<>();
            this.fileNames = (data.fileNames != null) ? data.fileNames : new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
            this.nodeNames = new HashMap<>();
            this.fileNames = new HashMap<>();
        }
    }
}
