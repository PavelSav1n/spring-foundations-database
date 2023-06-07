package ru.itsjava.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class IOServiceImplTest {

    @Configuration
    static class MyConfiguration {

        public static final String CONTROL_INPUT_STRING = "Hello"; // строка, которую будем вводить в конструктор через byteArrayInputStream
        public static final String CONTROL_INPUT_INT = "1"; // В консоли мы вводим не int, а string, поэтому тут тоже строка
        public static final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(CONTROL_INPUT_STRING.getBytes()); // input stream для конструктора
        public static final ByteArrayInputStream byteArrayInputStreamInt = new ByteArrayInputStream(CONTROL_INPUT_INT.getBytes()); // input stream для конструктора

        @Bean // конфигурируем бин, на котором будем вызывать проверяемый метод input()
        public IOService ioServiceString() {
            System.out.println("Конструктор ioServiceString из теста IOServiceImplTest");
            return new IOServiceImpl(byteArrayInputStream);
        }

        @Bean // конфигурируем бин, на котором будем вызывать проверяемый метод input()
        public IOService ioServiceInt() {
            System.out.println("Конструктор ioServiceInt из теста IOServiceImplTest");
            return new IOServiceImpl(byteArrayInputStreamInt);
        }
    }

    @Autowired
    private IOService ioServiceString;
    @Autowired
    private IOService ioServiceInt;

    @Test
    @DisplayName("Проверка input()")
    public void shouldHaveCorrectMethodInput() {
        assertEquals(MyConfiguration.CONTROL_INPUT_STRING, ioServiceString.input());
    }

    @Test
    @DisplayName("Проверка inputInt()")
    public void shouldHaveCorrectMethodInputInt() {
        assertEquals(1, ioServiceInt.inputInt());
    }

}
