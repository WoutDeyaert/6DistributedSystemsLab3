package Daniel.lab3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/namingServer")
public class Controller {

    @Autowired
    private NamingServer namingServer;

    @GetMapping("/")
    public String home(){
        return "Welcome";
    }

    @GetMapping("/ipaddress/{nodename}")
    public String ipaddress(@PathVariable String nodename){
        return namingServer.getIP(nodename);
    }
    @GetMapping("/nodename/{filename}")
    public List<String> nodename(@PathVariable String filename){
        return namingServer.getNode_s_(filename);
    }

    @PostMapping("/newnode/{nodename}/{ipaddress}")
    public String newnode(@PathVariable String nodename, @PathVariable String ipaddress){
        boolean done = namingServer.registerNode(nodename, ipaddress);
        if (done) {
            return "The node has been added";
        }
        else{
            return "This node already exists";
        }
    }
    @PostMapping("/newfile/{nodename}/{filename}")
    public void newfile(@PathVariable String nodename, @PathVariable String filename){
        namingServer.registerFile(filename,nodename);
    }

    @PostMapping("/removefile/{nodename}/{filename}")
    public void removefile(@PathVariable String nodename, @PathVariable String filename){
        namingServer.deleteFile(filename,nodename);
    }

}
