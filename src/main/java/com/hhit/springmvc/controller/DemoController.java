package com.hhit.springmvc.controller;


import com.hhit.springmvc.bean.User;
import com.hhit.springmvc.db.DBUtils;
import com.hhit.springmvc.db.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class DemoController {

    //-------------------Retrieve Single user--------------------------------------------------------
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {

        SqlSession sqlSession = null;
        try {
            sqlSession = DBUtils.openSqlSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.getUser(id);

            System.out.println(user.toString());
            sqlSession.commit();

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //-------------------Create a user--------------------------------------------------------
    /*
        http://localhost:8080/user/

        body:
        {
          "username": "萨摩",
          "password": "bbb",
          "address": "黑龙江"
        }
     */

    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> addUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {

        SqlSession sqlSession = null;
        try {
            sqlSession = DBUtils.openSqlSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.insertUser(user);
            sqlSession.commit();

        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    //------------------- Delete a Guest --------------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting user with id " + id);

        SqlSession sqlSession = null;
        try {
            sqlSession = DBUtils.openSqlSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.deleteUser(id);
            sqlSession.commit();

        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}