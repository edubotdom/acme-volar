Feature: Eliminar aeropuertos
   Como airline, puedo eliminar aeropuertos, para no dejar constancia de ellos
  
  Scenario: Eliminar aeropuertos como airline (Positive)
    Given estoy iniciado sesión como "airline1"
    When elimino un aeropuerto con nombre "nombre2"
    Then se me redirige a la vista con el listado de aeropuertos
    
   Scenario: Eliminar aeropuertos como airline (Positive)
    Given estoy iniciado sesión como "airline1"
    When elimino un aeropuerto con vuelos
    Then se me redirige a la vista de excepción

