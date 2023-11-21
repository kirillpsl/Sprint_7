import courier.Courier;
import courier.CourierMethods;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/*
Логин курьера
Проверь:
курьер может авторизоваться;
для авторизации нужно передать все обязательные поля;
система вернёт ошибку, если неправильно указать логин или пароль;
если какого-то поля нет, запрос возвращает ошибку;
если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
успешный запрос возвращает id.
 */

public class LoginCourierTest {
    private static final String login = "testNewCur2";
    private static final String nonexistentLogin = "456non-existent123";
    private static final String password = "1234";
    private static final String nonexistentPassword = "456non-existent123";
    String id;

    @Test
    @DisplayName("Успешный логин")
    @Description("Логин курьера в системе \n \n Курьер может авторизоваться; \n успешный запрос возвращает id.")
    public void createCourier() {
        Courier courier = new Courier(login, password);
        CourierMethods.createCourier(courier);
        id = CourierMethods.loginCourier(courier).then().extract().path("id").toString();
        Response response = CourierMethods.loginCourier(courier);
        response.then().assertThat().statusCode(200)
                .and()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Запрос без логина")
    @Description("Логин курьера в системе \n \n для авторизации нужно передать все обязательные поля; \n если какого-то поля нет, запрос возвращает ошибку;")
    public void loginCourierWithoutLogin() {
        Courier courier = new Courier("", password);
        Response response = CourierMethods.loginCourier(courier);
        response.then().assertThat().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа")); // если какого-то поля нет, запрос возвращает ошибку;
    }

    @Test
    @DisplayName("Запрос без пароля")
    @Description("Логин курьера в системе \n \n для авторизации нужно передать все обязательные поля; \n если какого-то поля нет, запрос возвращает ошибку;")
    public void loginCourierWithoutPassword() {
        Courier courier = new Courier(login, "");
        Response response = CourierMethods.loginCourier(courier);
        response.then().assertThat().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа")); // если какого-то поля нет, запрос возвращает ошибку;
    }

    @Test
    @DisplayName("Запрос с не существующим логином")
    @Description("Логин курьера в системе \n \n если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;")
    public void loginCourierNotCorrectLogin() {
        Courier courier = new Courier(nonexistentLogin, password);
        Response response = CourierMethods.loginCourier(courier);
        response.then().assertThat().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Запрос с не существующим паролем")
    @Description("Логин курьера в системе \n \n если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;")
    public void loginCourierNotCorrectPassword() {
        Courier courier = new Courier(login, nonexistentPassword);
        Response response = CourierMethods.loginCourier(courier);
        response.then().assertThat().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void delCourier() {
        if (id != null){
        CourierMethods.delCourier(id);}
    }
}