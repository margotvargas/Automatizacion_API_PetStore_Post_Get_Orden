package com.nttdata.glue;
import net.serenitybdd.rest.SerenityRest;
import com.nttdata.steps.GestionarOrden;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;


public class GestionarOrdenStepsDefs {

    @Steps
    GestionarOrden gestionarOrden;


    //PRIMER ESCENARIO: CREAR ORDEN

    // Este paso es una precondición, solo es para la verificación de disponibilidad del API
    @Given("que el API de PetStore está disponible")
    public void queElApiDePetStoreEstaDisponible() {

        SerenityRest.given()
                .relaxedHTTPSValidation()
                .when()
                .get("https://petstore.swagger.io/v2/store/inventory")
                .then()
                .statusCode(200);
    }

    @When("creo una orden con id {string}, petId {string}, quantity {string}, shipDate {string}, status {string}, complete {string}")
    public void creoUnaOrdenConIdPetIdQuantityShipDateStatusComplete(String id, String petId, String quantity, String shipDate, String status, String complete) {
        gestionarOrden.crearOrden(id, petId, quantity, shipDate, status, complete);
    }

    @Then("el código de respuesta es {int}")
    public void elCodigoDeRespuestaEs(int statusCode) {
        gestionarOrden.validarCodigoRespuesta(statusCode);
    }


    @And("el estado de la orden es placed {string}")
    public void elEstadoDeLaOrdenEsPlaced(String status) {
        gestionarOrden.validarEstadoOrden(status);
    }





    //SEGUNDO ESCENARIO: CONSULTAR ORDEN
    //No se coloco el given por ser redundante

    @When("deseo validar que la orden con id {string} existe")
    public void ValidoqueLaOrdenConIdExiste(String id) {
        gestionarOrden.consultarOrden(id);
    }

    @And("la orden tiene el id {string}")
    public void laOrdenTieneElId(String id) {
        gestionarOrden.validarIdOrden(id);
    }

    @And("la cantidad de la orden es {string}")
    public void laCantidadDeLaOrdenEs(String quantity) {
        gestionarOrden.validarCantidadOrden(quantity);
    }
}
