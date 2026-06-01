package org.iu.vorlesung.asset_management_application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/*
 * Mit @RestControllerAdvice kann in Spring Boot eine zentrale Fehlerbehandlung erfolgen.
 * Die Annotation kombiniert @ControllerAdvice mit @ResponseBody, wodurch alle Antworten
 * automatisch als JSON serialisiert werden.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /*
     * Pro zu behandelnder Exception kann hier eine dedizierte Behandlungsart hinterlegt werden.
     * In dieser können wir u. a. den korrekten HTTP-Statuscode für die Antwort festlegen und
     * weitere Informationen, die der Client zur Erklärung des Fehlers (bspw. "Feld darf nicht
     * leer sein") benötigen könnte, mitsenden.
     */

    /*
     * RuntimeException ist ziemlich allgemein und fängt alles ab, was die anderen Methoden nicht behandeln.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /*
     * Die MessageNotReadableException wird geworfen, wenn bspw. ein Wert im Request-Body nicht den
     * korrekten Typ besitzt.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", ex.getMessage());

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("errors", errors);
        response.put("message", "Ungültiger Typ der Eingabe");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
     * Die MethodArgumentNotValidException wird bei Validierungsfehlern (@Valid) geworfen.
     * Über getAllErrors() kommen wir an die Feldnamen, für die die Validierung fehlgeschlagen
     * ist.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("errors", errors);
        response.put("message", "Ungültige Eingaben");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}