package com.generation.clinicamedica.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import com.generation.clinicamedica.model.Medico;
import com.generation.clinicamedica.repository.MedicoRepository;
import com.generation.clinicamedica.repository.PacienteRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MedicoController {
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
    private PacienteRepository pacienteRepository;

	@GetMapping
	public ResponseEntity<List<Medico>> getAll() {
		return ResponseEntity.ok(medicoRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Medico> getById(@PathVariable Long id) {

		return medicoRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

	}

	@GetMapping("/medicos/{especialidade}")
	public ResponseEntity<List<Medico>> getByTitulo(@PathVariable String especialidade) {
		return ResponseEntity.ok(medicoRepository.findAllByTituloContainingIgnoreCase(especialidade));
	}

	@PostMapping
	public ResponseEntity<Medico> post(@Valid @RequestBody Medico medico) {
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(medicoRepository.save(medico));
	}

	@PutMapping
	public ResponseEntity<Medico> put(@Valid @RequestBody Medico medico) {
		
		if(pacienteRepository.existsById(medico.getId())) {		
				return ResponseEntity.status(HttpStatus.OK).body(medicoRepository.save(medico));
		}		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete (@PathVariable Long id)
	{
		Optional<Medico> medico = medicoRepository.findById(id);

		if (medico.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		medicoRepository.deleteById(id);
	}


}
