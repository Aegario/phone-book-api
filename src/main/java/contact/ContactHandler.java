package contact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ContactHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch (method) {
            case "POST":
                createContact(exchange);
                break;
            case "GET":
                getContact();
                break;
            case "PATCH":
                patchContact();
                break;
            case "DELETE":
                deleteContact();
                break;
            default:
                System.out.println("respond with something for the rest of http methods");
        }
    }

    private void getContact() {
        System.out.println("This is GET");
    }

    private void createContact(HttpExchange exchange) throws IOException {
        System.out.println("This is POST");
        InputStream inputStream = exchange.getRequestBody();
        parseBody(inputStream);
    }

    private void patchContact() {
        System.out.println("This is PATCH");
    }

    private void deleteContact() {
        System.out.println("This is DELETE");
    }

    private void parseBody(InputStream bodyStream) throws IOException {
        // turning bodyStream into a String
        String stringifiedBody = stringifyBody(bodyStream);

        // turning stringifiedBody into a map
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> bodyMap = mapper.readValue(stringifiedBody, Map.class);

        // mapping bodyMap into a ContactDTO

    }

    private String stringifyBody(InputStream bodyStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(bodyStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String newLineSeparator = System.getProperty("line.separator");

        StringBuilder stringifiedBody = new StringBuilder();
        String currentLine;
        while ((currentLine = bufferedReader.readLine()) != null) {
            if (stringifiedBody.length() > 0) stringifiedBody.append(newLineSeparator);
            stringifiedBody.append(currentLine);
        }

        bufferedReader.close();

        return stringifiedBody.toString();
    }
}
