package tests;

import lombok.DataLombokCheckAcc;
import lombok.LombokCheck;
import lombok.LombokEnterData;
import lombok.LombokEnterReg;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.LogSpecs.requesrSpec;
import static specs.LogSpecs.responceSpec;

public class RegTestModel {
    @Test
    @DisplayName("Проверка существования аккаунта с логами")
    void checkNameUsersWithLog(){
        DataLombokCheckAcc data = given(requesrSpec)
                .when()
                .get("/users/2")
                .then()
                .spec(responceSpec)
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
        LombokCheck data = given(requesrSpec)
                .body(enter)
                .when()
                .post("/login")
                .then()
                .spec(responceSpec)
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
        LombokCheck data = given(requesrSpec)
                .body(enter)
                .when()
                .post("/register")
                .then()
                .spec(responceSpec)
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

        LombokCheck data = given(requesrSpec)
                .body(ent)
                .when()
                .post("/users")
                .then()
                .spec(responceSpec)
                .statusCode(201)
                .extract().as(LombokCheck.class);
        assertThat(data.getName()).isEqualTo("morpheus");

    }
    @Test
    @DisplayName("Проверка запроса данных у несуществующего аккаунта")
    void checkUnknownUser() {
        LombokCheck data = given(requesrSpec)
                .when()
                .get("/users/16")
                .then()
                .spec(responceSpec)
                .statusCode(404)
                .extract().as(LombokCheck.class);
        assertEquals(data.getName(), null);
    }
}
