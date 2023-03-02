package contact;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ContactUtil {
    private void  ContractUtil() {
        throw new IllegalStateException("Cannot be instantiated");
    }
    public static ContactDTO parseInput(BufferedReader bufferedReader) throws IOException {
        String stringifiedInput = stringifyInput(bufferedReader);

        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> inputMap = mapper.readValue(stringifiedInput, Map.class);
        Integer id = inputMap.get("id") != null
                ? Integer.valueOf(inputMap.get("id"))
                : null;
        String firstName = inputMap.get("firstName");
        String lastName = inputMap.get("lastName");
        String phoneNumber = inputMap.get("phoneNumber");
        String birthdate = inputMap.get("birthdate");

        return new ContactDTO(
                id,
                firstName,
                lastName,
                phoneNumber,
                birthdate
        );
    }

    public static  HashMap<String, String> parseQuery(String query) {
        String[] params = query.split("&");
        HashMap<String, String> paramsMap = new HashMap();

        for (String param : params) {
            String[] entry = param.split("=");
            paramsMap.put(entry[0], entry[1]);
        }

        return paramsMap;
    }

    public static String stringifyInput(BufferedReader bufferedBodyReader) throws IOException {
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
