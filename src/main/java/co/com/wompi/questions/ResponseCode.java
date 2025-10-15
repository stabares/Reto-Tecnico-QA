package co.com.wompi.questions;


import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ResponseCode implements Question<Boolean> {
    private final int expectedCode;

    public ResponseCode(int expectedCode) {
        this.expectedCode = expectedCode;
    }

    public static ResponseCode is(int expectedCode) {
        return new ResponseCode(expectedCode);
    }

    @Override
    public Boolean answeredBy(Actor actor) {
        return SerenityRest.lastResponse().statusCode() == expectedCode;
    }
}
