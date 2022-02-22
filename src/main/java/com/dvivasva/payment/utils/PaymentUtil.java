package com.dvivasva.payment.utils;

import com.dvivasva.payment.dto.PaymentDto;
import com.dvivasva.payment.model.Payment;
import org.springframework.beans.BeanUtils;

public class PaymentUtil {

    public static PaymentDto entityToDto(Payment payment){
        var paymentDto=new PaymentDto();
        BeanUtils.copyProperties(payment,paymentDto);
        return paymentDto;
    }
    public static Payment dtoToEntity(PaymentDto paymentDto){
        var entity=new Payment();
        BeanUtils.copyProperties(paymentDto,entity);
        return entity;
    }

}
