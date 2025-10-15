package co.com.wompi.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import java.util.Arrays;
import java.util.List;

public class TransactionStatusIs implements Question<Boolean> {
    private final List<String> expectedStatuses;

    public TransactionStatusIs(String... statuses) {
        this.expectedStatuses = Arrays.asList(statuses);
    }

    public static TransactionStatusIs anyOf(String... statuses) {
        return new TransactionStatusIs(statuses);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        String actualStatus = SerenityRest.lastResponse().jsonPath().getString("data.status");
        return expectedStatuses.contains(actualStatus);
    }
}
