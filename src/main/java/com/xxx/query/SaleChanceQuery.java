package com.xxx.query;

import com.xxx.base.BaseQuery;
import lombok.Data;

@Data
public class SaleChanceQuery extends BaseQuery {
    private String customerName;
    private String createMan;
    private Integer state;

    private String devResult;
    private Integer assignMan;
}
