package nl.edegier.pwndclient;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.logging.Logger;

import static org.apache.commons.codec.digest.DigestUtils.sha1Hex;

public class PwndAPI {

    private final static Logger LOGGER = Logger.getLogger(PwndAPI.class.getName());

    private static final int PREFIX_LENGTH = 5;
    private PwndClient pwndClient;

    public PwndAPI(){
        this.pwndClient = new PwndClient();
    }

    @Autowired
    public PwndAPI(PwndClient pwndClient) {
        this.pwndClient = pwndClient;
    }

    public boolean haveIBeenPwned(char[] password) {
        String[] splitPassword = splitHashedPassword(hashPassword(password));
        Map<String, Integer> hashes = this.pwndClient.getHashes(splitPassword[0]);
        Integer occurances = hashes.get(splitPassword[1]);
        LOGGER.info("Password owned " + (occurances != null ? occurances : 0) + " times!");
        return occurances != null;
    }

    private String hashPassword(char[] password) {
        return sha1Hex(new String(password)).toUpperCase();
    }

    private String[] splitHashedPassword(String hashedPassword) {
        String[] result = new String[2];
        if (!hashedPassword.isEmpty()) {
            result[0] = hashedPassword.substring(0, PREFIX_LENGTH);
            result[1] = hashedPassword.substring(PREFIX_LENGTH);
        }
        return result;
    }
}
