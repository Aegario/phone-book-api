import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;
import contact.ContactHandler;
import contact.ContractDTO;

public class Main {
    public static void main(String[] args) throws IOException {
        ContractDTO contractDTO = new ContractDTO("Denis");
        System.out.println(contractDTO.name());
        // HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        // server.createContext("/api/v1/contact", new ContactHandler());
        // server.setExecutor(null);
        // server.start();
    }
}
