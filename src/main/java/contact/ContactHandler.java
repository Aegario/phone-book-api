package contact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ContactHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch (method) {
            case "POST":
                post(exchange);
                break;
            case "GET":
                get();
                break;
            case "PATCH":
                patch();
                break;
            case "DELETE":
                delete();
                break;
            default:
                System.out.println("respond with something for the rest of http methods");
        }
    }

    private void get() {
        System.out.println("This is GET");
    }

    private void post(HttpExchange exchange) throws IOException {
        System.out.println("This is POST");
        InputStream inputStream = exchange.getRequestBody();
        parseBody(inputStream);
    }

    private void patch() {
        System.out.println("This is PATCH");
    }

    private void delete() {
        System.out.println("This is DELETE");
    }

    private void parseBody(InputStream bodyStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(bodyStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String newLineSeparator = System.getProperty("line.separator");
        StringBuilder result = new StringBuilder();
        String currentLine;
        while ((currentLine = bufferedReader.readLine()) != null) {
            if (result.length() > 0) result.append(newLineSeparator);
            result.append(currentLine);
        }

        System.out.println(result);
    }
}
