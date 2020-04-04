Feature: Cliente reserva vuelo disponible
   Como cliente, para reservar un vuelo, quiero poder realizar una reserva de un vuelo que se encuentre disponible

  Scenario: Creación de reserva (Positiva)
    Given estoy iniciado sesión como "client1"
    When creo una cantidad de "2" reservas del vuelo con referencia "R-01"
    Then soy redirigido a la vista de mis reservas, donde está mi reserva de "2" asientos para el vuelo "R-01"

  Scenario: Creación de reserva (Negativa)
    Given estoy iniciado sesión como "client1"
    When creo una cantidad de "-5000" reservas del vuelo con referencia "R-01"
    Then soy redirigido a la vista de creación de la reserva con errores "tiene que ser mayor o igual que 1"