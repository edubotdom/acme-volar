Feature: Trabajador actualiza aviones
    Como trabajador de una aerolínea, para mantener actualizados los datos de mantenimiento de los aviones de mi aerolínea,
  quiero poder actualizar los detalles acerca de un avión que pertenezca a mi aerolínea

  Scenario: Actualizar un avión (Positiva)
    Given estoy iniciado sesión como "airline1"
    When modifico la fecha de mantenimiento a "2018-10-10" del avión con referencia "V14-5"
    Then soy redirigido a la vista del avion con referencia "V14-5", donde la fecha de mantenimiento es "2018-10-10[ 00:00:00.0]"
 
  Scenario: Actualizar un avión (Negativa)
    Given estoy iniciado sesión como "airline1"
    When modifico la fecha de mantenimiento a "2028-10-10" del avión con referencia "V14-5"
    Then soy redirigido a la vista de actualizar avión con errores "You must introduce a past date."
