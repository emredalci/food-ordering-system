package com.example.order.service.adapters.order.mapper;

import com.example.order.service.adapters.order.jpa.entity.OrderEntity;
import com.example.order.service.adapters.order.jpa.entity.OrderItemEntity;
import com.example.order.service.common.model.Money;
import com.example.order.service.order.model.Order;
import com.example.order.service.order.model.OrderItem;
import com.example.order.service.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static com.example.order.service.order.model.Order.FAILURE_MESSAGE_DELIMITER;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class );

    @Mapping(source = "id", target = "id")
    @Mapping(source = "customerId", target = "customerId")
    @Mapping(source = "restaurantId", target = "restaurantId")
    @Mapping(source = "trackingId", target = "trackingId")
    @Mapping(source = "price", target = "price.amount")
    @Mapping(source = "orderStatus", target = "orderStatus")
    @Mapping(source = "failureMessages", target = "failureMessages", qualifiedByName = "splitFailureMessages")
    @Mapping(source = "address.id", target = "deliveryAddress.id")
    @Mapping(source = "address.street", target = "deliveryAddress.street")
    @Mapping(source = "address.postalCode", target = "deliveryAddress.postCode")
    @Mapping(source = "address.city", target = "deliveryAddress.city")
    @Mapping(source = "items", target = "items", qualifiedByName = "mapOrderItems")
    Order map(OrderEntity orderEntity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "customerId", target = "customerId")
    @Mapping(source = "restaurantId", target = "restaurantId")
    @Mapping(source = "trackingId", target = "trackingId")
    @Mapping(source = "price.amount", target = "price")
    @Mapping(source = "orderStatus", target = "orderStatus")
    @Mapping(source = "failureMessages", target = "failureMessages", qualifiedByName = "splitFailureMessages", ignore = true)
    @Mapping(source = "deliveryAddress.id", target = "address.id")
    @Mapping(source = "deliveryAddress.street", target = "address.street")
    @Mapping(source = "deliveryAddress.postCode", target = "address.postalCode")
    @Mapping(source = "deliveryAddress.city", target = "address.city")
    @Mapping(source = "items", target = "items", qualifiedByName = "mapOrderItems", ignore = true)
    OrderEntity map(Order order);


    @Named("splitFailureMessages")
    default List<String> splitFailureMessages(String failureMessages){
        return failureMessages.isEmpty() ? new ArrayList<>() : Arrays.asList(failureMessages.split(FAILURE_MESSAGE_DELIMITER));
    }

    @Named("mapOrderItems")
    default List<OrderItem> mapOrderItems(List<OrderItemEntity> items){
        return items.stream().map(buildOrderItem()).toList();
    }

    private static Function<OrderItemEntity, OrderItem> buildOrderItem() {
        return orderItemEntity ->
                OrderItem.builder()
                        .id(orderItemEntity.getId())
                        .product(new Product(orderItemEntity.getProductId()))
                        .price(new Money(orderItemEntity.getPrice()))
                        .quantity(orderItemEntity.getQuantity())
                        .subTotal(new Money(orderItemEntity.getSubTotal()))
                        .build();
    }


}

