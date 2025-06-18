package com.todolist.todolist.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GoblalExceptionHandle {


    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponse> handleGenericException(GenericException e, WebRequest request) {
        String caminho = request.getDescription(false).toString();
        String metodo = request.getContextPath();

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage(),
                metodo,
                caminho
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, WebRequest request) {
        String caminho = request.getDescription(false).toString();
        String metodo = request.getContextPath();

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage(),
                metodo,
                caminho
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TarefaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTarefaNotFoundException(TarefaNotFoundException e, WebRequest request) {
        String caminho = request.getDescription(false).toString();
        String metodo = request.getContextPath();

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                metodo,
                caminho
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflitoTarefaException.class)
    public ResponseEntity<ErrorResponse> handleConflitoTarefaException(ConflitoTarefaException e, WebRequest request) {
        String caminho = request.getDescription(false).toString();
        String metodo = request.getContextPath();

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                metodo,
                caminho
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }


}
