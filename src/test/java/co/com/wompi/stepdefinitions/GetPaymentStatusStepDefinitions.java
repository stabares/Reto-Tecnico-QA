package co.com.wompi.stepdefinitions;

import co.com.wompi.questions.ResponseCode;
import co.com.wompi.questions.TransactionStatusIs;
import co.com.wompi.tasks.GetPaymentStatus;
import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

import static co.com.wompi.utils.Constants.BASE_URL;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.setTheStage;

public class GetPaymentStatusStepDefinitions {
    private Actor user;

    @Before
    public void setUp() {
        setTheStage(new OnlineCast());
    }

    @Dado("que existe un pago creado previamente")
    public void pagoExistente() {
        user = Actor.named("Usuario Wompi");
        user.can(CallAnApi.at(BASE_URL));
    }

    @Cuando("el usuario consulta el estado de la transacción")
    public void consultaEstadoTransaccion() {
        user.attemptsTo(GetPaymentStatus.transaction());
    }

    @Entonces("el sistema debe retornar un código de estado {int}")
    public void validarCodigoRespuesta(int codigoEsperado) {
        user.should(seeThat(ResponseCode.is(codigoEsperado)));
    }

    @Entonces("el estado de la transacción debe ser {string} o {string}")
    public void validarEstadoTransaccion(String estado1, String estado2) {
        user.should(seeThat(TransactionStatusIs.anyOf(estado1, estado2)));
    }
}
