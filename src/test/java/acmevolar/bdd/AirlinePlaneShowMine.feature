Feature: Trabajador consulta aviones
  Como trabajador de una aerolínea, para consultar el estado de mantenimiento de un avión de mi aerolínea, quiero hacer una consulta para acceder a todos los datos acerca de un avión

  Scenario: Consulta de avión (Positivo)
    Given estoy iniciado sesión como "airline1"
    When selecciono un avión con referencia "V14-5"
    Then soy redirigido a la vista del avión con referencia "V14-5"

  Scenario: Consulta de avión (Negativo)
    Given estoy iniciado sesión como "airline1"
    When accedo a la vista un avión ajeno con id "90"
    Then se me redirige a la vista de excepción
