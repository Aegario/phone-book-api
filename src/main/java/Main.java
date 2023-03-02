import com.sun.net.httpserver.HttpServer;
import contact.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        ContactService contactService = new ContactService(
            new ContactDAO(),
            new ContactDTOMapper()
        );
        server.createContext("/api/v1/contact", new ContactHandler(contactService));
        server.setExecutor(null);
        server.start();
    }
}
