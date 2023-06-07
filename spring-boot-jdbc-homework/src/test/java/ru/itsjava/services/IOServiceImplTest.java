package ru.itsjava.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class IOServiceImplTest {

    @Configuration
    static class MyConfiguration {

        public static final String CONTROL_INPUT_STRING = "Hello"; // строка, которую будем вводить в конструктор через byteArrayInputStream
        public static final Integer CONTROL_INPUT_INT = 1;
        private final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(CONTROL_INPUT_STRING.getBytes()); // input stream для конструктора
//        private final ByteArrayInputStream byteArrayInputStreamInt = new ByteArrayInputStream(new byte[]{CONTROL_INPUT_INT.byteValue()}); // input stream для конструктора

        @Bean // конфигурируем бин, на котором будем вызывать проверяемый метод input()
        public IOService ioService() {
            System.out.println("Конструктор из теста ioService ");
            return new IOServiceImpl(byteArrayInputStream);
        }
    }

    @Autowired
    private IOService ioService;

    @Test
    @DisplayName("Проверка input()")
    public void shouldHaveCorrectMethodInput() {
        assertEquals(MyConfiguration.CONTROL_INPUT_STRING, ioService.input());
    }

//    @Test
//    @DisplayName("Проверка inputInt()")
//    public void shouldHaveCorrectMethodInputInt() {
//        assertEquals(MyConfiguration.CONTROL_INPUT_INT, ioService.inputInt());
//    }

}
