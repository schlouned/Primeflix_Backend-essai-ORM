package com.BackPrimeflix.controller;

import com.BackPrimeflix.constants.ResponseCode;
import com.BackPrimeflix.constants.WebConstants;
import com.BackPrimeflix.dto.OrderDto;
import com.BackPrimeflix.exception.AddressDontExistCustomException;
import com.BackPrimeflix.exception.MovieStock0Exception;
import com.BackPrimeflix.exception.OrderCustomException;
import com.BackPrimeflix.exception.UserNotAllowedException;
import com.BackPrimeflix.model.*;
import com.BackPrimeflix.response.*;
import com.BackPrimeflix.util.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = WebConstants.ALLOWED_URL)
@RestController
@RequestMapping("/order")
public class OrderController {
    //members
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private DeliveryTypeService deliveryTypeService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderStateService orderStateService;
    @Autowired
    private MovieService movieService;

    //methods
    @PostMapping("/saveOrder")
    public ResponseEntity<OrderResponse> saveOrder(@RequestBody String json, Authentication auth) {
        //members
        OrderResponse resp = new OrderResponse();
        AlertObject alertObject = new AlertObject();
        OrderEntity orderEntity = new OrderEntity();
        Float deliveryCost = 0.0f;
        Float cartCost = 0.0f;
        Float totalCost = 0.0f;

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        try {
            //check if order already exist
            if(loggedUser.getOpenCart().getOrderEntity() != null){
                orderEntity = loggedUser.getOpenCart().getOrderEntity();
            }
            //we have the user
            //recover his cart
            CartEntity cartEntity = loggedUser.getOpenCart();
            //recover the delivery type
            DeliveryTypeEntity deliveryTypeEntity = deliveryTypeService.findDeliveryTypeEntityByCode(json);
            //calculate delivery cost if needed
            if (deliveryTypeEntity.getCode().equals(WebConstants.DELIVERY_CODE)) {
                deliveryCost = orderService.calculateDeliveryCost(loggedUser.getEmail());
            }
            //calculate cart cost
            cartCost = orderService.calculateCartCost(loggedUser);
            //calculate total cost
            totalCost = deliveryCost + cartCost;

            //create order
            orderEntity.setCartEntity(cartEntity);
            orderEntity.setDate(new Date());
            orderEntity.setCartCost(cartCost);
            orderEntity.setDeliveryCost(deliveryCost);
            orderEntity.setTotalCost(totalCost);
            orderEntity.setDeliveryTypeEntity(deliveryTypeEntity);
            OrderStateEntity orderStateEntity = orderStateService.findOrderStateEntityByCode(WebConstants.ORDER_STATE_VALIDATED);
            orderEntity.setOrderStateEntity(orderStateEntity);

            //try
            cartEntity.setOrderEntity(orderEntity);
            orderEntity.setCartEntity(cartEntity);
            cartEntity = cartService.save(cartEntity);

            //persist in the database
            OrderEntity savedOrder = cartEntity.getOrderEntity();
            //close the cart ????
            /*cartEntity.setOrderEntity(savedOrder);
            cartEntity.setOpenStatus(false);
            cartService.save(cartEntity);*/


            //prepare the response
            OrderDto orderDto = orderService.convertToOrderDto(savedOrder);
            resp.setOrderDto(orderDto);

            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.ORDER_SUCCESS);
            alertObject.setAlertCode(ResponseCode.ORDER_SUCCESS);
            alertObject.setAlertMessage(ResponseCode.ORDER_SUCCESS_MESSAGE);
            alertObject.setAlertType(AlertObject.alertTypeEnum.SUCCESS.toString());

        }
        //if error
        catch (Exception e) {
            if (e.getMessage().equals(WebConstants.STOCK_0))
                throw new MovieStock0Exception(WebConstants.STOCK_0);
            else if(e.getMessage().equals(ResponseCode.ADDRESS_DONT_EXIST))
                throw new AddressDontExistCustomException(ResponseCode.ADDRESS_DONT_EXIST);
            else
                throw new OrderCustomException(e.getMessage());
        }
        //set the alert object
        resp.setAlertObject(alertObject);

        //send response
        return new ResponseEntity<OrderResponse>(resp, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getOrderById")
    public ResponseEntity<OrderResponse> getOrderById(Authentication auth, String id) {
        //members
        OrderResponse resp = new OrderResponse();
        AlertObject alertObject = new AlertObject();

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        try {
            //recover cart
            //convert cart to dto
            OrderEntity order = orderService.findOrderEntityById(Long.parseLong(id));
            resp.setOrderDto(orderService.convertToOrderDto(order));
            //set the response and add the map to the response
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.GET_ORDER_SUCCESS);
        } catch (Exception e) {
            throw new OrderCustomException(ResponseCode.GET_ORDER_ERROR);
        }
        //return the response
        return new ResponseEntity<OrderResponse>(resp, HttpStatus.OK);
    }

