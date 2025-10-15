# language: es

@ConsultarTransaccionPSE
Característica: Consultar el estado de una transacción de pago PSE en Wompi

  Como comercio que realiza pagos vía PSE
  Quiero consultar el estado de una transacción
  Para verificar si ha sido aprobada o declinada

  Antecedentes:
    Dado que existe un pago creado previamente

  @ConsultaExitosa
  Escenario: Consulta exitosa de transacción
    Cuando el usuario consulta el estado de la transacción
    Entonces el sistema debe retornar un código de estado 200
    Y el estado de la transacción debe ser "APPROVED" o "DECLINED"