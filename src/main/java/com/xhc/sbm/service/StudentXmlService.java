package com.xhc.sbm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhc.sbm.bean.bo.StudentPageQueryBo;
import com.xhc.sbm.entity.Student;
import com.xhc.sbm.dao.StudentDaoXml;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 * 使用xml方式写sql的StudentDaoXml
 *
 * @Author: xhc
 * @Date: 2020/3/27 9:48
 * @Description:
 */
@Service
public class StudentXmlService implements IStudentService {

    //使用xml方式写sql
    @Autowired
    private StudentDaoXml studentDaoXml;

    @Override
    public Student queryStudentById(Long id) {
        return studentDaoXml.queryOne(id);
    }

    @Override
    public PageInfo<Student> queryAll(StudentPageQueryBo studentPageQueryBo) {
        String orderBy = " age desc";
        PageHelper.startPage(studentPageQueryBo.getPageNum(), studentPageQueryBo.getPageSize(), orderBy);
        Student student = new Student();
        BeanUtils.copyProperties(studentPageQueryBo, student);

        List<Student> list = studentDaoXml.queryAll(student);
        PageInfo<Student> pageInfo = new PageInfo<Student>(list);
        return pageInfo;
    }

    @Override
    public Long addStudent(Student student) {
        student.setCreateTime(new Date());
        studentDaoXml.addOne(student);
        return student.getId();
    }

    @Override
    public void updateStudent(Student student) {
        Integer integer = studentDaoXml.updateOne(student);
    }

    @Override
    public void delStudent(Long id) {
        Integer integer = studentDaoXml.delOne(id);
    }


    @Override
    public int addStudentBatch(List<Student> list) {
        if(list == null || list.size() == 0){
            return 0;
        }
        return studentDaoXml.addBatch(list);
    }

    @Override
    public int updateStudentBatch(List<Student> list) {
        if(list == null || list.size() == 0){
            return 0;
        }
        return studentDaoXml.updateBatch(list);
    }
}
