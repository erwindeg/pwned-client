package nl.edegier.pwndclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockPwndClient extends PwndClient {


    public Map<String, Integer> getHashes(String prefix) {
        return readFile();
    }

    private Map<String, Integer> readFile() {

        Class clazz = MockPwndClient.class;
        InputStream inputStream = clazz.getResourceAsStream("/hashes");
        try {
            return parseHashes(readFromInputStream(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    private List<String> readFromInputStream(InputStream inputStream)
            throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }
}
