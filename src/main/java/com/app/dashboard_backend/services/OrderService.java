package com.app.dashboard_backend.services;

import com.app.dashboard_backend.models.Order;

import java.util.List;
import java.util.Map;


public interface OrderService {
    Map<String,Object> getAll();
    Order getOrderById(int orderId);

}
