package com.food.ordring.system.order.service.domain;


import com.food.ordring.system.domain.valueobject.*;
import com.food.ordring.system.error.ErrorCode;
import com.food.ordring.system.order.service.domain.dto.create.CreateOrderCommand;
import com.food.ordring.system.order.service.domain.dto.create.CreateOrderResponse;
import com.food.ordring.system.order.service.domain.dto.create.OrderAddress;
import com.food.ordring.system.order.service.domain.dto.create.OrderItem;
import com.food.ordring.system.order.service.domain.entity.Customer;
import com.food.ordring.system.order.service.domain.entity.Order;
import com.food.ordring.system.order.service.domain.entity.Product;
import com.food.ordring.system.order.service.domain.entity.Restaurant;
import com.food.ordring.system.order.service.domain.exception.OrderDomainException;
import com.food.ordring.system.order.service.domain.mapper.OrderDataMapper;
import com.food.ordring.system.order.service.domain.ports.input.service.OrderApplicationService;
import com.food.ordring.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.food.ordring.system.order.service.domain.ports.output.repository.OrderRepository;
import com.food.ordring.system.order.service.domain.ports.output.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationServiceTest {

    @Autowired
    private OrderApplicationService orderApplicationService;

    @Autowired
    private OrderDataMapper orderDataMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    private CreateOrderCommand createOrderCommand;

    private CreateOrderCommand createOrderCommandWrongPrice;

    private CreateOrderCommand createOrderCommandWrongProductPrice;

    private final UUID CUSTOMER_ID = UUID.fromString("a4d5fca5-64d5-44a0-8f99-4b5652a54277");
    private final UUID RESTAURANT_ID = UUID.fromString("a4d5fca5-64d5-44a0-8f99-4b5652a54278");
    private final UUID PRODUCT_ID_ONE = UUID.fromString("a4d5fca5-64d5-44a0-8f99-4b5652a54279");
    private final UUID PRODUCT_ID_TWO = UUID.fromString("a4d5fca5-64d5-44a0-8f99-4b5652a54289");
    private final UUID ORDER_ID = UUID.fromString("a4d5fca5-64d5-44a0-8f99-4b5652a54280");
    private final BigDecimal PRICE = new BigDecimal(200);

    @BeforeAll
    public void init() {
        createOrderCommand = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .resturentId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .city("Garifa")
                        .postal("743166")
                        .street("7 kaibartya para road")
                        .build())
                .currency(Currency.INR.toString())
                .price(PRICE)
                .items(List.of(OrderItem.builder()
                                .currency(Currency.INR.toString())
                                .productId(PRODUCT_ID_ONE)
                                .quantity(1)
                                .productPrice(new BigDecimal(50))
                                .subTotal(new BigDecimal(50))
                                .build(),
                        OrderItem.builder()
                                .currency(Currency.INR.toString())
                                .productId(PRODUCT_ID_TWO)
                                .quantity(3)
                                .productPrice(new BigDecimal(50))
                                .subTotal(new BigDecimal(150))
                                .build()))
                .build();

        createOrderCommandWrongPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .resturentId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .city("Garifa")
                        .postal("743166")
                        .street("7 kaibartya para road")
                        .build())
                .currency(Currency.INR.toString())
                .price(new BigDecimal(212))
                .items(List.of(OrderItem.builder()
                                .currency(Currency.INR.toString())
                                .productId(PRODUCT_ID_ONE)
                                .quantity(1)
                                .productPrice(new BigDecimal(50))
                                .subTotal(new BigDecimal(50))
                                .build(),
                        OrderItem.builder()
                                .currency(Currency.INR.toString())
                                .productId(PRODUCT_ID_TWO)
                                .quantity(3)
                                .productPrice(new BigDecimal(50))
                                .subTotal(new BigDecimal(150))
                                .build()))
                .build();

        createOrderCommandWrongProductPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .resturentId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .city("Garifa")
                        .postal("743166")
                        .street("7 kaibartya para road")
                        .build())
                .currency(Currency.INR.toString())
                .price(new BigDecimal(210))
                .items(List.of(OrderItem.builder()
                                .currency(Currency.INR.toString())
                                .productId(PRODUCT_ID_ONE)
                                .quantity(1)
                                .productPrice(new BigDecimal(60))
                                .subTotal(new BigDecimal(60))
                                .build(),
                        OrderItem.builder()
                                .currency(Currency.INR.toString())
                                .productId(PRODUCT_ID_TWO)
                                .quantity(3)
                                .productPrice(new BigDecimal(50))
                                .subTotal(new BigDecimal(150))
                                .build()))
                .build();

        Customer customer = new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));

        Order orderCommandToOrder = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        orderCommandToOrder.setId(new OrderId(ORDER_ID));

        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(orderRepository.save(any(Order.class))).thenReturn(orderCommandToOrder);
    }

    @BeforeEach
    public void initRestaurant(){
        Product product_one = new Product(
                "Product-1", new Money(new BigDecimal(50), Currency.INR));
        product_one.setId(new ProductId(PRODUCT_ID_ONE));

        Product product_two = new Product(
                "Product-2", new Money(new BigDecimal(50), Currency.INR));
        product_two.setId(new ProductId(PRODUCT_ID_TWO));

        Restaurant restaurent_object = Restaurant.builder()
                .resturentId(new ResturentId(createOrderCommand.getResturentId()))
                .productList(List.of(product_one, product_two))
                .isActive(Boolean.TRUE)
                .build();
        when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
                .thenReturn(Optional.of(restaurent_object));
    }

    @Test
    public void testCreateOrder() {

        CreateOrderResponse order = orderApplicationService.createOrder(createOrderCommand);
        assertEquals(OrderStatus.PENDING, order.getOrderStatus());
        assertEquals("Order created successfully", order.getMessage());
        assertNotNull(order.getOrderTrackingId());
    }

    @Test
    public void testCreateOrderWithWrongPrice() {
        OrderDomainException orderDomainException
                = assertThrows(OrderDomainException.class, () -> orderApplicationService.createOrder(createOrderCommandWrongPrice));

        assertEquals(ErrorCode.DOM_103,orderDomainException.getErrorCode());
    }

    @Test
    public void testCreateOrderWithWrongProductPrice() {
        OrderDomainException orderDomainException
                = assertThrows(OrderDomainException.class, () -> orderApplicationService.createOrder(createOrderCommandWrongProductPrice));

        assertEquals(ErrorCode.DOM_104,orderDomainException.getErrorCode());
    }

    @Test
    public void testRestaurantNotActive() {

        Product product_one = new Product(
                "Product-1", new Money(new BigDecimal(50), Currency.INR));
        product_one.setId(new ProductId(PRODUCT_ID_ONE));

        Product product_two = new Product(
                "Product-2", new Money(new BigDecimal(50), Currency.INR));
        product_two.setId(new ProductId(PRODUCT_ID_TWO));

        Restaurant restaurent_object = Restaurant.builder()
                .resturentId(new ResturentId(createOrderCommand.getResturentId()))
                .productList(List.of(product_one, product_two))
                .isActive(Boolean.FALSE)
                .build();

        when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
                .thenReturn(Optional.of(restaurent_object));


        OrderDomainException orderDomainException
                = assertThrows(OrderDomainException.class, () -> orderApplicationService.createOrder(createOrderCommand));

        assertEquals(ErrorCode.DOM_109,orderDomainException.getErrorCode());
    }
}
