package com.bookvault.controller;
import java.util.List;
import com.bookvault.dto.OrderDTO;
import com.bookvault.service.OrderService;
import com.bookvault.service.impl.OrderServiceImpl;

public class OrderController {
    private OrderService orderService = new OrderServiceImpl();

    public int createOrder(OrderDTO order) {
        if (order.getTotalBill() <= 0) return -1;
        return orderService.createOrder(order);
    }

    public List<OrderDTO> getUserOrderHistory(int userId) { return orderService.getHistory(userId); }

    public boolean cancelUserOrder(int orderId, int userId) {
        if (orderId <= 0 || userId <= 0) return false;
        return orderService.cancelOrder(orderId, userId);
    }
}