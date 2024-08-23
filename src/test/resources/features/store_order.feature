Feature: Gestión de órdenes en PetStore

  @crearOrden
  Scenario Outline: Crear una nueva orden en PetStore
    Given que el API de PetStore está disponible
    When creo una orden con id "<id>", petId "<petId>", quantity "<quantity>", shipDate "<shipDate>", status "<status>", complete "<complete>"
    Then el código de respuesta es 200
    And el estado de la orden es placed "<status>"

    Examples:
      | id | petId | quantity | shipDate             | status | complete |
      | 100  | 4     | 5       | 2024-08-24T14:15:22Z | placed | true     |
      | 101  | 2     | 2       | 2024-08-23T14:15:22Z | placed | true     |

  @consultarOrden
  Scenario Outline: Consultar una orden existente en PetStore
    When deseo validar que la orden con id "<id>" existe
    Then el código de respuesta es 200
    And la orden tiene el id "<id>"
    And la cantidad de la orden es "<quantity>"

    Examples:
      | id | quantity |
      | 100  | 5       |
      | 101  | 2       |

    #NOTA: En el segundo escenario se omitió el Given, ya que era muy redundante


