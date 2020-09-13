package com.xhc.sbm.service;

import com.github.pagehelper.PageInfo;
import com.xhc.sbm.bean.bo.StudentPageQueryBo;
import com.xhc.sbm.entity.Student;

import java.util.List;


/**
 * @Author: xhc
 * @Date: 2020/3/27 9:45
 * @Description:
 */
public interface IStudentService {

    Student queryStudentById(Long id);

    PageInfo<Student> queryAll(StudentPageQueryBo studentPageQueryBo);

    Long addStudent(Student student);

    void updateStudent(Student student);

    void delStudent(Long id);

    int addStudentBatch(List<Student> list);

    int updateStudentBatch(List<Student> list);
}
