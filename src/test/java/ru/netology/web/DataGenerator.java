package ru.netology.web;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private static final Faker faker = new Faker(new Locale("en"));

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private DataGenerator() {
    }

    static void request(RegistrationInfo userData) {
        given()
                .spec(requestSpec)
                .body(userData)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static class Registration {
        private Registration() {
        }

        public static String generateLogin() {
            return faker.name().username();
        }

        public static String generatePassword() {
            return faker.internet().password();
        }

        public static RegistrationInfo activeUser() {
            RegistrationInfo userData = new RegistrationInfo(generateLogin(), generatePassword(), "active");
            request(userData);
            return userData;
        }

        public static RegistrationInfo blockedUser() {
            RegistrationInfo userData = new RegistrationInfo(generateLogin(), generatePassword(), "blocked");
            request(userData);
            return userData;
        }
    }
}
