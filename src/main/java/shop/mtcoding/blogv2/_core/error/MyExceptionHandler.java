package shop.mtcoding.blogv2._core.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String error(RuntimeException e) {
        return Scrpt.back(e.getMessage());

    }
}
