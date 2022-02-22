package com.dvivasva.payment.repository;

import com.dvivasva.payment.model.Payment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends ReactiveMongoRepository<Payment,String> {
}
