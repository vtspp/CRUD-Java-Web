package com.Spring.Web.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Spring.Web.Model.Paciente;
@Repository
public interface EntityRepository extends CrudRepository<Paciente, Long>{

}
