package contact;

import java.io.*;
import java.util.HashMap;
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
                deleteContacts(ex);
                break;
            default:
                System.out.println("respond with something for the rest of http methods");
        }
    }

    private void getContact(HttpExchange ex) throws IOException {
        HashMap<String, String> paramsMap = parseQuery(ex.getRequestURI().getQuery());
        int id = Integer.parseInt(paramsMap.get("id"));
        ContactDTO contactDTO = contactService.getContact(id);

        // TODO: is it better to stringify an object before converting it to byte stream?
        ObjectMapper mapper = new ObjectMapper();
        byte[] serializedContactDTO = mapper.writeValueAsBytes(contactDTO);
        OutputStream outputStream = ex.getResponseBody();
        ex.sendResponseHeaders(200, serializedContactDTO.length);
        outputStream.write(serializedContactDTO);
        ex.close();
    }

    private void createContact(HttpExchange ex) throws IOException {
        InputStream inputStream = ex.getRequestBody();
        BufferedReader bufferedInputReader = new BufferedReader(new InputStreamReader(inputStream));
        ContactDTO contactDTO = parseInput(bufferedInputReader);
        contactService.createContact(contactDTO);

        OutputStream outputStream = ex.getResponseBody();
        String successMsg = "Contact has been created.";
        ex.sendResponseHeaders(201, successMsg.length());
        outputStream.write(successMsg.getBytes());
        ex.close();
    }

    private void patchContact(HttpExchange ex) throws IOException {
        InputStream inputStream = ex.getRequestBody();
        BufferedReader bufferedInputReader = new BufferedReader(new InputStreamReader(inputStream));
        ContactDTO contactDTO = parseInput(bufferedInputReader);
    }

    private void deleteContacts(HttpExchange ex) throws IOException {
        InputStream inputStream = ex.getRequestBody();
        BufferedReader bufferedInputStream = new BufferedReader(new InputStreamReader(inputStream));
        String stringifiedInput = stringifyInput(bufferedInputStream);

        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> inputMap = mapper.readValue(stringifiedInput, Map.class);
        String identifier = (String) inputMap.get("identifier");

        int contactsDeleted = contactService.deleteContacts(identifier);
        OutputStream outputStream = ex.getResponseBody();
        if (contactsDeleted > 0) {
            String successMsg = "Number of deleted contacts: %s".formatted(contactsDeleted);
            ex.sendResponseHeaders(200, successMsg.length());
            outputStream.write(successMsg.getBytes());
        } else {
            String failMeg = "There is no matching contact to delete.";
            ex.sendResponseHeaders(404, failMeg.length());
            outputStream.write(failMeg.getBytes());
        }

        ex.close();
    }

    private ContactDTO parseInput(BufferedReader bufferedReader) throws IOException {
        String stringifiedInput = stringifyInput(bufferedReader);

        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> inputMap = mapper.readValue(stringifiedInput, Map.class);

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

    private HashMap<String, String> parseQuery(String query) {
        String[] params = query.split("&");
        HashMap<String, String> paramsMap = new HashMap();

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

        return stringifiedBody.toString();
    }
}
