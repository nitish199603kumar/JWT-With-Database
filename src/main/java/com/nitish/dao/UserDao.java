package com.nitish.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nitish.entity.User;

@Repository
public interface UserDao extends CrudRepository<User,String>{

}
