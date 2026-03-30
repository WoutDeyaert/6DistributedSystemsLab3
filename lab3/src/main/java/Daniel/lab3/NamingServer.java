
package Daniel.lab3;
import java.util.HashMap;

public class NamingServer {

    HashMap<Integer, String> nodeNames = new HashMap<>();    //hash(node) : ipaddres
    HashMap<Integer, String> fileNames = new HashMap<>();    //hash(filename) : hash(node)

    public void NamingServer() {

    }

    public boolean registerNode(String nodeName,String ipAddress) {
        int hashNode = nodeName.hashCode();
        if (nodeNames.containsKey(hashNode)) {  //if the name is already in the map=>fuck you
            return false;
        }
        nodeNames.put(hashNode, ipAddress);
        return true;
    }

    public boolean registerFile(String fileName,String nodeName) {
        int hashFile = fileName.hashCode();
        if (fileNames.containsKey(hashFile)) {
            return false;
        }
        fileNames.put(hashFile, nodeName);
        return true;
    }

    public String getNodeName(int hashNode) {
        if  (nodeNames.containsKey(hashNode)) {
            return nodeNames.get(hashNode);
        }
        throw new RuntimeException("Node not found");
    }
}
