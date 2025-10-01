package com.example.bookstore.validator;

import org.junit.jupiter.api.Test;

import com.example.bookstore.util.BookValidator;

import static org.assertj.core.api.Assertions.*;

public class BookValidatorTest {

    @Test
    public void testValidIsbn_10digit() {
        assertThat(BookValidator.isValidIsbn("1234567890")).isTrue();
    }

    @Test
    public void testValidIsbn_13digit() {
        assertThat(BookValidator.isValidIsbn("9781234567890")).isTrue();
    }

    @Test
    public void testInvalidIsbn_Null() {
        assertThat(BookValidator.isValidIsbn(null)).isFalse();
    }

    @Test
    public void testInvalidIsbn_Short() {
        assertThat(BookValidator.isValidIsbn("1234567")).isFalse();
    }

    @Test
    public void testInvalidIsbn_ContainsLetters() {
        assertThat(BookValidator.isValidIsbn("97812abc78901")).isFalse();
    }

    @Test
    public void testInvalidIsbn_SpecialCharacters() {
        assertThat(BookValidator.isValidIsbn("97812@#$78901")).isFalse();
    }
}
