package com.example.cart.controller;


import com.example.cart.dto.CartItemDto;
import com.example.cart.dto.SearchRequest;
import com.example.cart.exception.CartEmptyException;
import com.example.cart.exception.ErrorResponse;
import com.example.cart.exception.NoSuchElementFoundException;
import com.example.cart.model.Cart;
import com.example.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.UUID;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PutMapping("/addItem/{userId}")
    public Cart addNewItem(@PathVariable UUID userId, SearchRequest product) {
        return cartService.addCartItem(userId, product);
    }

    @PostMapping("/createCart/{userId}")
    public Cart createCart(@PathVariable UUID userId) {
        return cartService.createNewCart(userId);
    }

//    @PostMapping("/orderPlaced")
//    public boolean orderPlace(OrderResponse orderResponse) {
//        return cartService.emptyCart(orderResponse);
//    }

    @PutMapping("/editCartItem/{userId}")
    public Cart editItem(@PathVariable UUID userId, CartItemDto cartItemDto) {
        return cartService.editCartItem(userId,cartItemDto);
    }

    @GetMapping("/{userId}")
    public Cart getUserCart(@PathVariable UUID userId) {
        return cartService.getUserCartById(userId);
    }

    @DeleteMapping("/deleteCartItem/{userId}/{cartItemId}")
    public Cart removeCartItem(@PathVariable UUID userId, @PathVariable UUID cartItemId ){
        return cartService.removeCartItem(userId, cartItemId);
    }

    @ExceptionHandler(NoSuchElementFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleItemNotFoundException(
            NoSuchElementFoundException exception,
            WebRequest request
    ){
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(CartEmptyException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<ErrorResponse> handleCartEmptyException(
            NoSuchElementFoundException exception,
            WebRequest request
    ){
        return buildErrorResponse(exception, HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            WebRequest request
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation error. Check 'errors' field for details."
        );

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorResponse.addValidationError(fieldError.getField(),
                    fieldError.getDefaultMessage());
        }
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleAllUncaughtException(
            Exception exception,
            WebRequest request){
        return buildErrorResponse(
                exception,
                "Unknown error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            Exception exception,
            HttpStatus httpStatus,
            WebRequest request
    ) {
        return buildErrorResponse(
                exception,
                exception.getMessage(),
                httpStatus,
                request);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            Exception exception,
            String message,
            HttpStatus httpStatus,
            WebRequest request
    ) {
        ErrorResponse errorResponse = new ErrorResponse(
                httpStatus.value(),
                exception.getMessage()
        );

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

}
