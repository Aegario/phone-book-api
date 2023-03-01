package contact;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ContactHandler implements HttpHandler {
    ContactService contactService;

    public ContactHandler(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public void handle(HttpExchange ex) throws IOException {
        String method = ex.getRequestMethod();
        switch (method) {
            case "POST":
                createContact(ex);
                break;
            case "GET":
                getContact(ex);
                break;
            case "PATCH":
                patchContact(ex);
                break;
            case "DELETE":
                deleteContact(ex);
                break;
            default:
                System.out.println("respond with something for the rest of http methods");
        }
    }

    private void getContact(HttpExchange ex) throws IOException {
        Map<String, String> paramsMap = parseQuery(ex.getRequestURI().getQuery());
        int id = Integer.parseInt(paramsMap.get("id"));
        ContactDTO contactDTO = contactService.getContact(id);

        // writing dto into response body
        // TODO: is it better to stringify an object before converting it to byte stream?
        ex.sendResponseHeaders(200, 0);
        ObjectMapper mapper = new ObjectMapper();
        byte[] serializedContactDTO = mapper.writeValueAsBytes(contactDTO);
        // String stringifiedContactDTO = mapper.writeValueAsString(contactDTO);
        OutputStream outputStream = ex.getResponseBody();
        outputStream.write(serializedContactDTO);
        // outputStream.write(stringifiedContactDTO.getBytes());
        outputStream.close();
    }

    private void createContact(HttpExchange ex) throws IOException {
        InputStream inputStream = ex.getRequestBody();
        BufferedReader bufferedInputReader = new BufferedReader(new InputStreamReader(inputStream));
        ContactDTO contactDTO = parseInput(bufferedInputReader);
        contactService.createContact(contactDTO);

        OutputStream outputStream = ex.getResponseBody();
        ex.sendResponseHeaders(201, 0);
        outputStream.close();
    }

    private void patchContact(HttpExchange ex) throws IOException {
        InputStream bodyStream = ex.getRequestBody();
        BufferedReader bufferedBodyReader = new BufferedReader(new InputStreamReader(bodyStream));
        ContactDTO contactDTO = parseInput(bufferedBodyReader);
    }

    private void deleteContact(HttpExchange ex) {
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

    private Map<String, String> parseQuery(String query) {
        String[] params = query.split("&");
        Map<String, String> paramsMap = new HashMap();

        for (String param : params) {
            String[] entry = param.split("=");
            paramsMap.put(entry[0], entry[1]);
        }

        return paramsMap;
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
