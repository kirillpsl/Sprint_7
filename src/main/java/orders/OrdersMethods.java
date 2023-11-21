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


    @Step("Получение списка заказов")
    public static Response getOrdersList () {
        return given()
                .header("Content-type", "application/json")
                .get(BASE_URL + ORDER_PATH);
    }

    @Step("Получить заказ по его номеру")
    public static Response getOrdersId(String trackOrder){
        return given()
                .header("Content-type", "application/json")
                .queryParam("t", trackOrder)
                .get(BASE_URL + ORDER_PATH + "track");
    }

    @Step("Принять заказ")
    public static Response acceptOrder(String idOrder, String courierId) {
        return given()
                .queryParam("courierId", courierId)
                .put(BASE_URL + ACCEPT_ORDER_PATH + "{idOrder}", idOrder);
    }

    //Лучше не придумал, данный метод для проверки теста - если не передать номер заказа, запрос вернет ошибку
    @Step("Принять заказ")
    public static Response acceptOrder(String courierId) {
        return given()
//                .queryParam("courierId", courierId)
                .put(BASE_URL + ACCEPT_ORDER_PATH + "{courierId}", courierId);
    }
}
