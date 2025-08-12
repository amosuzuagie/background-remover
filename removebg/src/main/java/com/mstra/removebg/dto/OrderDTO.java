package com.mstra.removebg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private String id;
    private String entity;
    private Double amount;
    private String currency;
    private String status;
    private Date created_at;
    private String receipt;
}
