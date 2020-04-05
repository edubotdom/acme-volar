Feature: Trabajador registra información de aviones
   Como trabajador de una aerolínea, para poder ofrecer información de las características de los aviones que se ofrecen en mis vuelos, quiero registrar información sobre los aviones con los que opera la aerolínea
  
  Scenario: Creación de avión (Positivo)
    Given estoy iniciado sesión como "airline1"
    When creo un avion con referencia "REF-TEST" con número de asientos "100" y "10000" kilómetros
    Then soy redirigido a la vista de detalles del vuelo "REF-TEST" y tiene número de asientos "100" y "10000.0" kilómetros
    
   Scenario: Creación de avión (Negativo)
    Given estoy iniciado sesión como "airline1"
    When creo un avion con referencia "REF-TEST" con número de asientos "-100" y "-10000" kilómetros
    Then soy redirigido a la vista de creación del avión con errores "You must introduce a positive number."

  