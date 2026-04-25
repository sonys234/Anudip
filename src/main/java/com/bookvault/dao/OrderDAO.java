package com.bookvault.dao;

import java.util.List;
import com.bookvault.dto.OrderDTO;

public interface OrderDAO {
    int placeOrder(OrderDTO order);
    List<OrderDTO> getOrderHistory(int userId);
    
    
    boolean cancelOrder(int orderId, int userId);
    

    List<OrderDTO> getAllOrders();
}