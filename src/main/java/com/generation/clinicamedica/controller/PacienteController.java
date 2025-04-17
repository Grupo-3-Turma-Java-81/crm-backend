package com.generation.clinicamedica.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.generation.clinicamedica.model.Medico;
import com.generation.clinicamedica.model.Paciente;
import com.generation.clinicamedica.repository.MedicoRepository;
import com.generation.clinicamedica.repository.PacienteRepository;
import com.generation.clinicamedica.repository.PacienteRepository;

import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/paciente")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PacienteController {


		@Autowired
		private PacienteRepository pacienteRepository;

		@Autowired
		private MedicoRepository medicoRepository;

		@GetMapping
		public ResponseEntity<List<Paciente>> getAll() {
			return ResponseEntity.ok(pacienteRepository.findAll());
		}

		@GetMapping("/{id}")
		public ResponseEntity<Paciente> getById(@PathVariable Long id) {

			return pacienteRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
					.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

		}

		@GetMapping("/{cpf}")
		public ResponseEntity<List<Paciente>> getByCpf(@PathVariable Long cpf) {
			return ResponseEntity.ok(pacienteRepository.findAllByCpf(cpf));
		}

		@PostMapping
		public ResponseEntity<Paciente> post(@Valid @RequestBody Paciente paciente) {

			return ResponseEntity.status(HttpStatus.CREATED).body(pacienteRepository.save(paciente));
		}

		@PutMapping
		public ResponseEntity<Paciente> put(@Valid @RequestBody Paciente paciente) {

			if (pacienteRepository.existsById(paciente.getId())) {
				return ResponseEntity.status(HttpStatus.OK).body(pacienteRepository.save(paciente));
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		@ResponseStatus(HttpStatus.NO_CONTENT)
		@DeleteMapping("/{id}")
		public void delete(@PathVariable Long id) {
			Optional<Paciente> paciente = pacienteRepository.findById(id);

			if (paciente.isEmpty())
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);

			pacienteRepository.deleteById(id);
		}
		
	}

