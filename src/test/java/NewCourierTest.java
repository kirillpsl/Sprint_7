import courier.Courier;
import courier.CourierMethods;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
    /*
    Создание курьера
    Проверь:
    1. курьера можно создать;
    2. нельзя создать двух одинаковых курьеров;
    3. чтобы создать курьера, нужно передать в ручку все обязательные поля;
    4. запрос возвращает правильный код ответа;
    5. успешный запрос возвращает ok: true;
    6. если одного из полей нет, запрос возвращает ошибку;
    7. если создать пользователя с логином, который уже есть, возвращается ошибка.
     */

public class NewCourierTest {
    private static final String login = "testNewCur1";
    private static final String password = "1234";
    private static final String firstName = "Бородач";
    String id = null;

    @Test
    @DisplayName("Курьера можно создать")
    @Description("Создание курьера \n \n  1. курьера можно создать; \n 4. запрос возвращает правильный код ответа; \n 5. успешный запрос возвращает ok: true;")
    public void createCourier() {
        Courier courier = new Courier(login, password, firstName);
        Response response = CourierMethods.createCourier(courier);
        id = CourierMethods.loginCourier(courier).then().extract().path("id").toString();
        response.then().assertThat().statusCode(201)
                .and()
                .body("ok", equalTo(true));
         }

    @Test
    @DisplayName("Запрос с повторяющимся логином")
    @Description("Создание курьера \n \n  2. нельзя создать двух одинаковых курьеров; \n 7. если создать пользователя с логином, который уже есть, возвращается ошибка.")
    public void doubleCourier() {
        Courier courier = new Courier(login, password, firstName);
        CourierMethods.createCourier(courier);
        Response response = CourierMethods.createCourier(courier);
        id = CourierMethods.loginCourier(courier).then().extract().path("id").toString();
        response.then().assertThat().statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой.")); // если создать пользователя с логином, который уже есть, возвращается ошибка.
    }

    @Test
    @DisplayName("Запрос без логина")
    @Description("Создание курьера \n \n  3. чтобы создать курьера, нужно передать в ручку все обязательные поля; \n 6. если одного из полей нет, запрос возвращает ошибку;")
    public void newCourierWithoutLogin() {
        Courier courier = new Courier("", password, firstName);
        Response response = CourierMethods.createCourier(courier);
        response.then().assertThat().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи")); // если одного из полей нет, запрос возвращает ошибку;
    }

    @Test
    @DisplayName("Запрос без пароля")
    @Description("Создание курьера \n \n  3. чтобы создать курьера, нужно передать в ручку все обязательные поля; \n 6. если одного из полей нет, запрос возвращает ошибку;")
    public void newCourierWithoutPassword() {
        Courier courier = new Courier(login, "", firstName);
        Response response = CourierMethods.createCourier(courier);
        response.then().assertThat().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи")); // если одного из полей нет, запрос возвращает ошибку;
    }

    @After
    public void delCourier() {
        CourierMethods.delCourier(id);
    }
}