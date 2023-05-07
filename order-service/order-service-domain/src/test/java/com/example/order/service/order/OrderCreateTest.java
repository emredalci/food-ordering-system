package com.example.order.service.order;


import com.example.order.service.adapters.CustomerFakeAdapter;
import com.example.order.service.adapters.OrderFakeAdapter;
import com.example.order.service.adapters.OutboxPaymentFakeAdapter;
import com.example.order.service.adapters.RestaurantFakeAdapter;
import com.example.order.service.common.exception.OrderServiceBusinessException;
import com.example.order.service.common.model.Money;
import com.example.order.service.common.model.StreetAddress;
import com.example.order.service.order.OrderCreateUseCaseHandler;
import com.example.order.service.order.model.Order;
import com.example.order.service.order.model.OrderItem;
import com.example.order.service.order.model.OrderStatus;
import com.example.order.service.order.service.OrderService;
import com.example.order.service.order.usecase.OrderCreateUseCase;
import com.example.order.service.product.model.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderCreateTest {

    private OrderCreateUseCaseHandler handler;

    @BeforeEach
    void setUp() {
        CustomerFakeAdapter customerAdapter = new CustomerFakeAdapter();
        OrderFakeAdapter orderAdapter = new OrderFakeAdapter();
        RestaurantFakeAdapter restaurantAdapter = new RestaurantFakeAdapter();
        OutboxPaymentFakeAdapter outboxPaymentAdapter = new OutboxPaymentFakeAdapter();
        OrderService orderService = new OrderService();
        handler = new OrderCreateUseCaseHandler(customerAdapter, restaurantAdapter, orderService, orderAdapter,
                outboxPaymentAdapter);
    }

    @Test
    void Should_Create_Order_When_UseCase_Is_Sufficient() {
        //GIVEN
        OrderItem item1 = buildOrderItem(UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb48"),
                1,
                new Money(new BigDecimal("50.00")),
                new Money(new BigDecimal("50.00")));

        OrderItem item2 = buildOrderItem(UUID.fromString("c05c5b14-ce68-4237-b4c0-4b95d397ac09"),
                3,
                new Money(new BigDecimal("50.00")),
                new Money(new BigDecimal("150.00")));

        OrderCreateUseCase orderCreateUseCase = buildOrderCreateUseCase(
                UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41"),
                UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb45"),
                new BigDecimal("200.00"),
                List.of(item1, item2),
                new StreetAddress(UUID.fromString("19cc9a3a-bd29-443f-a447-d221e1790641"), "street_1", "1000AB",
                        "Amsterdam"));

        //WHEN
        Order order = handler.handler(orderCreateUseCase);
        //THEN
        assertAll(
                () -> assertNotNull(order.getId()),
                () -> assertTrue(order.getItems().stream().allMatch((item) -> Objects.nonNull(item.getOrderId()))),
                () -> assertTrue(order.getItems().stream().allMatch((item) -> Boolean.FALSE.equals(item.getId() <= 0))),
                () -> assertNotNull(order.getTrackingId()),
                () -> assertEquals(OrderStatus.PENDING, order.getOrderStatus()),
                () -> assertTrue(Objects.isNull(order.getFailureMessages()))
        );

    }

    @Test
    void Should_Throw_Exception_When_Wrong_Price() {
        //GIVEN
        OrderItem item1 = buildOrderItem(UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb48"),
                1,
                new Money(new BigDecimal("50.00")),
                new Money(new BigDecimal("50.00")));

        OrderItem item2 = buildOrderItem(UUID.fromString("c05c5b14-ce68-4237-b4c0-4b95d397ac09"),
                3,
                new Money(new BigDecimal("50.00")),
                new Money(new BigDecimal("150.00")));

        OrderCreateUseCase orderCreateUseCase = buildOrderCreateUseCase(
                UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41"),
                UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb45"),
                new BigDecimal("250.00"),
                List.of(item1, item2),
                new StreetAddress(UUID.fromString("19cc9a3a-bd29-443f-a447-d221e1790641"), "street_1", "1000AB",
                        "Amsterdam"));
        String expectedErrorMessage = "total.price.not.equal.order.items.total.price";
        //WHEN
        //THEN
        OrderServiceBusinessException exception = assertThrows(OrderServiceBusinessException.class,
                () -> handler.handler(orderCreateUseCase));
        assertEquals(expectedErrorMessage, exception.getMessage());

    }

    @Test
    void Should_Throw_Exception_When_Wrong_Product_Price() {
        //GIVEN
        OrderItem item1 = buildOrderItem(UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb48"),
                1,
                new Money(new BigDecimal("60.00")),
                new Money(new BigDecimal("60.00")));

        OrderItem item2 = buildOrderItem(UUID.fromString("c05c5b14-ce68-4237-b4c0-4b95d397ac09"),
                3,
                new Money(new BigDecimal("50.00")),
                new Money(new BigDecimal("150.00")));

        OrderCreateUseCase orderCreateUseCase = buildOrderCreateUseCase(
                UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41"),
                UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb45"),
                new BigDecimal("250.00"),
                List.of(item1, item2),
                new StreetAddress(UUID.fromString("19cc9a3a-bd29-443f-a447-d221e1790641"), "street_1", "1000AB",
                        "Amsterdam"));
        String expectedErrorMessage = "invalid.order.item.price";
        //WHEN
        //THEN
        OrderServiceBusinessException exception = assertThrows(OrderServiceBusinessException.class,
                () -> handler.handler(orderCreateUseCase));
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    private OrderItem buildOrderItem(UUID id, int quantity, Money price, Money subtotal) {
        return OrderItem.builder()
                .product(new Product(id))
                .quantity(quantity)
                .price(price)
                .subTotal(subtotal)
                .build();
    }

    private OrderCreateUseCase buildOrderCreateUseCase(UUID customerId, UUID restaurantId, BigDecimal price,
            List<OrderItem> items, StreetAddress address) {
        return new OrderCreateUseCase(customerId,
                restaurantId,
                price,
                items,
                address);
    }
}
