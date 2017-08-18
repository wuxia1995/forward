package com.ntech.dao;

import com.ntech.model.PayTimes;
import com.ntech.model.PayTimesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PayTimesMapper {
    long countByExample(PayTimesExample example);

    int deleteByExample(PayTimesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PayTimes record);

    int insertSelective(PayTimes record);

    List<PayTimes> selectByExample(PayTimesExample example);

    PayTimes selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PayTimes record, @Param("example") PayTimesExample example);

    int updateByExample(@Param("record") PayTimes record, @Param("example") PayTimesExample example);

    int updateByPrimaryKeySelective(PayTimes record);

    int updateByPrimaryKey(PayTimes record);
}