package com.generation.clinicamedica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.clinicamedica.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
	
	public List<Medico> findAllByTituloContainingIgnoreCase(@Param("especialidade")String especialidade);

}
