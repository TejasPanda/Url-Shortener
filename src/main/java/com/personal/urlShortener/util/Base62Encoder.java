package com.personal.urlShortener.util;
import org.springframework.stereotype.Component;

@Component
public class Base62Encoder {

    private static final String BASE62 =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String encode(long number) {
        StringBuilder result = new StringBuilder();

        while (number > 0) {
            result.append(BASE62.charAt((int) (number % 62)));
            number /= 62;
        }

        return result.reverse().toString();
    }
}
