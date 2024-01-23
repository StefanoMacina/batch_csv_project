package com.app.dashboard_backend.services;

import com.app.dashboard_backend.models.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Component
public interface OrderService {
    Map<String,Object> getAll();
    Order getOrderById(int orderId);



}
