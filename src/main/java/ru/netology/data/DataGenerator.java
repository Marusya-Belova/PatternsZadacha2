package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import jdk.jfr.ContentType;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private DataGenerator() {

    }

    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static Faker faker = new Faker();

    public static void sendRequest(UserInfo user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String getRandomLogin() {
        return faker.name().username();
    }

    public static String getRandomPassword() {
        return faker.internet().password();
    }

    public static class Registration {
        private Registration() {

        }

        public static UserInfo getUser(String status) {
            UserInfo user = new UserInfo(
                    getRandomLogin(),
                    getRandomPassword(),
                    status
            );
            return user;
        }

        public static UserInfo getRegisteredUser(String status) {
            var registeredUser = getUser(status);
            sendRequest(registeredUser);

            return registeredUser;
        }

    }

}