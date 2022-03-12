package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.UserInfo;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthorizationTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
    }

    @Test
    void shouldSuccessfulLoginRegisteredUser() {
        UserInfo user = DataGenerator.Registration.getUser("active");
        DataGenerator.sendRequest(user);
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[id='root']").shouldBe(Condition.exactText("Личный кабинет"));
    }

    @Test
    void shouldLoginBlockedUser() {
        UserInfo user = DataGenerator.Registration.getUser("blocked");
        DataGenerator.sendRequest(user);
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldBe(Condition.exactText("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldInvalidLogin() {
        UserInfo user = DataGenerator.Registration.getUser("active");
        DataGenerator.sendRequest(user);
        var anotherLogin = DataGenerator.getRandomLogin();
        $("[data-test-id='login'] input").setValue(anotherLogin);
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldBe(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldInvalidPassword() {
        UserInfo user = DataGenerator.Registration.getUser("active");
        DataGenerator.sendRequest(user);
        var anotherPassword = DataGenerator.getRandomPassword();
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(anotherPassword);
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldBe(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldLoginNotRegisteredUser() {
        var notRegisteredUser = DataGenerator.Registration.getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldBe(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
    }
}
