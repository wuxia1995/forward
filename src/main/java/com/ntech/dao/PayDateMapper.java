package com.ntech.dao;

import com.ntech.model.PayDate;
import com.ntech.model.PayDateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PayDateMapper {
    long countByExample(PayDateExample example);

    int deleteByExample(PayDateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PayDate record);

    int insertSelective(PayDate record);

    List<PayDate> selectByExample(PayDateExample example);

    PayDate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PayDate record, @Param("example") PayDateExample example);

    int updateByExample(@Param("record") PayDate record, @Param("example") PayDateExample example);

    int updateByPrimaryKeySelective(PayDate record);

    int updateByPrimaryKey(PayDate record);
}