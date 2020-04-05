Feature: Eliminar aeropuertos
   Como airline, puedo eliminar aeropuertos, para no dejar constancia de ellos
  
  Scenario: Eliminar pista como airline (Positivo)
    Given estoy iniciado sesión como "airline1"
    When elimino una pista con referencia "A-06" del aeropuerto con nombre "Sevilla Airport"
    Then no se encuentra en la vista del listado de pistas la referencia "A-06"
    
   Scenario: Eliminar pista como airline (Negativo)
    Given estoy iniciado sesión como "airline1"
    When elimino una pista con vuelos asociados con referencia "A-01" del aeropuerto con nombre "Sevilla Airport"
    Then se me redirige a la vista de excepción

