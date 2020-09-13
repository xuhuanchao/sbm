package com.xhc.sbm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xhc.sbm.bean.bo.StudentPageQueryBo;
import com.xhc.sbm.entity.Student;
import com.xhc.sbm.dao.StudentDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 * 使用注解方式写sql的StudentDao
 *
 * @Author: xhc
 * @Date: 2020/3/27 9:48
 * @Description:
 */
@Service
public class StudentService implements IStudentService {

    // StudentDao 使用注解方式写sql
    @Autowired
    private StudentDao studentDao;

    @Override
    public Student queryStudentById(Long id) {
        return studentDao.queryOne(id);
    }

    @Override
    public PageInfo<Student> queryAll(StudentPageQueryBo studentPageQueryBo) {
        PageHelper.startPage(studentPageQueryBo.getPageNum(), studentPageQueryBo.getPageSize());
        Student student = new Student();
        BeanUtils.copyProperties(studentPageQueryBo, student);

        List<Student> list = studentDao.queryAll(student);
        PageInfo<Student> pageInfo = new PageInfo<Student>(list);
        return pageInfo;
    }

    @Override
    public Long addStudent(Student student) {
        student.setCreateTime(new Date());
        Integer i = studentDao.addOne(student);
        return student.getId();
    }

    @Override
    public void updateStudent(Student student) {
        Integer integer = studentDao.updateOne(student);
    }

    @Override
    public void delStudent(Long id) {
        Integer integer = studentDao.delOne(id);
    }

    @Override
    public int addStudentBatch(List<Student> list) {
        return 0;
    }

    @Override
    public int updateStudentBatch(List<Student> list) {
        return 0;
    }
}
