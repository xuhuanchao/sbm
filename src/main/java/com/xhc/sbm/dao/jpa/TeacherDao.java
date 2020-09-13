package com.xhc.sbm.dao.jpa;

import com.xhc.sbm.entity.jpa.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: xhc
 * @Date: 2020/6/11 18:21
 * @Description:
 */
public interface TeacherDao extends JpaRepository<Teacher, Long> {

    @Transactional
    @Modifying
    @Query("update Teacher set deleted=1 where id=?1")
    void deleteTeacher(Long id);
}
