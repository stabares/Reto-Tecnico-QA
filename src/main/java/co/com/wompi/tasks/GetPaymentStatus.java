package co.com.wompi.tasks;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Get;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static co.com.wompi.utils.Constants.PAYMENT_JSON_PATH;
import static co.com.wompi.utils.Constants.PRIVATE_KEY;

public class GetPaymentStatus implements Task {
    private String paymentId;

    public static GetPaymentStatus transaction() {
        return Tasks.instrumented(GetPaymentStatus.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        // Lee el payment.json directamente
        Path path = Paths.get(PAYMENT_JSON_PATH);
        try {
            String content = Files.readString(path);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(content);
            paymentId = root.get("data").get("id").asText(); // <-- Accede al id que esta dentro de data
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo leer el archivo payment.json", e);
        }

        // Ejecuta GET con el ID
        actor.attemptsTo(
                Get.resource("/transactions/{id}")
                        .with(request -> request.pathParam("id", paymentId)
                                .contentType("application/json")
                                .header("Authorization", "Bearer " + PRIVATE_KEY))
        );
    }
}
