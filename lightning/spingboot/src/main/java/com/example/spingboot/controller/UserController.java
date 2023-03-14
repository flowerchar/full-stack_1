package com.example.spingboot.controller;

import com.example.spingboot.POJO.Q;
import com.example.spingboot.POJO.R;
import com.example.spingboot.dao.UserDao;
import com.example.spingboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDao userDao;

    @PostMapping("/login")
    public R login(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return R.error("参数错误");
        }
        User res = userDao.getByUser(user.getUsername(), user.getPassword());
        if (res == null) {
            return R.error("用户名或者密码错误");
        }
        return R.success(res);
    }
    @GetMapping
    public R hello() {
        return R.success(userDao.findAll());
    }

    @GetMapping("/{id}")
    public R findById(@PathVariable Integer id) {
        return R.success(userDao.getById(id));
    }

    @GetMapping("/uniquery")
    public R GetByUser(String username, String password) {
        return R.success(userDao.getByUser(username, password));
    }

    @PostMapping
    public R save(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return R.error("参数错误");
        }
        userDao.insert(user);
        return R.success();
    }

    @PutMapping
    public R update(@RequestBody User user) {
        if (user.getId() == null) {
            throw new RuntimeException("参数错误");
        }
        userDao.update(user);
        return R.success();
    }

    @GetMapping("/page")
    public R findPage(Q q) {
        Integer currentPage = q.getCurrentPage();
        Integer pageSize = q.getPageSize();
        if (currentPage==null || currentPage<=0 || pageSize==null || pageSize <1){
            return R.error("参数错误");
        }
        Integer pageNum = (currentPage-1)*pageSize;
        q.setPageNum(pageNum);
        List<User> users = userDao.findPage(q);
        long total = userDao.count(q);
        Map<String, Object> map = new HashMap<>();
        map.put("data", users);
        map.put("total", total);
        return R.success(map);
    }
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Integer id) {
        if (id == null || id == 0) {
            throw new RuntimeException("参数错误");
        }
        return R.success(userDao.deleteById(id)==1);
    }


}
