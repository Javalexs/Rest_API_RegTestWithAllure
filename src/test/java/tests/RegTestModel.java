package tests;

import lombok.DataLombokCheckAcc;

import lombok.LombokCheck;
import lombok.LombokEnterData;
import lombok.LombokEnterReg;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class RegTestModel {
    @Test
    @DisplayName("Проверка существования аккаунта с логами")
    void checkNameUserswithLog(){
        DataLombokCheckAcc data = given()
                .log().uri()
                .contentType(JSON)
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(DataLombokCheckAcc.class);

        assertThat(data.getData().getEmail()).isEqualTo("janet.weaver@reqres.in");
        assertThat(data.getData().getFirstName()).isEqualTo("Janet");
    }

    @Test
    @DisplayName("Проверка существования аккаунта с данной электронной почтой")
    void checkUnLogin() {
        LombokEnterReg enter = new LombokEnterReg();
        enter.setEmail("peter@klaven");
        LombokCheck data = given()
                .log().uri()
                .contentType(JSON)
                .body(enter)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .extract().as(LombokCheck.class);

        assertEquals(data.getEmail(), null);
    }


    @Test
    @DisplayName("Проверка присвоения токена при запросе")
    void checkRegisterSuccessful(){
        LombokEnterReg enter = new LombokEnterReg();
        enter.setEmail("eve.holt@reqres.in");
        enter.setPassword("pistol");
        LombokCheck data = given()
                .log().uri()
                .contentType(JSON)
                .body(enter)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LombokCheck.class);

        assertThat(data.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    @DisplayName("Проверка создания нового аккаунта")
    void createUser() {
        LombokEnterData ent = new LombokEnterData();
        ent.setName("morpheus");
        ent.setJob("leader");

        LombokCheck data = given()
                .log().uri()
                .contentType(JSON)
                .body(ent)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(LombokCheck.class);

        assertThat(data.getName()).isEqualTo("morpheus");


    }
    @Test
    @DisplayName("Проверка запроса данных у несуществующего аккаунта")
    void checkUnknownUser() {
        LombokCheck data = given()
                .log().uri()
                .contentType(JSON)
                .when()
                .get("https://reqres.in/api/users/16")
                .then()
                .log().status()
                .log().body()
                .statusCode(404)
                .extract().as(LombokCheck.class);
        assertEquals(data.getName(), null);

    }
}
