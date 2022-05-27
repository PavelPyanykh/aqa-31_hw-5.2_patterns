package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.DataGenerator.Registration.*;

public class AuthTest {
    @BeforeEach
    void setupTest() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitForm() {
        RegistrationInfo activeUserData = activeUser();
        $("[data-test-id='login'] input").setValue(activeUserData.getLogin());
        $("[data-test-id='password'] input").setValue(activeUserData.getPassword());
        $("[data-test-id='action-login']").click();
        $(".heading").shouldHave(text("Личный кабинет!")).shouldBe(visible);
    }

    @Test
    void shouldNotSubmitBlockedUser() {
        RegistrationInfo blockedUserData = blockedUser();
        $("[data-test-id='login'] input").setValue(blockedUserData.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUserData.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(text("Ошибка! Пользователь заблокирован")).shouldBe(visible);
    }

    @Test
    void shouldNotSubmitInvalidLogin() {
        String invalidLoginData = generateLogin();
        RegistrationInfo activeUserData = activeUser();
        $("[data-test-id=login] input").setValue(invalidLoginData);
        $("[data-test-id='password'] input").setValue(activeUserData.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldNotSubmitInvalidPassword() {
        String invalidPasswordData = generatePassword();
        RegistrationInfo activeUserData = activeUser();
        $("[data-test-id='login'] input").setValue(activeUserData.getLogin());
        $("[data-test-id=password] input").setValue(invalidPasswordData);
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }
}
