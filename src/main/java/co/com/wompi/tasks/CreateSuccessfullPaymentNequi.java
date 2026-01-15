package co.com.wompi.tasks;

import co.com.wompi.models.PaymentRequest;
import co.com.wompi.utils.Constants;
import co.com.wompi.utils.SignatureUtils;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

import static co.com.wompi.utils.Constants.PRIVATE_KEY;

public class CreateSuccessfullPaymentNequi implements Task {
    @Override
    @Step("Crea una transaccion exitosa con Nequi")
    public <T extends Actor> void performAs(T actor) {
        String token = SignatureUtils.getAcceptanceToken();

        PaymentRequest payment = new PaymentRequest();
        payment.amount_in_cents = 2500000;
        payment.currency = "COP";
        payment.customer_email = "prueba.testing@yopmail.com";
        payment.reference = "REF-WOMPI-" + System.currentTimeMillis();
        payment.acceptance_token = token;
        payment.signature = SignatureUtils.generateSignature(payment.reference, payment.amount_in_cents, payment.currency);

        PaymentRequest.PaymentMethodNequi nequi = new PaymentRequest.PaymentMethodNequi();
        nequi.phone_number = "3991111111";
        nequi.payment_description = "Probando pago a Tienda Wompi con Nequi";

        payment.payment_method = nequi;

        SerenityRest
                .given()
                .baseUri(Constants.BASE_URL)
                .contentType("application/json")
                .header("Authorization", "Bearer "+ PRIVATE_KEY)
                .body(payment)
                .post("/transactions")
                .then()
                .log().all();
    }

    public static CreateSuccessfullPaymentNequi transaction() {
        return Tasks.instrumented(CreateSuccessfullPaymentNequi.class);
    }

}
