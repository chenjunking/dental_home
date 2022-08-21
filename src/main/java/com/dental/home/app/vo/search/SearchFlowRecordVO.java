package com.dental.home.app.vo.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * SearchEnterpriseVO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchFlowRecordVO {
    private String accountName;
    private String searchTime;
    private String projectTagId;
    private String remarkTagId;
    private String amountType;

    private Date startTime;
    private Date endTime;

    List<String> accountNameList;
    List<String> amountAccountNameList;
    List<String> projectTagIdList;
    List<String> remarkTagIdList;
}
