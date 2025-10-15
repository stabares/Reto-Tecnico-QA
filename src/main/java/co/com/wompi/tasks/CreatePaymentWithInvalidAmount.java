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

public class CreatePaymentWithInvalidAmount implements Task {
    @Override
    @Step("Intenta una transaccion con un monto invalido (Menor al permitido)")
    public <T extends Actor> void performAs(T actor) {
        String token = SignatureUtils.getAcceptanceToken();

        PaymentRequest payment = new PaymentRequest();
        payment.amount_in_cents = 500;
        payment.currency = "COP";
        payment.customer_email = "PruebasQA@test.com";
        payment.reference = "REF-WOMPI-" + System.currentTimeMillis();
        payment.acceptance_token = token;
        payment.signature = SignatureUtils.generateSignature(payment.reference, payment.amount_in_cents, payment.currency);

        PaymentRequest.PaymentMethod method = new PaymentRequest.PaymentMethod();
        method.type = "PSE";
        method.user_type = 0;
        method.user_legal_id = "123456789";
        method.user_legal_id_type = "CC";
        method.financial_institution_code = "1";
        method.payment_description = "Probando pago a Tienda Wompi";

        payment.payment_method = method;

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

    public static CreatePaymentWithInvalidAmount transaction() {
        return Tasks.instrumented(CreatePaymentWithInvalidAmount.class);
    }
}
