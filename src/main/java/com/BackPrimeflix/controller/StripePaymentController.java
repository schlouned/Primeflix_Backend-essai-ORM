package com.BackPrimeflix.controller;

import com.BackPrimeflix.constants.WebConstants;
import com.BackPrimeflix.dto.PaymentDto;
import com.BackPrimeflix.exception.MovieStock0Exception;
import com.BackPrimeflix.model.CartEntity;
import com.BackPrimeflix.model.CartItemEntity;
import com.BackPrimeflix.model.OrderEntity;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.BackPrimeflix.util.service.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = WebConstants.ALLOWED_URL)
@RestController
@RequestMapping("/stripePayment")
public class StripePaymentController {
    //members
    private StripeService stripeClient;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MovieService movieService;

    //methods
    @Autowired
    StripePaymentController(StripeService stripeClient) {
        this.stripeClient = stripeClient;
    }

    @PostMapping("/charge")
    public Charge chargeCard(@RequestBody PaymentDto payment) throws Exception {
        //recover the token
        String token = payment.getToken();
        //recover the order
        OrderEntity order = orderService.findOrderEntityById(Long.parseLong(payment.getOrderId()));
        //check if there is no one product which stock is 0
        CartEntity cart = order.getCartEntity();
        for (CartItemEntity cartItemEntity : cart.getCartItemEntities()) {
            if (!movieService.checkMovieStockQuantity(cartItemEntity.getMovie(), cartItemEntity.getQuantity())) {
                throw new MovieStock0Exception(WebConstants.STOCK_0);
            }
        }
        //recover the amount and convert to double
        Double amount = (double)order.getTotalCost();
        return this.stripeClient.chargeNewCard(token, amount);
    }

}
