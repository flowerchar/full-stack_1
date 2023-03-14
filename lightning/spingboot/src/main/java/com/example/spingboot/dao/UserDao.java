package com.example.spingboot.dao;


import com.example.spingboot.POJO.Q;
import com.example.spingboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {

    List<User> findAll();

    User getById(Integer id);

    User getByUser(@Param("username")String username,@Param("password") String password);

    int insert(User user);

    int update(User user);

    int deleteById(Integer id);


    List<User> findPage(Q q);

    long  count(Q q);
}
