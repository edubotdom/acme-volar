Feature: Actualizar aeropuertos
   Como airline, puedo actualizar aeropuertos, para mantenerlos actualizados en el sistema
  
  Scenario: Actualizar aeropuertos como airline (Positive)
    Given estoy iniciado sesión como "airline1"
    When actualizo un aeropuerto con nombre "nombre1" con nuevas coordenadas "55" y "55"
    Then se me redirige a la vista con detalles de aeropuerto "nombre1" y coordenadas "55.0" y "55.0"

   Scenario: Actualizar aeropuertos como airline (Negative)
    Given estoy iniciado sesión como "airline1"
    When actualizo un aeropuerto con nombre "nombre1" con nuevas coordenadas "-500" y "-500"
    Then se me redirige a la vista de creación de aeropuerto con errores "tiene que estar entre -90 y 90" y "tiene que estar entre -180 y 180"