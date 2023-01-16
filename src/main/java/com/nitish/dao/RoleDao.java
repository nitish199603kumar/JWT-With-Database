package com.nitish.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nitish.entity.Role;

@Repository
public interface RoleDao extends CrudRepository<Role,String> {

}
