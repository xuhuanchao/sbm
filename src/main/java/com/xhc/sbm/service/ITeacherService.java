package com.xhc.sbm.service;

import com.xhc.sbm.entity.jpa.Teacher;
import org.springframework.data.domain.Page;

/**
 * @Author: xhc
 * @Date: 2020/6/11 18:29
 * @Description:
 */
public interface ITeacherService {

    Teacher findTeacherById(Long id);

    void addTeacher(Teacher teacher);

    void modifyTeacher(Teacher teacher);

    void deleteTeacher(Long id);

    Page<Teacher> findPageTeacher(int pageNum, int size, Teacher teacher);
}
