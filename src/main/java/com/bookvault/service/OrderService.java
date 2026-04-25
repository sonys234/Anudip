package com.bookvault.service;
import java.util.List;
import com.bookvault.dto.OrderDTO;

public interface OrderService {
    int createOrder(OrderDTO order);
    List<OrderDTO> getHistory(int userId);
    boolean cancelOrder(int orderId, int userId);
}