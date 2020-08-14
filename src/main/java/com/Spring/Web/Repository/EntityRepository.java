package com.Spring.Web.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Spring.Web.Model.Paciente;
@Repository
public interface EntityRepository extends CrudRepository<Paciente, String>{
	Paciente findByNomeIgnoreCaseContaining(String nome);
	Paciente findByCpf(String cpf);
	Paciente findByRg(String rg);
	void deleteByCpf(String cpf);
	void deleteByRg(String rg);
	boolean existsByCpf(String cpf);
	boolean existsByRg(String rg);
}
