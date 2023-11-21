import courier.Courier;
import courier.CourierMethods;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import orders.Orders;
import orders.OrdersMethods;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Test;

/*
Принять заказ
Проверь:
успешный запрос возвращает ok: true;
если не передать id курьера, запрос вернёт ошибку;
если передать неверный id курьера, запрос вернёт ошибку;
если не передать номер заказа, запрос вернёт ошибку;
если передать неверный номер заказа, запрос вернёт ошибку.
 */
public class AcceptOrderTest {

    private static final String login = "testNewCur1";
    private static final String password = "1234";
    private static final String firstName = "Бородач";
    String trackOrder;
    String idOrder;
    String courierId;

    @Test
    @DisplayName("Orders - Принять заказ")
    @Description("успешный запрос возвращает ok: true;")
    public void acceptOrder() {
        Courier courier = new Courier(login, password, firstName);
        CourierMethods.createCourier(courier);
        courierId =  CourierMethods.loginCourier(courier).then().extract().path("id").toString();

        Orders order = new Orders("Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha");
        OrdersMethods.newOrder(order);
        trackOrder = OrdersMethods.newOrder(order).then().extract().path("track").toString();

        idOrder = OrdersMethods.getOrdersId(trackOrder).then().extract().path("order.id").toString();
        Response response = OrdersMethods.acceptOrder(idOrder, courierId);
        response.then().assertThat().statusCode(200)
                .and()
                .body("ok", CoreMatchers.equalTo(true));
    }

    @Test
    @DisplayName("Orders - Принять заказ")
    @Description("если не передать id курьера, запрос вернёт ошибку;")
    public void acceptOrderNullCourierId() {
        courierId = "";

        Orders order = new Orders("Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha");
        OrdersMethods.newOrder(order);
        trackOrder = OrdersMethods.newOrder(order).then().extract().path("track").toString();

        idOrder = OrdersMethods.getOrdersId(trackOrder).then().extract().path("order.id").toString();

        Response response = OrdersMethods.acceptOrder(idOrder, courierId);
        response.then().assertThat().statusCode(400)
                .and()
                .body("message", CoreMatchers.equalTo("Недостаточно данных для поиска"));
    }

    @Test
    @DisplayName("Orders - Принять заказ")
    @Description("если передать неверный id курьера, запрос вернёт ошибку;")
    public void acceptOrderIncorrectCourierId() {
        courierId = "0000000";

        Orders order = new Orders("Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha");
        OrdersMethods.newOrder(order);
        trackOrder = OrdersMethods.newOrder(order).then().extract().path("track").toString();

        idOrder = OrdersMethods.getOrdersId(trackOrder).then().extract().path("order.id").toString();

        Response response = OrdersMethods.acceptOrder(idOrder, courierId);
        response.then().assertThat().statusCode(404)
                .and()
                .body("message", CoreMatchers.equalTo("Курьера с таким id не существует"));
    }

    @Test
    @DisplayName("Orders - Принять заказ")
    @Description("если не передать номер заказа, запрос вернёт ошибку;")
    public void acceptOrderNullIdOrder() {
        Courier courier = new Courier(login, password, firstName);
        CourierMethods.createCourier(courier);
        courierId = CourierMethods.loginCourier(courier).then().extract().path("id").toString();

        Response response = OrdersMethods.acceptOrder(courierId);
        response.then().assertThat().statusCode(400)
                .and()
                .body("message", CoreMatchers.equalTo("Недостаточно данных для поиска"));
    }

    @Test
    @DisplayName("Orders - Принять заказ")
    @Description("если передать неверный номер заказа, запрос вернёт ошибку.")
    public void acceptOrderIncorrectIdOrder() {
        Courier courier = new Courier(login, password, firstName);
        CourierMethods.createCourier(courier);
        courierId = CourierMethods.loginCourier(courier).then().extract().path("id").toString();

        idOrder = "00000000000000000000";

        Response response = OrdersMethods.acceptOrder(idOrder, courierId);
        response.then().assertThat().statusCode(404)
                .and()
                .body("message", CoreMatchers.equalTo("Заказа с таким id не существует"));
    }

    @After
    public void cancelOrderAndCourier() {
        if (courierId != null && trackOrder != null ){
        OrdersMethods.cancelOrder(trackOrder);
        CourierMethods.delCourier(courierId);}
    }

}
