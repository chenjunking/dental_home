package com.dental.home.app.dao.cluster;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dental.home.app.vo.TestVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ClusterMapper extends BaseMapper<TestVO> {
    Long countAll();
    List<TestVO> selectAll();

    IPage<TestVO> getDataList(IPage<TestVO> page);
}
