import courier.Courier;
import courier.CourierMethods;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

/*
С методом DELETE можно работать так же, как с другими методами.
Проверь:
неуспешный запрос возвращает соответствующую ошибку;
успешный запрос возвращает ok: true;
если отправить запрос без id, вернётся ошибка;
если отправить запрос с несуществующим id, вернётся ошибка.
 */
public class DeleteCourierTest {
    private static final String login = "testNewCur1";
    private static final String password = "1234";
    private static final String firstName = "Бородач";
    private static final String notFoundId = "123123123";

    String id = null;
    @Before
    public void createCourier() {
        Courier courier = new Courier(login, password);
        CourierMethods.createCourier(courier);
        id = CourierMethods.loginCourier(courier).then().extract().path("id").toString();
    }

//    @Test
//    @DisplayName("Неуспешный запрос возвращает соответствующую ошибку;")
//    @Description("запрос без id;")
//    public void deleteCourierBadRequest() {
//        Response response = CourierMethods.delCourier("1");
//        response.then().assertThat().statusCode(400)
//                .and()
//                .assertThat().body("message", equalTo("Недостаточно данных для удаления курьера"));
//    }


    @Test
    @DisplayName("Неуспешный запрос возвращает соответствующую ошибку;")
    @Description("запрос с не существующим id;")
    public void deleteCourierNotFoundId() {
        Response response = CourierMethods.delCourier(notFoundId);
        response.then().assertThat().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Курьера с таким id нет."));
    }

    @Test
    @DisplayName("Успешный запрос")
    @Description("успешный запрос возвращает ok: true;")
    public void deleteCourier() {
        Courier courier = new Courier(login, password, firstName);
        CourierMethods.createCourier(courier);
        id = CourierMethods.loginCourier(courier).then().extract().path("id").toString();
        Response response = CourierMethods.delCourier(id);
        response.then().assertThat().statusCode(200)
                .and()
                .body("ok", CoreMatchers.equalTo(true));
    }

    @After
    public void delCourier() {
        if (id != null){
            CourierMethods.delCourier(id);}
    }
}
