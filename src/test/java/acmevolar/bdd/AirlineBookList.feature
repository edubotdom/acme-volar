Feature: Trabajador lista reservas
  Como trabajador de una aerolínea, para conocer los que han realizado una reserva para un vuelo dado, quiero mostrar las reservas realizadas por los clientes para dicho vuelo en concreto creado por el trabajador de la aerolínea

  Scenario: Listar de reservas (Positivo)
    Given estoy iniciado sesión como "airline1"
    When accedo a mi listado de reservas
    Then encuentro un resultado coincidiendo con mi vuelo con referencia "R-01" y estado "approved"

  Scenario: Listar de reservas (Negativo)
    Given estoy iniciado sesión como "airline1"
    When accedo a mi listado de reservas
    Then no encuentro el vuelo ajeno a mi con referencia "R-02"
