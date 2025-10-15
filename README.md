# Reto Tecnico QA Automation

Reto tecnico que consite en automatizar el flujo de creación y consulta de pagos PSE en la API de Wompi usando Serenity BDD + Screenplay + RestAssured.

## Arquitectura

- **models/** → Estructura de datos y payloads de las peticiones  
- **tasks/** → Acciones principales del actor (CreatePayment, GetPaymentStatus)  
- **questions/** → Validaciones de respuesta y verificación de estado  
- **utils/** → Constantes, configuración y manejo de datos  
- **features/** → Escenarios BDD en lenguaje Gherkin  
- **runners/** → Ejecución de los features con Serenity y Cucumber  
- **stepdefinitions/** → Enlace entre pasos Gherkin y el modelo Screenplay 

## Tecnologías utilizadas

- Java 17+
- Gradle
- Serenity BDD
- Cucumber
- RestAssured
- Screenplay Pattern

## Escenarios implementados

# Crear transacción PSE
- Transacción exitosa 201
- Llave inválida 401
- Referencia duplicada 422
- Monto no permitido 422

# Consultar estado de transacción
- Consulta exitosa 200 
- Verifica que el campo `"status"` sea `"APPROVED"` o `"DECLINED"`.
