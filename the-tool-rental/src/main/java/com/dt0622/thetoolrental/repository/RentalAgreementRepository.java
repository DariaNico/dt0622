package com.dt0622.thetoolrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dt0622.thetoolrental.model.RentalAgreement;

public interface RentalAgreementRepository extends JpaRepository<RentalAgreement, Long> {

}
