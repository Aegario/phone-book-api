package contact;

import java.io.*;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ContactHandler implements HttpHandler {
    ContactService contactService;

    public ContactHandler(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch (method) {
            case "POST":
                createContact(exchange);
                break;
            case "GET":
                getContact(exchange);
                break;
            case "PATCH":
                patchContact(exchange);
                break;
            case "DELETE":
                deleteContact(exchange);
                break;
            default:
                System.out.println("respond with something for the rest of http methods");
        }
    }

    private void getContact(HttpExchange exchange) {
        System.out.println("This is GET");
    }

    private void createContact(HttpExchange exchange) throws IOException {
        InputStream inputBodyStream = exchange.getRequestBody();
        BufferedReader bufferedInputReader = new BufferedReader(new InputStreamReader(inputBodyStream));
        ContactDTO contactDTO = parseInput(bufferedInputReader);
        contactService.create(contactDTO);

        OutputStream outputBodyStream = exchange.getResponseBody();
        exchange.sendResponseHeaders(201, 0);
        outputBodyStream.close();
    }

    private void patchContact(HttpExchange exchange) throws IOException {
        InputStream bodyStream = exchange.getRequestBody();
        BufferedReader bufferedBodyReader = new BufferedReader(new InputStreamReader(bodyStream));
        ContactDTO contactDTO = parseInput(bufferedBodyReader);
    }

    private void deleteContact(HttpExchange exchange) {
        System.out.println("This is DELETE");
    }

    private ContactDTO parseInput(BufferedReader bufferedReader) throws IOException {
        String stringifiedInput = stringifyInput(bufferedReader);

        // turning stringifiedInput into a map
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> inputMap = mapper.readValue(stringifiedInput, Map.class);

        // mapping bodyMap into a ContactDTO
        String firstName = (String) inputMap.get("firstName");
        String lastName = (String) inputMap.get("lastName");
        String phoneNumber = (String) inputMap.get("phoneNumber");
        String birthdate = (String) inputMap.get("birthdate");

        return new ContactDTO(
            firstName,
            lastName,
            phoneNumber,
            birthdate
        );
    }

    private String stringifyInput(BufferedReader bufferedBodyReader) throws IOException {
        String newLineSeparator = System.getProperty("line.separator");

        StringBuilder stringifiedBody = new StringBuilder();
        String currentLine;
        while ((currentLine = bufferedBodyReader.readLine()) != null) {
            if (stringifiedBody.length() > 0) stringifiedBody.append(newLineSeparator);
            stringifiedBody.append(currentLine);
        }

        bufferedBodyReader.close();

        return stringifiedBody.toString();
    }
}
