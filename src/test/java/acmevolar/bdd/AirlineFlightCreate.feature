Feature: Trabajador registra un vuelo
   Como trabajador de una aerolínea, para tener información sobre los vuelos que ofrece mi aerolínea almacenada y poder presentársela a nuestros clientes, quiero registrar información sobre los vuelos que sobre los que opera mi aerolínea, incluyendo su información asociada
  incluyendo destino, origen, aeropuerto, aerolínea, avión, aerolínea, entre otros

  Scenario: Creación de vuelo (Positivo)
    Given estoy iniciado sesión como "airline1"
    When creo un vuelo con referencia "R-TEST" con número de asientos "20"
    Then soy redirigido a la vista de detalles del vuelo "R-TEST" y tiene número de asientos "20"

  Scenario: Creación de vuelo (Negativo)
    Given estoy iniciado sesión como "airline1"
    When creo un vuelo con referencia "R-TEST" con número de asientos "-100"
    Then soy redirigido a la vista de creación del vuelo con errores "You must specificate a number equal or higher than 0."
