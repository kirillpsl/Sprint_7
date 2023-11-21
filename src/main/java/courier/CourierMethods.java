package courier;

import io.qameta.allure.Step;

import static constants.URL.*;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class CourierMethods {

    @Step("Создание курьера")
    public static Response createCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(BASE_URL + COURIER_PATH);
    }

    @Step("Вход курьера")
    public static Response loginCourier(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(BASE_URL + LOGIN_PATH);
    }

    @Step("Удаление курьера")
    public static Response delCourier(String courierId) {

        return given()
                 //   .body("id:" + courierId)
                .delete(BASE_URL + COURIER_PATH + "{courierId}", courierId);

    }
}