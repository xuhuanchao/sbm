package com.xhc.sbm.dao;

import com.xhc.sbm.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author: xhc
 * @Date: 2020/3/27 9:44
 * @Description:
 */
@Mapper
public interface StudentDao extends BaseDao<Student, Long> {

    @Result(property = "name", column = "name")
    @Result(property = "nameAndAge", column = "nameAge")
    @Select("select id, name, age, create_time, CONCAT_WS('#', name,age) nameAge from student where id = #{id}")
    @Override
    Student queryOne(Long aLong);

    @Select("<script>" +
            "select id, name, age, create_time, CONCAT_WS('#', name,age) nameAge from student where 1=1 <if test='name != null'> and name like CONCAT('%',#{name},'%') </if>" +
            "<if test='age != null'> and age = #{age}</if>" +
            "</script>")
    @Override
    List<Student> queryAll(Student student);


    @Select("insert into student(name, age, create_time) values(#{name}, #{age}, #{createTime})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn = "id")
    @Override
    Integer addOne( Student student);

    @Select("update student set name=#{name}, age=#{age} where id=#{id}")
    @Override
    Integer updateOne(Student student);

    @Select("delete from student where id=#{id}")
    @Override
    Integer delOne(@Param("id") Long id);
}

