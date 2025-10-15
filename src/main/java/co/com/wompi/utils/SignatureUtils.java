package co.com.wompi.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class SignatureUtils {
    public static String getAcceptanceToken() {
        Response response = RestAssured
                .given()
                .baseUri(Constants.BASE_URL)
                .get("/merchants/" + Constants.PUBLIC_KEY)
                .then()
                .extract()
                .response();

        return response.path("data.presigned_acceptance.acceptance_token");
    }

    public static String generateSignature(String reference, long amount, String currency) {
        try {
            String toHash = reference + amount + currency + Constants.INTEGRITY_KEY;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error generating signature", e);
        }
    }
}
