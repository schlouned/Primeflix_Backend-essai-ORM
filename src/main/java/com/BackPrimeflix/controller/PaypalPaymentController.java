package com.BackPrimeflix.controller;

import com.BackPrimeflix.constants.WebConstants;
import com.BackPrimeflix.exception.MovieStock0Exception;
import com.BackPrimeflix.model.CartEntity;
import com.BackPrimeflix.model.CartItemEntity;
import com.BackPrimeflix.model.OrderEntity;
import com.BackPrimeflix.util.service.MovieService;
import com.BackPrimeflix.util.service.OrderService;
import com.BackPrimeflix.util.service.PaypalService;
import com.BackPrimeflix.util.service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.Map;

@CrossOrigin(origins = WebConstants.ALLOWED_URL)
@RestController
@RequestMapping("/paypalPayment")
public class PaypalPaymentController {
    //members
    private final PaypalService payPalClient;
    @Autowired
    private OrderService orderService;

    @Autowired
    PaypalPaymentController(PaypalService payPalClient) {
        this.payPalClient = payPalClient;
    }

    @Autowired
    private MovieService movieService;

    //methods
    @PostMapping("/make/payment")
    public Map<String, Object> makePayment(@RequestParam("orderId") String orderId) {
        //recover the order
        OrderEntity order = orderService.findOrderEntityById(Long.parseLong(orderId));
        //check if there is no one product which stock is 0
        CartEntity cart = order.getCartEntity();
        for (CartItemEntity cartItemEntity : cart.getCartItemEntities()) {
            if (!movieService.checkMovieStockQuantity(cartItemEntity.getMovie(), cartItemEntity.getQuantity())) {
                throw new MovieStock0Exception(WebConstants.STOCK_0);
            }
        }
        //recover the amount
        DecimalFormat df = new DecimalFormat("0.00");
        String sum = df.format(order.getTotalCost());
        String sum2 = sum.replace(',', '.');
        //return
        return payPalClient.createPayment(sum2);
    }

    @PostMapping("/complete/payment")
    public Map<String, Object> completePayment(HttpServletRequest request, @RequestParam("paymentId") String paymentId, @RequestParam("payerId") String payerId) {
        return payPalClient.completePayment(request);
    }
}
