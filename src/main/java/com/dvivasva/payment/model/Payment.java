package com.dvivasva.payment.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@Document
public class Payment {
    @Id
    private String id;
    private double amount;
    private double commission;
    private String description;
    private String creditId;
    private Date date;
    private String param;

}
