package com.etiya.ecommercedemo4.repository;

import com.etiya.ecommercedemo4.entities.concretes.Town;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITownRepository extends JpaRepository<Town,Integer> {

}
