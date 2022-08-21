package com.dental.home.app.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Temperature {
    private Date ts;
    private BigDecimal temperature;
    private String location;
    private Integer tbIndex;
}
