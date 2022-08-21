package com.dental.home.app.vo.excel.in;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 交易流水导入model
 */
@Data
public class ImportFlowRecordVO {
    /**
     * 账号名称
     */
    @ExcelProperty(value = "账号名称*", index = 0)
    private String accountName;
    /**
     * 交易日
     */
    @ExcelProperty(value = "交易日(yyyyMMdd)*", index = 1)
    private String day;
    /**
     * 交易时间
     */
    @ExcelProperty(value = "交易时间(HHmmss)*", index = 2)
    private String time;
    /**
     * 企业/单位所属州市
     */
    @ExcelProperty(value = "交易类型*", index = 3)
    private String flowTypeName;
    /**
     * 借方金额
     */
    @ExcelProperty(value = "借方金额*", index = 4)
    private BigDecimal out;
    /**
     * 贷方金额
     */
    @ExcelProperty(value = "贷方金额*", index = 5)
    private BigDecimal in;
    /**
     * 流水号
     */
    @ExcelProperty(value = "流水号*", index = 6)
    private String recordNo;

    /**
     * 收(付)方名称
     */
    @ExcelProperty(value = "收(付)方名称*", index = 7)
    private String amountAccountName;
}
