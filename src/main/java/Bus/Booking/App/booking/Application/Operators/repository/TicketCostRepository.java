package com.RedBus.Operators.repository;

import com.RedBus.Operators.entity.TicketCost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketCostRepository extends JpaRepository<TicketCost,String> {
}
