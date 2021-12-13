package com.BackPrimeflix.util.service;

import com.BackPrimeflix.constants.WebConstants;
import com.BackPrimeflix.controller.DistanceCalculationController;
import com.BackPrimeflix.dto.GpsCoordinatesDto;
import com.BackPrimeflix.dto.OrderDto;
import com.BackPrimeflix.dto.RoutesDto;
import com.BackPrimeflix.exception.MovieStock0Exception;
import com.BackPrimeflix.model.*;
import com.BackPrimeflix.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service("orderService")
public class ImplOrderService implements OrderService{
    //members
    @Autowired
    private DistanceCalculationController distanceCalculationController;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private OrderStateService orderStateService;

    //methods
    public String createGPSCalculationUrl(String emailUser){
        //recover user entity Address
        UserEntity userEntity = userService.getByEmail(emailUser);
        //recover the address out of this user
        AddressEntity addressEntity = userEntity.getAddressEntity();
        //create the url
        String urlUser = WebConstants.urlGPSCalculation
                + WebConstants.urlGPSStreet + addressEntity.getStreet() + " " + addressEntity.getHouseNumber()
                + WebConstants.urlGPSZipCode + addressEntity.getZipCode()
                + WebConstants.urlGPSCity + addressEntity.getCity()
                + WebConstants.urlGPSCountryCode + addressEntity.getCountryCode()
                + WebConstants.urlGPSFormat;
        //return
        return urlUser;
    }

    public Float calculateDeliveryCost(String email){
        //create the two url
        String UserUrl = createGPSCalculationUrl(email);
        String PrimeflixUrl = createGPSCalculationUrl(WebConstants.COMPANY_EMAIL);
        //create two gps point
        GpsCoordinatesDto userGps = distanceCalculationController.GetGpsFromAddress(UserUrl);
        GpsCoordinatesDto primeflixGps = distanceCalculationController.GetGpsFromAddress(PrimeflixUrl);
        //distance calculation
        RoutesDto routes = distanceCalculationController.distanceCalculation(primeflixGps, userGps);
        long distance = routes.getDistance()/(long)1000;

        //cost calculation
        Float cost;
        if(distance <= 5)
            cost = 0.0f;
        else if(distance > 5 && distance <= 20)
            cost = 5.0f;
        else if(distance > 20 && distance <= 100)
            cost = 7.0f;
        else
            cost = 10.0f;
        //return
        return cost;
    }

    public Float calculateCartCost(UserEntity userEntity) throws MovieStock0Exception {
        //members
        CartEntity cartEntity = userEntity.getOpenCart();
        Float cartCost = 0.0f;

        //calculation loop
        for(CartItemEntity cartItemEntity: cartEntity.getCartItemEntities()){
            //check if the stock is not 0
            if(!movieService.checkMovieStockQuantity(cartItemEntity.getMovie(), cartItemEntity.getQuantity())){
                throw new MovieStock0Exception(WebConstants.STOCK_0); //"movie id: " + cartItemEntity.getMovie().getId() +
            }
            Float tempCost = 0.0f;
            Float movieNetPrice = 0.0f;
            DiscountEntity discount = cartItemEntity.getMovie().getCategory().getCurrentDiscount();
            if(discount.getPercentage() != null){
                movieNetPrice = cartItemEntity.getMovie().getPrice()*(1 - cartItemEntity.getMovie().getCategory().getCurrentDiscount().getPercentage()/100.0f);
                //round
                DecimalFormat df = new DecimalFormat("0.00");
                movieNetPrice = Float.parseFloat(df.format(movieNetPrice).replace(',','.'));
            }
            else
                movieNetPrice = cartItemEntity.getMovie().getPrice();
            tempCost = cartItemEntity.getQuantity()* movieNetPrice;
            //add to total cost
            cartCost += tempCost;
        }
        //return
        return cartCost;
    }

    public OrderEntity save(OrderEntity orderEntity){
        return orderRepository.save(orderEntity);
    }

    public OrderEntity findOrderEntityById(Long id){
        return orderRepository.findOrderEntityById(id);
    }

    public OrderDto convertToOrderDto(OrderEntity orderEntity){
        //convert date
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        OrderDto orderDto = new OrderDto();
        orderDto.setId(orderEntity.getId().toString());
        orderDto.setCart(cartService.convertToCartDto(orderEntity.getCartEntity()));
        orderDto.setDate(formatter.format(orderEntity.getDate()));
        orderDto.setDeliveryType(orderEntity.getDeliveryTypeEntity().getCode());
        orderDto.setOrderState(orderEntity.getOrderStateEntity().getCode());
        orderDto.setCartCost(orderEntity.getCartCost());
        orderDto.setDeliveryCost(orderEntity.getDeliveryCost());
        orderDto.setTotalCost(orderEntity.getTotalCost());
        //return
        return orderDto;
    }

    public void delete(OrderEntity orderEntity){
        orderRepository.delete(orderEntity);
    }

    public List<OrderEntity> findOrderByUser(UserEntity userEntity){
        //members
        List<OrderEntity> orders = new ArrayList<>();
        //recover all carts
        List<CartEntity> carts = userEntity.getCarts();
        //for each cart if status is close recover the order
        for(CartEntity cart: carts){
            if(cart.getOpenStatus() == false){
                //recover the order
                OrderEntity order = cart.getOrderEntity();
                if(order != null){
                    orders.add(order);
                }
            }
        }
        //return
        return orders;
    }

    public List<OrderEntity> findAllOrders(){
        return orderRepository.findAllByOrderByIdAsc();
    }

    public void orderNextStatus(OrderEntity order){
        //recover current order status
        OrderStateEntity orderState =  order.getOrderStateEntity();
        OrderStateEntity nextOrderState = order.getOrderStateEntity();
        //chose the next one
        if(orderState.getCode().equals(WebConstants.ORDER_STATE_VALIDATED)){
            nextOrderState = orderStateService.findOrderStateEntityByCode(WebConstants.ORDER_STATE_PAYED);
        }
        if(orderState.getCode().equals(WebConstants.ORDER_STATE_PAYED)){
            nextOrderState = orderStateService.findOrderStateEntityByCode(WebConstants.ORDER_STATE_PREPARED);
        }
        if(orderState.getCode().equals(WebConstants.ORDER_STATE_PREPARED)){
            nextOrderState = orderStateService.findOrderStateEntityByCode(WebConstants.ORDER_STATE_DELIVERY_IN_PROGRESS);
        }
        if(orderState.getCode().equals(WebConstants.ORDER_STATE_DELIVERY_IN_PROGRESS)){
            nextOrderState = orderStateService.findOrderStateEntityByCode(WebConstants.ORDER_STATE_DELIVERED);
        }
        //save the order
        order.setOrderStateEntity(nextOrderState);
        orderRepository.save(order);
    }
}
