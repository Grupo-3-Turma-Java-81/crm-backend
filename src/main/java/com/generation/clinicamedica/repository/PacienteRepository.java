package com.generation.clinicamedica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.clinicamedica.model.Medico;
import com.generation.clinicamedica.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
	
	public List<Paciente> findAllByTituloContainingIgnoreCase(@Param("cpf")Long cpf);

}
