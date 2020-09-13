package com.xhc.sbm.dao;


import com.xhc.sbm.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleDao extends BaseDao<Role, Long> {

    List<Role> queryRolesByUserId(Long userId);

}