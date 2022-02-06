/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.respository;

import com.client.model.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author leonard
 */
public interface UserRepository extends JpaRepository<UserDto, Long>{
    UserDto findByPhoneNumber(String phone);
    UserDto findByEmailAddress(String email);
}
