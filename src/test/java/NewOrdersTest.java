import io.restassured.response.Response;
import orders.Orders;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import orders.OrdersMethods;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.notNullValue;

/*
Создание заказа
Проверь, что когда создаёшь заказ:
можно указать один из цветов — BLACK или GREY;
можно указать оба цвета;
можно совсем не указывать цвет;
тело ответа содержит track.
Чтобы протестировать создание заказа, нужно использовать параметризацию.
 */

@RunWith(Parameterized.class)
public class NewOrdersTest {

    private final String colour;
    String track;

    public NewOrdersTest(String colour) {
        this.colour = colour;
    }

    @Parameterized.Parameters (name = "colour = ''{0}''")
    public static Object[] getColour() {
        return new Object[][]{
                //можно указать один из цветов — BLACK или GREY;
                {"BLACK"},
                {"GREY"},
                //можно указать оба цвета;
                {"BLACK GREY"},
                //можно совсем не указывать цвет;
                {""}
        };
    }

    @Test
    @DisplayName("Проверка цвета")
    @Description("Проверь, что когда создаёшь заказ:\n " +
            "можно указать один из цветов — BLACK или GREY;\n" +
            "можно указать оба цвета;\n" +
            "можно совсем не указывать цвет;\n" +
            "тело ответа содержит track.")
    public void testColour() {
        Orders order = new Orders("Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5, "2020-06-06", "Saske, come back to Konoha", new String[]{colour});
        Response response = OrdersMethods.newOrder(order);
        track = response.then().extract().path("track").toString();
        response.then()
                .assertThat().statusCode(201)
                .and()
                .assertThat().body("track", notNullValue());
    }

    @After
    public void cancelOrder() {
        OrdersMethods.cancelOrder(track);
    }
}
