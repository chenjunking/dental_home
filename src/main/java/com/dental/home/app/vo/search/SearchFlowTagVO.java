package com.dental.home.app.vo.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * SearchEnterpriseVO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchFlowTagVO {
    private String name;
    private String type;
    private String flowRecordId;
}
