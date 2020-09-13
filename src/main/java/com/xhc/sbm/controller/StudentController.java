package com.xhc.sbm.controller;

import com.github.pagehelper.PageInfo;
import com.xhc.sbm.bean.ResponseResult;
import com.xhc.sbm.bean.bo.StudentAddBo;
import com.xhc.sbm.bean.bo.StudentPageQueryBo;
import com.xhc.sbm.bean.bo.StudentUpdateBo;
import com.xhc.sbm.entity.Student;
import com.xhc.sbm.service.IStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * dao 使用注解方式
 *
 * @Author: xhc
 * @Date: 2020/3/27 9:49
 * @Description:
 */
@RestController
@RequestMapping("student")
@Api(tags = "测试dao使用注解方式写sql")
public class StudentController {

    @Autowired
    @Qualifier("studentService")
    private IStudentService studentService;

    @ApiOperation(value = "通过id查学生", notes="通过id查学生")
    @GetMapping(value = "/queryOne/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", defaultValue = "", required = true),
    })
    public ResponseResult<Student> queryStudentById(@PathVariable Long id){
        return ResponseResult.success(studentService.queryStudentById(id));
    }

    @ApiOperation(value = "通过条件查学生列表", notes="通过条件查学生列表")
    @PostMapping(value = "/queryAll")
    public ResponseResult<PageInfo<Student>> queryAll(@RequestBody @Validated StudentPageQueryBo studentPageQueryBo){
        return ResponseResult.success(studentService.queryAll(studentPageQueryBo));
    }

    @ApiOperation(value = "添加学生", notes="添加学生")
    @PostMapping(value = "/add")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", defaultValue = "", required = true),
    })
    public ResponseResult addStudent(@RequestBody @Validated StudentAddBo studentAddBo){
        Student student = new Student();
        BeanUtils.copyProperties(studentAddBo, student);
        return ResponseResult.success(studentService.addStudent(student));
    }

    @ApiOperation(value = "修改学生", notes="修改学生")
    @PostMapping(value = "/update")
    @Transactional(rollbackFor=Exception.class)
    public ResponseResult updateStudent(@RequestBody @Validated StudentUpdateBo studentUpdateBo){
        Student student = new Student();
        BeanUtils.copyProperties(studentUpdateBo, student);
        studentService.updateStudent(student);
        return ResponseResult.success();
    }

    @ApiOperation(value = "删除学生", notes="删除学生")
    @PostMapping(value = "/del/{id}")
    public ResponseResult delStudent(@PathVariable Long id){
        studentService.delStudent(id);
        return ResponseResult.success();
    }

}
