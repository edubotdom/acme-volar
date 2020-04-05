Feature: Cliente consulta vuelo disponible
  Como cliente, para conocer los detalles de vuelos que me pueden interesar reservar, quiero poder obtener toda la información relativa a un vuelo disponible, incluyendo destino, origen, aeropuerto, aerolínea, avión, aerolínea, entre otros

  Scenario: Consulta de vuelo (Positivo)
    Given estoy iniciado sesión como "client1"
    When selecciono un vuelo con referencia "R-01"
    Then soy redirigido a la vista del vuelo con referencia "R-01"

  Scenario: Consulta de vuelo (Negativo)
    Given estoy iniciado sesión como "client1"
    When accedo a la vista un vuelo pasado con id "12"
    Then soy redirigido al listado de vuelos