    @PostMapping("/deleteOrder")
    public ResponseEntity<OrderResponse> deleteOrder(@RequestBody String json, Authentication auth) {
        //members
        OrderResponse resp = new OrderResponse();
        AlertObject alertObject = new AlertObject();
        OrderEntity orderEntity = new OrderEntity();

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        try {
            //recover order and cart
            OrderEntity order = orderService.findOrderEntityById(Long.parseLong(json));
            CartEntity cart = order.getCartEntity();
            //delete order
            orderService.delete(order);
            //set the response and add the map to the response
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.DELETE_ORDER_SUCCESS);
            alertObject.setAlertCode(ResponseCode.DELETE_ORDER_SUCCESS);
            alertObject.setAlertMessage(ResponseCode.DELETE_ORDER_SUCCESS_MESSAGE);
            alertObject.setAlertType(AlertObject.alertTypeEnum.SUCCESS.toString());
        } catch (Exception e) {
            throw new OrderCustomException(ResponseCode.DELETE_ORDER_ERROR);
        }
        //return the response
        return new ResponseEntity<OrderResponse>(resp, HttpStatus.OK);
    }

    @PostMapping("/changeOrderStatusToPayed")
    public ResponseEntity<OrderResponse> changeOrderStatus(@RequestBody String json, Authentication auth) {
        //members
        OrderResponse resp = new OrderResponse();
        AlertObject alertObject = new AlertObject();
        OrderEntity orderEntity = new OrderEntity();
        CartEntity cart = new CartEntity();

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        //check if user allowed to do this
        if (!userService.checkUserRoleAllowed(loggedUser, WebConstants.USER_CLIENT_ROLE))
            throw new UserNotAllowedException("USER_NOT_ALLOWED");

        try {
            //recover order and cart
            orderEntity = orderService.findOrderEntityById(Long.parseLong(json));
            //recover status
            OrderStateEntity orderStateEntity = orderStateService.findOrderStateEntityByCode(WebConstants.ORDER_STATE_PAYED);
            orderEntity.setOrderStateEntity(orderStateEntity);
            //persist in DB
            orderService.save(orderEntity);

            cart = orderEntity.getCartEntity();
            //close the cart
            cart.setOrderEntity(orderEntity);
            cart.setOpenStatus(false);
            cartService.save(cart);
            //reduce movie quantity
            Set<CartItemEntity> cartItemEntities = cart.getCartItemEntities();
            for (CartItemEntity item : cartItemEntities) {
                //recover the movie
                MovieEntity movie = item.getMovie();
                movieService.movieReduceQuantity(movie, item.getQuantity());
            }
            //check if the order status is "PAYED" if true create a new cart
            orderEntity = orderService.findOrderEntityById(Long.parseLong(json));
            if (orderEntity.getOrderStateEntity().equals(orderStateEntity)) {
                CartEntity cartEntity = new CartEntity();
                cartEntity.setUser(loggedUser);
                cartEntity.setOpenStatus(true);
                cartService.save(cartEntity);
            }

            //prepare response
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.ORDER_STATUS_PAYED_SUCCESS);
        } catch (Exception e) {
            throw new OrderCustomException(ResponseCode.ORDER_STATUS_PAYED_ERROR);
        }

        //return the response
        return new ResponseEntity<OrderResponse>(resp, HttpStatus.OK);
    }

    @GetMapping("/getOrdersByUserId")
    public ResponseEntity<OrderResponse> getOrderByUserId(Authentication auth) {
        //members
        OrderResponse resp = new OrderResponse();
        AlertObject alertObject = new AlertObject();

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        try {
            //recover orders
            List<OrderEntity> orders = orderService.findOrderByUser(loggedUser);
            //convert to dto
            List<OrderDto> ordersDto = new ArrayList<>();
            for(OrderEntity order: orders){
                ordersDto.add(orderService.convertToOrderDto(order));
            }
            //convert prepare response
            resp.setOrders(ordersDto);
            //set the response and add the map to the response
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.GET_ORDER_SUCCESS);
        } catch (Exception e) {
            throw new OrderCustomException(ResponseCode.GET_ORDER_ERROR);
        }
        //return the response
        return new ResponseEntity<OrderResponse>(resp, HttpStatus.OK);
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<OrderResponse> getAllOrders(Authentication auth) {
        //members
        OrderResponse resp = new OrderResponse();
        AlertObject alertObject = new AlertObject();

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        try {
            //recover orders
            List<OrderEntity> orders = orderService.findAllOrders();
            //convert to dto
            List<OrderDto> ordersDto = new ArrayList<>();
            for(OrderEntity order: orders){
                ordersDto.add(orderService.convertToOrderDto(order));
            }
            //prepare response
            resp.setOrders(ordersDto);
            //set the response and add the map to the response
            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.GET_ORDER_SUCCESS);
        } catch (Exception e) {
            throw new OrderCustomException(ResponseCode.GET_ORDER_ERROR);
        }
        //return the response
        return new ResponseEntity<OrderResponse>(resp, HttpStatus.OK);
    }

    @PostMapping("/orderNextStatus")
    public ResponseEntity<OrderResponse> orderNextStatus(@RequestBody String json, Authentication auth) {
        //members
        OrderResponse resp = new OrderResponse();
        AlertObject alertObject = new AlertObject();
        OrderEntity orderEntity = new OrderEntity();

        //check authentication (if not send exception)
        UserEntity loggedUser = userService.getByEmail(auth.getName());
        if (loggedUser == null) {
            throw new UsernameNotFoundException("USER_NOT_EXIST");
        }
        try {
            //recover the order
            OrderEntity order = orderService.findOrderEntityById(Long.parseLong(json));
            //change the status
            orderService.orderNextStatus(order);

            resp.setStatus(ResponseCode.SUCCESS_CODE);
            resp.setMessage(ResponseCode.ORDER_STATUS_CHANGED_SUCCESS);
            alertObject.setAlertCode(ResponseCode.ORDER_STATUS_CHANGED_SUCCESS);
            alertObject.setAlertMessage(ResponseCode.ORDER_STATUS_CHANGED_SUCCESS_MESSAGE);
            alertObject.setAlertType(AlertObject.alertTypeEnum.SUCCESS.toString());
        } catch (Exception e) {
            throw new OrderCustomException(ResponseCode.ORDER_STATUS_CHANGED_ERROR);
        }
        //return the response
        return new ResponseEntity<OrderResponse>(resp, HttpStatus.OK);
    }



}
