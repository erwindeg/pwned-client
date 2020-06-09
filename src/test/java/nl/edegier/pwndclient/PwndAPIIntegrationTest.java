package nl.edegier.pwndclient;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PwndAPIIntegrationTest {

    @Test
    public void testHaveIBeenPwned() {
        PwndAPI pwndAPI = new PwndAPI();
        assertTrue(pwndAPI.haveIBeenPwned("password"), "should return 'true'");
    }
}
