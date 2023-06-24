package com.dt0622.thetoolrental.repository;

import com.dt0622.thetoolrental.model.Checkout;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
}
