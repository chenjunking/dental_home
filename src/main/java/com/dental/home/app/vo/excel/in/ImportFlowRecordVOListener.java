package com.dental.home.app.vo.excel.in;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImportFlowRecordVOListener extends AnalysisEventListener<ImportFlowRecordVO> {

    //可以通过实例获取该值
    private List<ImportFlowRecordVO> data = new ArrayList<>();

    private void doSomething(Object object) {
        //1、入库调用接口
    }

    @Override
    public void invoke(ImportFlowRecordVO importFlowRecordVO, AnalysisContext analysisContext) {
        //数据存储到list，供批量处理，或后续自己业务逻辑处理。
        data.add(importFlowRecordVO);
        //根据自己业务做处理
        doSomething(importFlowRecordVO);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //解析结束销毁不用的资源
        //data.clear();
    }

    public List<ImportFlowRecordVO> getData() {
        return data;
    }
    public void setData(List<ImportFlowRecordVO> data) {
        this.data = data;
    }
}
