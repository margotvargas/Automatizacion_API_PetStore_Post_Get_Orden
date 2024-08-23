package com.nttdata.steps;

import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class GestionarOrden {

    private static final String STORE_API_URL = "https://petstore.swagger.io/v2/store/order";

    //@Step("Crear una orden en PetStore")
    //Aqui añado una nota, ya que en los campos que eran distintos a String (como id,petId, cantidad que eran int) no me reconocia en el feature el step
    // (no se subrayaba en azul ni vinculaba al colocarlos segun correspondian)
    //asi que los trabaje todas mlas variables como string ya que me estaba demorando mucho en buscar una solución
    public void crearOrden(String id, String petId, String quantity, String shipDate, String status, String complete) {
        SerenityRest.given()
                .contentType("application/json")
                .relaxedHTTPSValidation()
                .body("{\n" +
                        "  \"id\": " + id + ",\n" +
                        "  \"petId\": " + petId + ",\n" +
                        "  \"quantity\": " + quantity + ",\n" +
                        "  \"shipDate\": \"" + shipDate + "\",\n" +
                        "  \"status\": \"" + status + "\",\n" +
                        "  \"complete\": " + complete + "\n" +
                        "}")
                .log().all()
                .post(STORE_API_URL)
                .then()
                .log().all();
    }

    //@Step("Consultar una orden en PetStore")
    public void consultarOrden(String id) {
        SerenityRest.given()
                .contentType("application/json")
                .relaxedHTTPSValidation()
                .log().all()
                .get(STORE_API_URL + "/" + id)
                .then()
                .log().all();
    }

    @Step("Validar el código de respuesta") //Este step se reutilizó en ambos escenarios
    public void validarCodigoRespuesta(int statusCode) {
        restAssuredThat(response -> response.statusCode(statusCode));
    }

    @Step("Validar el estado de la orden") //assertion del escenario 1 crear orden
    public void validarEstadoOrden(String status) {
        restAssuredThat(response -> response.body("status", equalTo(status)));
    }



    //Estos 2 steps son los assertion del 2do escenario Consultar Orden
    public void validarIdOrden(String id) {  //Validamos que ID coincida con el resultado
        int id_integer  = Integer.parseInt(id); //aqui si tuve que convertir el campo string (que lineas arriba explico) convirtiendolos a int para que los valide correctamente con el json
        restAssuredThat(response -> response.body("id", equalTo(id_integer)));
    }

    public void validarCantidadOrden(String quantity) { //Validamos que la cantidad coincida con el resultado
        int quantity_integer = Integer.parseInt(quantity); //lo mismo tuve que convertir a int también, sino no funcionaba mi assertion :(
        restAssuredThat(response -> response.body("quantity", equalTo(quantity_integer)));
    }
}
