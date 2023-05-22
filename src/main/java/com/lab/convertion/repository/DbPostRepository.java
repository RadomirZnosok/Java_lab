package com.lab.convertion.repository;

import com.lab.convertion.models.DbEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DbPostRepository extends JpaRepository<DbEntity, String> {

}
