package com.example.order.service.adapters.order.rest;

import com.example.order.service.adapters.order.rest.dto.OrderCreateRequest;
import com.example.order.service.adapters.order.rest.dto.OrderCreateResponse;
import com.example.order.service.adapters.order.rest.dto.OrderTrackResponse;
import com.example.order.service.common.rest.BaseController;
import com.example.order.service.common.rest.Response;
import com.example.order.service.order.model.Order;
import com.example.order.service.order.usecase.OrderTrackUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/order")
@Slf4j
public class OrderController extends BaseController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<OrderCreateResponse> create(@RequestBody OrderCreateRequest request){
        log.info("Creating order for customer: {} at restaurant: {}",
                request.customerId(),
                request.restaurantId());

        Order order = publish(Order.class, request.toUseCase());
        OrderCreateResponse response = OrderCreateResponse.fromModel(order);

        log.info("Order created with tracking id: {}", response.orderTrackingId());
        return respond(response);

    }

    @GetMapping("/{tracking-id}")
    public Response<OrderTrackResponse> getOrderStatus(@PathVariable("tracking-id") UUID trackingId) {

        Order order = publish(Order.class, OrderTrackUseCase.from(trackingId));
        return respond(OrderTrackResponse.fromModel(order));
    }
}
