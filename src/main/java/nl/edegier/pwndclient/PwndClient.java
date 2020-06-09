package nl.edegier.pwndclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toMap;

public class PwndClient {

    static final String DELIM = ":";
    private final static Logger LOGGER = Logger.getLogger(PwndClient.class.getName());
    private static final String PWND_API_DEFAULT_URL = "https://api.pwnedpasswords.com/range/";
    private RestTemplate restTemplate;
    @Value("${pwndapi.url}")
    private String pwndApiUrl;

    public PwndClient() {
        this.restTemplate = new RestTemplate();
    }

    @Autowired
    public PwndClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Map<String, Integer> getHashes(String prefix) {
        return parseHashes(callPwnd(prefix));
    }

    Map<String, Integer> parseHashes(List<String> hashes) {
        return hashes.stream().map(hash -> hash.split(DELIM)).collect(toMap(s -> s[0], s -> Integer.parseInt(s[1])));
    }

    private List<String> callPwnd(String prefix) {
        ResponseEntity<String> hashesResponse = restTemplate.getForEntity(getPwndApiUrl().concat(prefix), String.class);
        if (hashesResponse.getStatusCode() == HttpStatus.OK) {
            String body = hashesResponse.getBody();
            if(body.contains("\r\n")){
                return asList(body.split("\r\n"));
            } else {
                return asList(body.split("\n"));
            }

        } else {
            LOGGER.severe("HAVEIBEENPWND return error code: " + hashesResponse.getStatusCode());
            return new ArrayList();
        }
    }

    public String getPwndApiUrl() {
        if (this.pwndApiUrl == null) {
            return PWND_API_DEFAULT_URL;
        } else {
            return this.pwndApiUrl;
        }
    }

    public void setPwndApiUrl(String pwndApiUrl) {
        this.pwndApiUrl = pwndApiUrl;
    }
}
