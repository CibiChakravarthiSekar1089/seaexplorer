package com.maveric.seaexplorer.controller.error;

import com.maveric.seaexplorer.controller.res.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@Slf4j
@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(ProbeException.class)
    public ResponseEntity<Error> onProbeException(ProbeException probeException){
        log.error("error happended {}", probeException);
        return ResponseEntity.badRequest().body(Error.builder()
                .message(probeException.getMessage())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity onAnyException(Exception exception){
        log.error("error happended {}", exception);
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity onHandlerMethodValidationException(){
        return ResponseEntity.badRequest().build();
    }
}
