package com.xhc.sbm.service;

import com.xhc.sbm.dao.jpa.TeacherDao;
import com.xhc.sbm.entity.jpa.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @Author: xhc
 * @Date: 2020/6/11 18:31
 * @Description:
 */
@Service
public class TeacherService implements ITeacherService {

    @Autowired
    private TeacherDao teacherDao;

    @Override
    public Teacher findTeacherById(Long id) {
        Optional<Teacher> optional = teacherDao.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }else{
            return null;
        }
    }

    @Override
    public void addTeacher(Teacher teacher) {
        teacherDao.save(teacher);
    }

    @Override
    public void modifyTeacher(Teacher teacher) {
        teacherDao.save(teacher);
    }

    @Override
    public void deleteTeacher(Long id) {
        teacherDao.deleteTeacher(id);
    }

    @Override
    public Page<Teacher> findPageTeacher(int pageNum, int size, Teacher teacher) {
        teacher.setDeleted(0);
        //页码从0开始
        pageNum--;
        if(pageNum < 0){
            pageNum = 0;
        }
        PageRequest pageRequest = PageRequest.of(pageNum, size, Sort.Direction.DESC, "createTime");
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("deleted", ExampleMatcher.GenericPropertyMatchers.exact())
                .withIgnorePaths("id");
        if(!StringUtils.isEmpty(teacher.getName())){
            matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        if(teacher.getSex() != null){
            matcher = matcher.withMatcher("sex", ExampleMatcher.GenericPropertyMatchers.exact());
        }
        if(teacher.getAge() != null){
            matcher = matcher.withMatcher("age", ExampleMatcher.GenericPropertyMatchers.exact());
        }

        Example<Teacher> example = Example.of(teacher, matcher);
        return teacherDao.findAll(example, pageRequest);
    }
}
