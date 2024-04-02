package com.xxx.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleChance {
    private Integer id;

    private String chanceSource;

    private String customerName;

    private Integer cgjl;

    private String overview;

    private String linkMan;

    private String linkPhone;

    private String description;

    private String createMan;

    private String assignMan;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date assignTime;

    private Integer state;

    private Integer devResult;

    private Integer isValid;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateDate;

    private String uname;
}