package com.codigospostalesmexicoapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.codigospostalesmexicoapi.entity.CodigoPostal;
import com.codigospostalesmexicoapi.entity.Settlements;


public interface SettlementsRepository extends CrudRepository<Settlements, Long>{
	
	
}
