# language: es

@CrearTransacción
Característica: Crear una transacción de pago en Wompi

  Como comercio que realiza pagos
  Quiero que el sistema gestione correctamente las transacciones
  Para poder procesarlas de manera segura y confiable

  Antecedentes:
    Dado que el comercio tiene configuradas las llaves de autenticación


  @CrearTransacciónExitosaPSE
  Escenario: Transacción exitosa con PSE
    Cuando el usuario realiza una transacción de pago vía PSE con datos correctos
    Entonces la respuesta debe tener el código 201
    Y la transacción debe tener un estado "PENDING" o "APPROVED"

  @CrearTransacciónExitosaNEQUI
  Escenario: Transacción exitosa con NEQUI
    Cuando el usuario realiza una transacción de pago vía NEQUI con datos correctos
    Entonces la respuesta debe tener el código 201

  @TransacciónConLlaveInvalida
  Escenario: Transacción con llave privada inválida
    Cuando el usuario intenta realizar una transacción con llave incorrecta
    Entonces la respuesta debe tener el código 401

  @TransacciónConReferenciaDuplicada
  Escenario: Transacción con referencia que ya ha sido usada
    Cuando el usuario envía una transacción con referencia duplicada
    Entonces la respuesta debe tener el código 422
    Y el mensaje de error debe contener "La referencia ya ha sido usada" en la clave "reference"

  @TransacciónConMontoNoPermitido
  Escenario: Transacción con monto menor al mínimo permitido
    Cuando el usuario intenta realizar una transacción con monto menor a 1500
    Entonces la respuesta debe tener el código 422
    Y el mensaje de error debe contener "El monto mínimo de una transacción es $1,500 exceptuando impuestos" en la clave "valid_amount_in_cents"