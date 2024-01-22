package com.app.dashboard_backend.services.repository;

import com.app.dashboard_backend.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Integer> {
}
