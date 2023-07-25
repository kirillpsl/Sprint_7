import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import orders.OrdersMethods;
import org.junit.Test;
import static org.hamcrest.Matchers.notNullValue;

/*
Список заказов
Проверь, что в тело ответа возвращается список заказов.
 */

    public class OrdersListTest {
        @Test
        @DisplayName("Orders - Получение списка заказов.")
        @Description("Проверь, что в тело ответа возвращается список заказов.")
        public void getOrder() {
            Response response = OrdersMethods.getOrdersList();
            response.then()
                    .statusCode(200)
                    .assertThat()
                    .body("orders.id",notNullValue());
        }
    }
