package com.BackPrimeflix.controller;

import com.BackPrimeflix.constants.ResponseCode;
import com.BackPrimeflix.constants.WebConstants;
import com.BackPrimeflix.exception.*;
import com.BackPrimeflix.response.AlertObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//specific controller to handler exceptions and prepare the appropriate response to the client
//this controller prepares client error code
@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    //User Exception Handler
    @ExceptionHandler(AccountCustomException.class)
    public ResponseEntity<Object> accountExceptionHandler(AccountCustomException e, WebRequest request) {
        //create a alert object
        AlertObject alertObject = new AlertObject();
        alertObject.setAlertCode(e.getMessage());
        alertObject.setAlertMessage("ex message");
        alertObject.setAlertType(AlertObject.alertTypeEnum.ERROR.toString());

        //return response
        return new ResponseEntity<>(alertObject, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> userNotFoundExceptionHandler(UsernameNotFoundException e, WebRequest request) {
        //ceate a alert object
        AlertObject alertObject = new AlertObject();
        alertObject.setAlertCode(e.getMessage());
        alertObject.setAlertMessage("ex message");
        alertObject.setAlertType(AlertObject.alertTypeEnum.ERROR.toString());

        //return response
        return new ResponseEntity<>(alertObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MovieCustomException.class)
    public ResponseEntity<Object> movieExceptionHandler(MovieCustomException e, WebRequest request) {
        //ceate a alert object
        AlertObject alertObject = new AlertObject();
        alertObject.setAlertCode(e.getMessage());
        alertObject.setAlertMessage("ex message");
        alertObject.setAlertType(AlertObject.alertTypeEnum.ERROR.toString());

        //return response
        return new ResponseEntity<>(alertObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartItemCustomException.class)
    public ResponseEntity<Object> CartItemExceptionHandler(CartItemCustomException e, WebRequest request) {
        //ceate a alert object
        AlertObject alertObject = new AlertObject();
        alertObject.setAlertCode(e.getMessage());
        alertObject.setAlertMessage("ex message");
        alertObject.setAlertType(AlertObject.alertTypeEnum.ERROR.toString());

        //return response
        return new ResponseEntity<>(alertObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DiscountCustomException.class)
    public ResponseEntity<Object> DiscountExceptionHandler(DiscountCustomException e, WebRequest request) {
        //ceate a alert object
        AlertObject alertObject = new AlertObject();
        alertObject.setAlertCode(e.getMessage());
        alertObject.setAlertMessage(ResponseCode.SAVE_DISCOUNT_ERROR_MESSAGE);
        alertObject.setAlertType(AlertObject.alertTypeEnum.ERROR.toString());

        //return response
        return new ResponseEntity<>(alertObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderCustomException.class)
    public ResponseEntity<Object> OrderExceptionHandler(OrderCustomException e, WebRequest request) {
        //ceate a alert object
        AlertObject alertObject = new AlertObject();
        alertObject.setAlertCode(e.getMessage());
        alertObject.setAlertMessage(ResponseCode.ORDER_ERROR);
        alertObject.setAlertMessage(ResponseCode.ORDER_ERROR);
        alertObject.setAlertType(AlertObject.alertTypeEnum.ERROR.toString());

        //return response
        return new ResponseEntity<>(alertObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MovieStock0Exception.class)
    public ResponseEntity<Object> MovieStock0ExceptionHandler(MovieStock0Exception e, WebRequest request) {
        //ceate a alert object
        AlertObject alertObject = new AlertObject();
        alertObject.setAlertCode(e.getMessage());
        alertObject.setAlertMessage(WebConstants.STOCK_0);
        alertObject.setAlertMessage(WebConstants.STOCK_0);
        alertObject.setAlertType(AlertObject.alertTypeEnum.ERROR.toString());

        //return response
        return new ResponseEntity<>(alertObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InventoryCustomException.class)
    public ResponseEntity<Object> InventoryExceptionHandler(InventoryCustomException e, WebRequest request) {
        //ceate a alert object
        AlertObject alertObject = new AlertObject();
        alertObject.setAlertCode(e.getMessage());
        alertObject.setAlertMessage(ResponseCode.INVENTORY_SAVED_ERROR);
        alertObject.setAlertMessage(ResponseCode.INVENTORY_SAVED_ERROR);
        alertObject.setAlertType(AlertObject.alertTypeEnum.ERROR.toString());

        //return response
        return new ResponseEntity<>(alertObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PersonCrudCustomException.class)
    public ResponseEntity<Object> PersonCrudExceptionHandler(PersonCrudCustomException e, WebRequest request) {
        //ceate a alert object
        AlertObject alertObject = new AlertObject();
        alertObject.setAlertCode(e.getMessage());
        alertObject.setAlertMessage(ResponseCode.PERSON_CRUD_ERROR);
        alertObject.setAlertMessage(ResponseCode.PERSON_CRUD_ERROR);
        alertObject.setAlertType(AlertObject.alertTypeEnum.ERROR.toString());

        //return response
        return new ResponseEntity<>(alertObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddressDontExistCustomException.class)
    public ResponseEntity<Object> AddressDontExistExceptionHandler(AddressDontExistCustomException e, WebRequest request) {
        //ceate a alert object
        AlertObject alertObject = new AlertObject();
        alertObject.setAlertCode(e.getMessage());
        alertObject.setAlertMessage(ResponseCode.ADDRESS_DONT_EXIST);
        alertObject.setAlertMessage(ResponseCode.ADDRESS_DONT_EXIST);
        alertObject.setAlertType(AlertObject.alertTypeEnum.ERROR.toString());

        //return response
        return new ResponseEntity<>(alertObject, HttpStatus.NOT_FOUND);
    }
}
