package com.xhc.sbm.dao;

import com.xhc.sbm.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: xhc
 * @Date: 2020/3/27 10:27
 * @Description:
 */
@Mapper
public interface StudentDaoXml extends BaseDao<Student, Long> {

    @Override
    Student queryOne(Long t);

    @Override
    List<Student> queryAll(Student student);

    @Override
    Integer addOne(Student student);

    @Override
    Integer updateOne(Student student);

    @Override
    Integer delOne(Long id);

    int addBatch(@Param("list") List<Student> list);

    int updateBatch(@Param("list") List<Student> list);
}
