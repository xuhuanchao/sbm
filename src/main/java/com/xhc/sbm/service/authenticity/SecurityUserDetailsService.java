package com.xhc.sbm.service.authenticity;

import com.xhc.sbm.dao.RoleDao;
import com.xhc.sbm.dao.UserXmlDao;
import com.xhc.sbm.entity.Role;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: xhc
 * @Date: 2020/4/13 11:31
 * @Description:
 */
@Service
@Log4j2
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UserXmlDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        com.xhc.sbm.entity.User accountUser = userDao.queryOneByAccountName(s);
        if(accountUser == null ){
            log.error("没有查到[" + s + "]用户");
            throw new UsernameNotFoundException("没有该用户");
        }
        if(accountUser.getDeleted() == 1){
            log.error(s+"用户已被删除");
            throw new UsernameNotFoundException(s+"用户已被删除");
        }

        List<Role> userRoles = roleDao.queryRolesByUserId(accountUser.getId());
        List<SimpleGrantedAuthority> authorities = userRoles.stream().map(role -> new SimpleGrantedAuthority(role.getRolename())).collect(Collectors.toList());
        User user = new User(s, accountUser.getPassword(), authorities);
        return user;
    }


    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("123456"));
    }
}
