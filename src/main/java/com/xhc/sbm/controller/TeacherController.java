package com.xhc.sbm.controller;

import com.xhc.sbm.bean.ResponseResult;
import com.xhc.sbm.bean.bo.TeacherBo;
import com.xhc.sbm.entity.jpa.Teacher;
import com.xhc.sbm.service.ITeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: xhc
 * @Date: 2020/6/11 22:55
 * @Description:
 */
@RestController
@RequestMapping("teacher")
@Api(tags = "teacherController 测试持久化使用spring jpa")
public class TeacherController {


    @Autowired
    private ITeacherService iTeacherService;

    @PostMapping("/addTeahcer")
    @ApiOperation(value = "添加一个老师信息", notes="添加一个老师信息")
    public ResponseResult addTeahcer(@RequestBody @Valid TeacherBo teacherBo){
        Teacher t = new Teacher();
        BeanUtils.copyProperties(teacherBo, t);
        t.setDeleted(0);
        iTeacherService.addTeacher(t);
        return ResponseResult.success();
    }


    @PostMapping("/modifyTeacher")
    @ApiOperation(value = "修改一个老师信息", notes="修改一个老师信息")
    public ResponseResult modifyTeacher(@RequestBody @Valid TeacherBo teacherBo){
        Teacher t = new Teacher();
        BeanUtils.copyProperties(teacherBo, t);
        iTeacherService.modifyTeacher(t);
        return ResponseResult.success();
    }

    @PostMapping("/deleteTeacher")
    @ApiOperation(value = "删除一个老师信息", notes="删除一个老师信息")
    public ResponseResult deleteTeacher(Long id){
        iTeacherService.deleteTeacher(id);
        return ResponseResult.success();
    }

    @PostMapping("/queryTeacherById")
    @ApiOperation(value = "通过id查询老师信息", notes="通过id查询老师信息")
    public ResponseResult<Teacher> queryTeacherById(Long id){
        Teacher teacher = iTeacherService.findTeacherById(id);
        return ResponseResult.success(teacher);
    }

    @PostMapping("/queryPageTeacher")
    @ApiOperation(value = "分页查询老师信息", notes="分页查询老师信息")
    public ResponseResult<Page<Teacher>> queryPageTeacher(@RequestParam(value = "pageNum") Integer pageNum,
                                                          @RequestParam(value = "pageSize") Integer pageSize,
                                                          @RequestBody @Valid Teacher teacher){
        Page<Teacher> page = iTeacherService.findPageTeacher(pageNum, pageSize, teacher);
        return ResponseResult.success(page);
    }

}
