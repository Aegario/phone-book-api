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

    private void createContact(HttpExchange ex) throws IOException {
        InputStream inputStream = ex.getRequestBody();
        BufferedReader bufferedInputReader = new BufferedReader(new InputStreamReader(inputStream));
        ContactDTO contactDTO = ContactUtil.parseInput(bufferedInputReader);
        contactService.createContact(contactDTO);

        OutputStream outputStream = ex.getResponseBody();
        String successMsg = "Contact has been created.";
        ex.sendResponseHeaders(201, successMsg.length());
        outputStream.write(successMsg.getBytes());
        ex.close();
    }

    private void getContact(HttpExchange ex) throws IOException {
        HashMap<String, String> paramsMap = ContactUtil.parseQuery(ex.getRequestURI().getQuery());
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

    private void patchContact(HttpExchange ex) throws IOException {
        InputStream inputStream = ex.getRequestBody();
        BufferedReader bufferedInputReader = new BufferedReader(new InputStreamReader(inputStream));
        ContactDTO contactDTO = ContactUtil.parseInput(bufferedInputReader);

        contactService.patchContact(contactDTO);

        ex.close();
    }

    private void deleteContacts(HttpExchange ex) throws IOException {
        InputStream inputStream = ex.getRequestBody();
        BufferedReader bufferedInputStream = new BufferedReader(new InputStreamReader(inputStream));
        String stringifiedInput = ContactUtil.stringifyInput(bufferedInputStream);

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
}
