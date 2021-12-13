package com.BackPrimeflix.controller;

import com.BackPrimeflix.constants.ResponseCode;
import com.BackPrimeflix.constants.WebConstants;
import com.BackPrimeflix.dto.CartItemDto;
import com.BackPrimeflix.exception.CartCustomException;
import com.BackPrimeflix.exception.CartItemCustomException;
import com.BackPrimeflix.exception.UserNotAllowedException;
import com.BackPrimeflix.model.CartEntity;
import com.BackPrimeflix.model.CartItemEntity;
import com.BackPrimeflix.model.MovieEntity;
import com.BackPrimeflix.model.UserEntity;
import com.BackPrimeflix.response.*;
import com.BackPrimeflix.util.service.CartItemService;
import com.BackPrimeflix.util.service.CartService;
import com.BackPrimeflix.util.service.MovieService;
import com.BackPrimeflix.util.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = WebConstants.ALLOWED_URL)
@RestController
@RequestMapping("/cart")
public class CartController {
    //members
    @Autowired
    CartItemService cartItemService;
    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;
    @Autowired
    MovieService movieService;

    //methods
    @PostMapping("/addToCart")
    public ResponseEntity<CartItemResponse> addToCart(@RequestBody String movieId, Authentication auth) {
        //members
        CartItemResponse resp = new CartItemResponse();
        AlertObject alertObject = new AlertObject();
        Boolean movieAlreadyInTheCart = false;

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        //check if user allowed to do this
        if (!userService.checkUserRoleAllowed(loggedUser, WebConstants.USER_CLIENT_ROLE))
            throw new UserNotAllowedException("USER_NOT_ALLOWED");

        //add the movie to the cart (create a cartItem) = a line in the cart
        try {
            //recover the movie by the id
            MovieEntity movieEntity = movieService.getMovieById(movieId);
            //check if the quantity is not 0
            if (!movieEntity.getStockQuantity().equals(0)) {
                //create a cartItem
                CartItemEntity cartItemEntity = new CartItemEntity();
                cartItemEntity.setDate(new Date());
                cartItemEntity.setMovie(movieEntity);
                //check if this film is already in the cart
                CartEntity cartEntity = cartService.getCartByUser(loggedUser);
                for (CartItemEntity cartItem : cartEntity.getCartItemEntities()) {
                    if (movieEntity == cartItem.getMovie()) {
                        movieAlreadyInTheCart = true;
                        break;
                    }
                }
                if (movieAlreadyInTheCart) {
                    //recover cartItemEntity
                    cartItemEntity = cartItemService.getCartItemEntityByMovieAndCart(movieEntity, cartEntity);
                    //recover the quantity
                    Integer quantity = cartItemEntity.getQuantity() + 1;
                    //set quantity
                    cartItemEntity.setQuantity(quantity);
                } else {
                    //set the cart
                    cartItemEntity.setCart(loggedUser.getOpenCart());
                    //set quantity to one by default
                    cartItemEntity.setQuantity(1);
                }
                //save the cartItem line
                cartItemService.save(cartItemEntity);

                //alert object
                alertObject.setAlertCode(ResponseCode.CART_ADD_MOVIE_SUCCESS);
                alertObject.setAlertMessage(ResponseCode.CART_ADD_MOVIE_SUCCESS_MESSAGE);
                alertObject.setAlertType(AlertObject.alertTypeEnum.SUCCESS.toString());
                //response
                resp.setStatus(ResponseCode.SUCCESS_CODE);
                resp.setMessage(ResponseCode.CART_ADD_MOVIE_SUCCESS);
            } else {
                //alert object
                alertObject.setAlertCode(ResponseCode.CART_STOCK_0_ERROR);
                alertObject.setAlertMessage(ResponseCode.CART_STOCK_0_ERROR_MESSAGE);
                alertObject.setAlertType(AlertObject.alertTypeEnum.ERROR.toString());
                //response
                resp.setAlertObject(alertObject);
                resp.setStatus(ResponseCode.BAD_REQUEST_CODE);
                resp.setMessage(ResponseCode.BAD_REQUEST_MESSAGE);
            }
        } catch (Exception e) {
            throw new CartItemCustomException(ResponseCode.CART_ADD_MOVIE_ERROR);
        }

        //send response
        return new ResponseEntity<CartItemResponse>(resp, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getCart")
    public ResponseEntity<CartResponse> getCart(Authentication auth) {
        //members
        CartResponse resp = new CartResponse();
        AlertObject alertObject = new AlertObject();

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        //check if user allowed to do this
        if (!userService.checkUserRoleAllowed(loggedUser, WebConstants.USER_CLIENT_ROLE))
            throw new UserNotAllowedException("USER_NOT_ALLOWED");
        try {
            //recover cart
            //convert cart to dto
            resp.setCart(cartService.convertToCartDto(cartService.getCartByUser(loggedUser)));
            //set the response and add the map to the response
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.GET_CART_SUCCESS);
        } catch (Exception e) {
            throw new CartCustomException(ResponseCode.GET_CART_ERROR);
        }
        //return the response
        return new ResponseEntity<CartResponse>(resp, HttpStatus.OK);
    }

    @PostMapping("/updateCartItem")
    public ResponseEntity<CartItemResponse> updateCartItem(@RequestBody String json, Authentication auth) {
        //members
        CartItemResponse resp = new CartItemResponse();
        AlertObject alertObject = new AlertObject();

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        //check if user allowed to do this
        if (!userService.checkUserRoleAllowed(loggedUser, WebConstants.USER_CLIENT_ROLE))
            throw new UserNotAllowedException("USER_NOT_ALLOWED");

        //update the cart item
        try {
            //convert to dto
            ObjectMapper mapper = new ObjectMapper();
            CartItemDto cartItemDto = mapper.readValue(json, CartItemDto.class);

            //recover the movie by the id
            MovieEntity movieEntity = movieService.getMovieById(cartItemDto.getMovie().getId());
            //check if the quantity is <= stock quantity
            if (cartItemDto.getQuantity() <= movieEntity.getStockQuantity()) {
                //recover the cart Item
                CartItemEntity cartItemEntity = cartItemService.getCartItemEntityById(Long.parseLong(cartItemDto.getId()));
                //set the quantity
                cartItemEntity.setQuantity(cartItemDto.getQuantity());
                //update the cartItem line
                cartItemService.save(cartItemEntity);

                //alert object
                alertObject.setAlertCode(ResponseCode.CARTITEM_UPDATE_SUCCESS);
                alertObject.setAlertMessage(ResponseCode.CARTITEM_UPDATE_SUCCESS_MESSAGE);
                alertObject.setAlertType(AlertObject.alertTypeEnum.SUCCESS.toString());
                //response
                resp.setStatus(ResponseCode.SUCCESS_CODE);
                resp.setMessage(ResponseCode.CARTITEM_UPDATE_SUCCESS);
            } else {
                //alert object
                alertObject.setAlertCode(ResponseCode.CARTITEM_UPDATE_STOCK_ERROR);
                alertObject.setAlertMessage(ResponseCode.CARTITEM_UPDATE_STOCK_ERROR_MESSAGE);
                alertObject.setAlertType(AlertObject.alertTypeEnum.ERROR.toString());
                //response
                resp.setAlertObject(alertObject);
                resp.setStatus(ResponseCode.BAD_REQUEST_CODE);
                resp.setMessage(ResponseCode.BAD_REQUEST_MESSAGE);
            }
        } catch (Exception e) {
            throw new CartItemCustomException(ResponseCode.CARTITEM_UPDATE_STOCK_ERROR);
        }

        //send response
        return new ResponseEntity<CartItemResponse>(resp, HttpStatus.ACCEPTED);
    }

    @PostMapping("/deleteCartItem")
    public ResponseEntity<CartItemResponse> deleteCartItem(@RequestBody String json, Authentication auth) {
        //members
        CartItemResponse resp = new CartItemResponse();
        AlertObject alertObject = new AlertObject();

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        //check if user allowed to do this
        if (!userService.checkUserRoleAllowed(loggedUser, WebConstants.USER_CLIENT_ROLE))
            throw new UserNotAllowedException("USER_NOT_ALLOWED");

        //delete the cart item
        try {
            //convert json to dto
            ObjectMapper mapper = new ObjectMapper();
            CartItemDto cartItemDto = mapper.readValue(json, CartItemDto.class);

            //recover the cart Item
            CartItemEntity cartItemEntity = cartItemService.getCartItemEntityById(Long.parseLong(cartItemDto.getId()));
            //delete the cartItem
            cartItemService.deleteCartItem(cartItemEntity);

            //alert object
            alertObject.setAlertCode(ResponseCode.CARTITEM_DELETED_SUCCESS);
            alertObject.setAlertMessage(ResponseCode.CARTITEM_DELETED_SUCCESS_MESSAGE);
            alertObject.setAlertType(AlertObject.alertTypeEnum.SUCCESS.toString());
            //response
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.CARTITEM_DELETED_SUCCESS);

        } catch (Exception e) {
            throw new CartItemCustomException(ResponseCode.CARTITEM_DELETED_ERROR);
        }

        //send response
        return new ResponseEntity<CartItemResponse>(resp, HttpStatus.ACCEPTED);
    }
}
