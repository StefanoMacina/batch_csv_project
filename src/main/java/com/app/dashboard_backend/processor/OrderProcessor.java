package com.app.dashboard_backend.processor;


import com.app.dashboard_backend.models.Order;
import com.app.dashboard_backend.models.Order;
import org.springframework.batch.item.ItemProcessor;

public class OrderProcessor implements ItemProcessor<Order, Order> {

    @Override
    public Order process(Order order) throws Exception {
        order.setId(null);
        return order;
    }
}
