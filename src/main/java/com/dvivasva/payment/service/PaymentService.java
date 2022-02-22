package com.dvivasva.payment.service;

import com.dvivasva.payment.dto.PaymentDto;
import com.dvivasva.payment.repository.PaymentRepository;
import com.dvivasva.payment.utils.DateUtil;
import com.dvivasva.payment.utils.PaymentUtil;
import com.dvivasva.payment.webclient.CreditWebClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final static Logger logger= LoggerFactory.getLogger(PaymentService.class);

    public Flux<PaymentDto> read(){
        return paymentRepository.findAll().map(PaymentUtil::entityToDto);
    }
    public Mono<PaymentDto> create(Mono<PaymentDto> entityToDto){

        Mono<PaymentDto> result=entityToDto.map(
                p -> {
                    var today = LocalDateTime.now();

                    if(p.getParam().equals("UP")) {
                        Mono<Void> v = updateCredit(p.getCreditId(), p.getAmount());
                        v.doOnSuccess(x -> logger.info("update credit")).subscribe();
                    }

                    p.setDate(DateUtil.toDate(today));
                    return p;
                });

        return result.map(PaymentUtil::dtoToEntity)
                .flatMap(paymentRepository::save)
                .map(PaymentUtil::entityToDto);

    }

    public Mono<PaymentDto> update(Mono<PaymentDto> paymentDtoMono, String id){
        return paymentRepository.findById(id)
                .flatMap(p->paymentDtoMono.map(PaymentUtil::dtoToEntity)
                        .doOnNext(e->e.setId(id)))
                .flatMap(paymentRepository::save)
                .map(PaymentUtil::entityToDto);

    }
    public Mono<Void> delete(String id){
        return paymentRepository.deleteById(id);
    }

    public Mono<Void> updateCredit(String creditId, double amount){
        CreditWebClient creditWebClient= new CreditWebClient();
        var creditDtoMono= creditWebClient.details(creditId)
                .switchIfEmpty(Mono.error(new ClassNotFoundException("..")))
                .map(p->{
                    p.setPayments(p.getPayments()+amount);
                    return p;
                });
        return  Mono.when(creditWebClient.update(creditId,creditDtoMono));

    }

}
