Feature: Trabajador edita el estado de una reserva
  Como trabajador de una aerolínea, para aprobar o rechazar una reserva tras haberla supervisado, quiero poder editar el campo estado de una reserva para un vuelo dado que haya creado el mismo trabajador

  Scenario: Editar una reserva (Positivo)
    Given estoy iniciado sesión como "airline1"
    When accedo a la vista de de edición de la reserva con id "1" del vuelo con referencia "R-01" y la cambio a "cancelled"
    Then encuentro un resultado coincidiendo con mi vuelo con referencia "R-01" y estado "cancelled"

  Scenario: Editar una reserva (Negativo)
    Given estoy iniciado sesión como "airline1"
    When intento acceder a la vista de de edición de la reserva con id "2" del vuelo con referencia "R-02"
    Then se me redirige a la vista de excepción
