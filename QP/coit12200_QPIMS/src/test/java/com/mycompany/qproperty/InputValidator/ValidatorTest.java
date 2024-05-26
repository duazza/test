/*
Purpose: Unit test InputValidator methods
Author: Tarique Turnbull
 */
package com.mycompany.qproperty.InputValidator;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 * @author tqt13
 */
public class ValidatorTest {

    public ValidatorTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }


    Validator validator = new Validator();
    @Test()
    public void testValidateName() {
        String valid = "Tarique";
        List<String> invalid = List.of(
                "",
                "Tar ique",
                " ",
                "-",
                "x".repeat(46)
        );

        assertDoesNotThrow(() -> {
            validator.validateName(valid);
        });
        for (String name : invalid) {
            assertThrows(InvalidInputException.class, () -> {
                validator.validateName(name);
            });
        }
    }

    @Test
    public void testValidateEmail() {
        String valid = "abc123@gmail.com";
        List<String> invalid = List.of(
                "",
                "abc123mail",
                "x".repeat(46),
                "@mailabc123",
                "abc123@gmail"
        );

        assertDoesNotThrow(() -> {
            validator.validateEmail(valid);
        });
        for (String email : invalid) {
            assertThrows(InvalidInputException.class, () -> {
                validator.validateEmail(email);
            });
        }
    }

    @Test
    public void testValidateUsername() {
        String valid = "useRGuy_3321";
        List<String> invalid = List.of(
                "",
                " ",
                "user name",
                "x".repeat(46)
        );
        assertDoesNotThrow(() -> {
            validator.validateUsername(valid);
        });
        for (String username : invalid) {
            assertThrows(InvalidInputException.class, () -> {
                validator.validateUsername(username);
            });
        }
    }

    @Test
    public void testValidatePassword() {
        String valid = "This_is_a_really_long_passphrase_!234";
        List<String> invalid = List.of(
                "",
                " ",
                "This is a really long passphrase !234",
                "pass word",
                "x".repeat(256)
        );

        assertDoesNotThrow(() -> {
            validator.validatePassword(valid);
        });
        for (String password : invalid) {
            assertThrows(InvalidInputException.class, () -> {
                validator.validatePassword(password);
            });
        }
    }
    @Test
    public void testValidatePhone() {
        String valid = "0411111111";
        System.out.println(valid.length());
        List<String> invalid = List.of(
                "",
                " ",
                "04 11111111",
                "04 1",
                "04-1111111",
                "0411a11111"
        );
        assertDoesNotThrow(() -> {
            validator.validatePhoneNumber(valid);
        });
        for (String phone : invalid) {
            assertThrows(InvalidInputException.class, () -> {
                validator.validatePhoneNumber(phone);
            });
        }
    }

    @Test
    public void testValidateStreet() {
        String valid = "63 Queens Street";
        String invalid = "x".repeat(46);

        assertDoesNotThrow(() -> {
            validator.validateStreet(valid);
        });
        assertThrows(InvalidInputException.class, () -> {
            validator.validateStreet(invalid);
        });
    }

    @Test
    public void testValidateCity() {
        String valid = "Brisbane";
        String invalid = "x".repeat(46);

        assertDoesNotThrow(() -> {
            validator.validateStreet(valid);
        });
        assertThrows(InvalidInputException.class, () -> {
            validator.validateStreet(invalid);
        });
    }
}
