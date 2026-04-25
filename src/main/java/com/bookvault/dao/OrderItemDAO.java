package com.bookvault.dao;

import java.util.List;
import com.bookvault.dto.OrderItemDTO;

public interface OrderItemDAO {
    boolean saveOrderItems(List<OrderItemDTO> items);
    List<OrderItemDTO> getItemsByOrderId(int orderId);
    boolean deleteItemsByOrderId(int orderId); // Needed to prevent Foreign Key errors on cancel!
}