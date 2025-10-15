package co.com.wompi.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import java.util.List;

public class ErrorMessageContains implements Question<Boolean> {
    private final String errorKey;
    private final String expectedMessage;

    public ErrorMessageContains(String expectedMessage, String errorKey) {
        this.errorKey = errorKey;
        this.expectedMessage = expectedMessage;
    }

    public static ErrorMessageContains text(String expectedMessage, String errorKey) {
        return new ErrorMessageContains(expectedMessage, errorKey);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        List<String> messages = SerenityRest.lastResponse()
                .jsonPath().getList("error.messages." + errorKey);

        return messages != null && messages.stream()
                .anyMatch(msg -> msg.contains(expectedMessage));
    }
}
