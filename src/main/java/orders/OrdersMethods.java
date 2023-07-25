package orders;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static constants.URL.*;
import static io.restassured.RestAssured.given;

public class OrdersMethods {
    @Step("Создание заказа")
    public static Response newOrder (Orders oreder) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(oreder)
                .when()
                .post(BASE_URL + ORDER_PATH);
    }

    @Step("Отмена заказа")
    public static void cancelOrder (String track) {
         given()
                .header("Content-type", "application/json")
                .queryParams("track", track)
                .when()
                .put(BASE_URL + CANCEL_ORDER_PATH);
    }

    @Step("Получение списка заказов.")
    public static Response getOrdersList () {
        return given()
                .header("Content-type", "application/json")
                .get(BASE_URL + ORDER_PATH);
    }
}
