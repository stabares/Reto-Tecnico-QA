package co.com.wompi.stepdefinitions;

import co.com.wompi.questions.ErrorMessageContains;
import co.com.wompi.questions.ResponseCode;
import co.com.wompi.questions.TransactionStatusIs;
import co.com.wompi.tasks.*;
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


public class CreatePaymentStepDefinitions {
    private Actor user;

    @Before
    public void setUp() {
        setTheStage(new OnlineCast());
    }

    @Dado("que el comercio tiene configuradas las llaves de autenticación")
    public void contextoInicial() {
        user = Actor.named("Usuario Wompi");
        user.can(CallAnApi.at(BASE_URL));
    }

    // Transacción exitosa PSE
    @Cuando("el usuario realiza una transacción de pago vía PSE con datos correctos")
    public void realizarTransaccionExitosaPse() {
        user.attemptsTo(CreateSuccessfulPayment.transaction());
    }

    // Transacción exitosa NEQUI
    @Cuando("el usuario realiza una transacción de pago vía NEQUI con datos correctos")
    public void realizarTransaccionExitosaNequi() {
        user.attemptsTo(CreateSuccessfullPaymentNequi.transaction());
    }

    // Llave inválida
    @Cuando("el usuario intenta realizar una transacción con llave incorrecta")
    public void realizarTransaccionConLlaveInvalida() {
        user.attemptsTo(CreatePaymentWithInvalidKey.transaction());
    }

    // Referencia duplicada
    @Cuando("el usuario envía una transacción con referencia duplicada")
    public void realizarTransaccionConReferenciaDuplicada() {
        user.attemptsTo(CreatePaymentWithDuplicateReference.transaction());
    }

    //Monto no permitido
    @Cuando("el usuario intenta realizar una transacción con monto menor a 1500")
    public void realizarTransaccionConMontoNoPermitido() {
        user.attemptsTo(CreatePaymentWithInvalidAmount.transaction());
    }

    //Validaciones Assertions
    @Entonces("la respuesta debe tener el código {int}")
    public void validarCodigoRespuesta(int codigoEsperado) {
        user.should(seeThat(ResponseCode.is(codigoEsperado)));
    }

    @Entonces("la transacción debe tener un estado {string} o {string}")
    public void validarEstadoTransaccion(String estado1, String estado2) {
        user.should(seeThat(TransactionStatusIs.anyOf(estado1, estado2)));
    }

    @Entonces("el mensaje de error debe contener {string} en la clave {string}")
    public void validarMensajeDeError(String mensajeEsperado, String claveError) {
        user.should(seeThat(ErrorMessageContains.text(mensajeEsperado, claveError)));
    }
}
