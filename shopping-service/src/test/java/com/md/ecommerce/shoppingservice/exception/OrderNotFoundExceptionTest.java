package com.md.ecommerce.shoppingservice.exception;

import com.md.ecommerce.shoppingservice.repository.OrderRepository;
import com.md.ecommerce.shoppingservice.service.OrderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OrderNotFoundExceptionTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test(expected = OrderNotFoundException.class)
    public void shouldThrowOrderNotFoundException() {

        when(orderRepository.findById("12345")).thenReturn(Optional.empty());

        orderService.findById("12345");
    }
}