Feature: Crear aeropuertos
   Como airline, puedo crear aeropuertos, para registrarlos en el sistema
  
  Scenario: Crear aeropuertos como airline (Positive)
    Given estoy iniciado sesión como "airline1"
    When creo un aeropuerto con nombre "nombre1" con coordenadas "50" y "50"
    Then se me redirige a la vista con detalles de aeropuerto "nombre1" y coordenadas "50.0" y "50.0"

   Scenario: Crear aeropuertos como airline (Negative)
    Given estoy iniciado sesión como "airline1"
    When creo un aeropuerto con nombre "nombre1" con coordenadas "-500" y "-500"
    Then se me redirige a la vista de creación de aeropuerto con errores "tiene que estar entre -90 y 90" y "tiene que estar entre -180 y 180"