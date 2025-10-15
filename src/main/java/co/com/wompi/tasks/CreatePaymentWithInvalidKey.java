package co.com.wompi.tasks;

import co.com.wompi.models.PaymentRequest;
import co.com.wompi.utils.Constants;
import co.com.wompi.utils.SignatureUtils;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class CreatePaymentWithInvalidKey implements Task {
    @Override
    @Step("Intenta realizar una transaccion con una llave invalida")
    public <T extends Actor> void performAs(T actor) {
        PaymentRequest payment = new PaymentRequest();
        payment.amount_in_cents = 620000;
        payment.currency = "COP";
        payment.customer_email = "prueba.testing@yopmail.com";
        payment.reference = "REF-WOMPI-INVALIDKEY";
        payment.signature = SignatureUtils.generateSignature(payment.reference, payment.amount_in_cents, payment.currency);

        SerenityRest
                .given()
                .baseUri(Constants.BASE_URL)
                .header("Authorization", "Bearer INVALID_KEY")
                .contentType("application/json")
                .body(payment)
                .post("/transactions")
                .then()
                .log().all();
    }

    public static CreatePaymentWithInvalidKey transaction() {
        return Tasks.instrumented(CreatePaymentWithInvalidKey.class);
    }
}
