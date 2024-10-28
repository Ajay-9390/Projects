package com.anr.tiffinshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anr.tiffinshop.model.MyOrder;

public interface MyOrderRepository extends JpaRepository<MyOrder, Long> {

}
