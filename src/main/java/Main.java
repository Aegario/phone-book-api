import com.sun.net.httpserver.HttpServer;
import contact.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.LocalDate;

// import contact.ContactDTO;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(80), 0);
        ContactDAO contactDAO = new ContactDAO();
        ContactDTOMapper contactDTOMapper = new ContactDTOMapper();
        ContactService contactService = new ContactService(contactDAO, contactDTOMapper);
        server.createContext("/api/v1/contact", new ContactHandler(contactService));
        server.setExecutor(null);
        server.start();
    }
}
