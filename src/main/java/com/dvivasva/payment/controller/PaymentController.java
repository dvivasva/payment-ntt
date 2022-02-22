package com.dvivasva.payment.controller;

import com.dvivasva.payment.dto.PaymentDto;
import com.dvivasva.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public Flux<PaymentDto> read() {
        return paymentService.read();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<PaymentDto> create(@RequestBody Mono<PaymentDto> paymentDtoMono) {
        return this.paymentService.create(paymentDtoMono);
    }
    @PutMapping("/{id}")
    public Mono<PaymentDto> update(@RequestBody Mono<PaymentDto> accountDtoMono, @PathVariable String id){
        return this.paymentService.update(accountDtoMono,id);
    }
    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable String id){
        return paymentService.delete(id);
    }

}
