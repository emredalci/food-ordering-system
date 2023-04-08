package com.example.order.service.order.model;

import com.example.order.service.common.exception.OrderServiceBusinessException;
import com.example.order.service.common.model.Money;
import com.example.order.service.common.model.StreetAddress;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Builder
@Getter
public final class Order {

    public static final String FAILURE_MESSAGE_DELIMITER = ",";

    private UUID id;
    private final UUID customerId;
    private final UUID restaurantId;
    private final StreetAddress deliveryAddress;
    private final Money price;
    private List<OrderItem> items;
    private UUID trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;

    public void pay(){
        if (Boolean.FALSE.equals(orderStatus.equals(OrderStatus.PENDING))){
            throw new OrderServiceBusinessException("invalid.order.status.for.pay");
        }
        orderStatus = OrderStatus.PAID;
    }

    public void approve() {
        if(Boolean.FALSE.equals(orderStatus.equals(OrderStatus.PAID))) {
            throw new OrderServiceBusinessException("invalid.order.status.for.approve");
        }
        orderStatus = OrderStatus.APPROVED;
    }

    public void initCancel(List<String> failureMessages) {
        if (Boolean.FALSE.equals(orderStatus.equals(OrderStatus.PAID))) {
            throw new OrderServiceBusinessException("invalid.order.status.for.init.cancel");
        }
        orderStatus = OrderStatus.CANCELLING;
        updateFailureMessages(failureMessages);
    }

    public void cancel(List<String> failureMessages) {
        if (Boolean.FALSE.equals(orderStatus.equals(OrderStatus.CANCELLING) || orderStatus.equals(OrderStatus.PENDING))) {
            throw new OrderServiceBusinessException("invalid.order.status.for.cancel");
        }
        orderStatus = OrderStatus.CANCELLED;
        updateFailureMessages(failureMessages);
    }

    public void initializeOrder(){
        id = UUID.randomUUID();
        trackingId = UUID.randomUUID();
        orderStatus = OrderStatus.PENDING;
        initializeOrderItems();
    }

    public void validateOrder(){
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if (Objects.nonNull(this.failureMessages) && Objects.nonNull(failureMessages)) {
            this.failureMessages.addAll(failureMessages.stream().filter(message -> Boolean.FALSE.equals(message.isEmpty())).toList());
        }
        if (Objects.isNull(this.failureMessages)) {
            this.failureMessages = failureMessages;
        }
    }

    private void validateItemsPrice() {
        Money orderItemsTotal = getItems().stream().map(orderItem -> {
            validateItemPrice(orderItem);
            return orderItem.getSubTotal();
        }).reduce(Money.ZERO, Money::add);

        if (Boolean.FALSE.equals(getPrice().equals(orderItemsTotal))){
            throw new OrderServiceBusinessException("total.price.not.equal.order.items.total.price");
        }
    }

    private void validateItemPrice(OrderItem orderItem) {
        if (Boolean.FALSE.equals(orderItem.isPriceValid())){
            throw new OrderServiceBusinessException("invalid.order.item.price");
        }
    }

    private void validateInitialOrder() {
        if (Objects.nonNull(orderStatus) || Objects.nonNull(id)){
            throw new OrderServiceBusinessException("order.not.correct.state");
        }
    }

    private void validateTotalPrice() {
        if (Objects.isNull(price) || Boolean.FALSE.equals(price.isGreaterThanZero())){
            throw new OrderServiceBusinessException("total.price.not.valid");
        }

    }

    private void initializeOrderItems() {
        AtomicLong itemId = new AtomicLong(1);
        items.forEach(orderItem -> orderItem.initializeOrderItem(itemId.incrementAndGet(), id));
    }


}
