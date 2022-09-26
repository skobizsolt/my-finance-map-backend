package com.myfinancemap.app.persistence.repository;

import com.myfinancemap.app.persistence.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}

