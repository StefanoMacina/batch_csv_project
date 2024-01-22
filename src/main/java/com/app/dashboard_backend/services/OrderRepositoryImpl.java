package com.app.dashboard_backend.services;

import com.app.dashboard_backend.models.Order;
import com.app.dashboard_backend.services.OrderService;
import com.app.dashboard_backend.services.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderRepositoryImpl implements OrderService {

    @Autowired
    IOrderRepository orderRepository;

    @Override
    public Map<String, Object> getAll() {
        Map<String,Object> response = new HashMap<>();
        response.put("total_orders", orderRepository.count());
        response.put("orders", orderRepository.findAll());
        return response;
    }

    @Override
    public Order getOrderById(int orderId) {
        Optional<Order> order = orderRepository.findById(orderId);

        if(order.isPresent()){
            return order.get();
        } else {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find order with id " + orderId);
        }
    }
}
