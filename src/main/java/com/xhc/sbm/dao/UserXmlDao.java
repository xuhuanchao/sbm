package com.xhc.sbm.dao;

import com.xhc.sbm.entity.Student;
import com.xhc.sbm.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: xhc
 * @Date: 2020/4/14 16:58
 * @Description:
 */
@Mapper
public interface UserXmlDao extends BaseDao<User, Long> {
    @Override
    public User queryOne(Long t);

    public User queryOneByAccountName(String accountName);

    @Override
    public List<User> queryAll(User user);

    @Override
    public Integer addOne(User user);

    @Override
    public Integer updateOne(User user);

    @Override
    public Integer delOne(Long aLong);

    public void addBatch(@Param("list") List<User> list);
}
