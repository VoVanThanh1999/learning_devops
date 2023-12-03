package com.btc.user.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.btc.user.model.AppUser;

@Repository
public interface UserRespository extends JpaRepository<AppUser, Integer>{

}
