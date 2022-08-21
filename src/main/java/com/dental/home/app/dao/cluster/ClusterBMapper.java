package com.dental.home.app.dao.cluster;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dental.home.app.vo.Temperature;
import com.dental.home.app.vo.TestVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

public interface ClusterBMapper extends BaseMapper<Temperature> {

//    @Update("CREATE TABLE if not exists temperature(ts timestamp, temperature float) tags(location nchar(64), tbIndex int);")
    @Update("CREATE STABLE if not exists meters (ts timestamp, current float, voltage int, phase float) TAGS (location binary(64), groupId int);")
    void createSuperTable();

    @Update("create table if not exists #{tbName} using meters tags( #{location}, #{tbindex})")
    void createTable(@Param("tbName") String tbName, @Param("location") String location, @Param("tbindex") int tbindex);

    @Update("drop table if exists temperature")
    void dropSuperTable();

//    @Insert("insert into tb${tbIndex}(ts, temperature) values(#{ts}, #{temperature})")
    @Insert("INSERT INTO ${tbName} USING meters TAGS (${location}, ${tbindex}) VALUES (now, ${current},  ${voltage},  ${phase});")
    int insertOne(@Param("tbName") String tbName, @Param("location") String location, @Param("tbindex") int tbindex, @Param("current") BigDecimal current, @Param("voltage") BigDecimal voltage, @Param("phase") BigDecimal phase);
}
