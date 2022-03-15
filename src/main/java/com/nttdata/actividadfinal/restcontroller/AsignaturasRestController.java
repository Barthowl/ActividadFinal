package com.nttdata.actividadfinal.restcontroller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.actividadfinal.repository.entity.Asignatura;
import com.nttdata.actividadfinal.service.AsignaturaService;

@RestController
@RequestMapping ("/api/asignaturas")
public class AsignaturasRestController {
	
	@Autowired
	AsignaturaService asignaturaService;

	// consultarTodos
	
	@GetMapping
	public ResponseEntity<List<Asignatura>> consultarTodos () {
	
		try {
			HttpHeaders headers = new HttpHeaders();
			List<Asignatura> lista = asignaturaService.consultarTodos();

			URI newPath = new URI("/api/asignaturas/");
			headers.setLocation(newPath);
			headers.set("Message", "Asignaturas consultadas correctamente");
			return new ResponseEntity<>(lista, headers, HttpStatus.CREATED);
		}
		catch (Exception ex) {
			return new ResponseEntity<> (null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//consultarPorID
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Asignatura> consultarPorID(@PathVariable("id") Integer id) {
		HttpHeaders headers = new HttpHeaders();
		try {
			if(asignaturaService.consultarPorID(id) == null) {
				headers.set("Message", "El id no existe en la lista de asignaturas");
				return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);	
			}
			Asignatura as = asignaturaService.consultarPorID(id);
			URI newPath = new URI("/api/asignaturas/"+id);
			headers.setLocation(newPath);
			headers.set("Message", "Asignatura consultada correctamente con id: "+ id);
			
			return new ResponseEntity<> (as , headers, HttpStatus.CREATED);
		}
		catch (Exception ex) {
			return new ResponseEntity<> (null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	// eliminarTodos
	
	@CacheEvict(value="asignaturas" , allEntries = true)
	@DeleteMapping
	public ResponseEntity<Asignatura> eliminarTodos() {
		try {
			HttpHeaders headers = new HttpHeaders();
			asignaturaService.eliminarTodos();
			URI newPath = new URI("/api/asignaturas/");
			headers.setLocation(newPath);
			headers.set("Message", "Todas las asignaturas han sido borradas");
			
			return new ResponseEntity<> (headers, HttpStatus.CREATED);
		}
		catch (Exception ex) {
			return new ResponseEntity<> (null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// eliminarPorID
	
	@CacheEvict(value="asignaturas" , allEntries = true)
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Asignatura> eliminarPorID(@PathVariable("id") Integer id) {
		HttpHeaders headers = new HttpHeaders();
		try {
			if(asignaturaService.consultarPorID(id) == null) {
				headers.set("Message", "El id no existe en la lista de asignaturas");
				return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);	
			}
			asignaturaService.eliminarPorID(id);
			URI newPath = new URI("/api/asignaturas/"+id);
			headers.setLocation(newPath);
			headers.set("Message", "Asignatura borrada correctamente con id: "+ id);
			
			return new ResponseEntity<> (headers, HttpStatus.CREATED);
		}
		catch (Exception ex) {
			return new ResponseEntity<> (null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// modificar
	
	@PutMapping
	@CacheEvict(value="asignaturas" , allEntries = true)
	public ResponseEntity<Asignatura> modificar(@RequestBody Asignatura asig) {
		int id = 0;
		HttpHeaders headers = new HttpHeaders();
		try {
			
			if (asig.getNombre()==null || asig.getNombre().equals("")
					|| asig.getDescripcion() ==null || asig.getDescripcion().equals("")
					|| asig.getCurso() == 0) {
					headers.set("Message", "Ningún valor puede ser vacío");
					return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);	
					
				}  else if (asig.getId() != null) 
				id = asig.getId();
				else if (asig.getId() == null) {
				headers.set("Message", "Para modificar una asignatura, debe dar el id y debe ser superior a 0");
				return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);
			}
				asignaturaService.consultarTodos().get(id).getId();
				Asignatura as = asignaturaService.modificar(asig);
				URI newPath = new URI("/api/asignaturas/"+as.getId());
				headers.setLocation(newPath);
				headers.set("Message", "Asignatura modificada correctamente con id: "+as.getId());
				return new ResponseEntity<> (as, headers, HttpStatus.CREATED);
		}
		catch (Exception ex) {
			if (ex instanceof IndexOutOfBoundsException) {
				headers.set("Message", "El id no existe en la lista de asignaturas");
				return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);	
			}
			return new ResponseEntity<> (null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	// insertar
	
	@CacheEvict(value="asignaturas", allEntries=true)
	@PostMapping
	public ResponseEntity<Asignatura> insertar (@RequestBody Asignatura asig) {
		try {
			HttpHeaders headers = new HttpHeaders();
			if (asig.getId()!=null) {
				headers.set("Message", "Para dar de alta una nueva asignatura, el ID debe llegar vacío");
				return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);
			}
			else if (asig.getNombre()==null || asig.getNombre().equals("")
				|| asig.getDescripcion() ==null || asig.getDescripcion().equals("")
				|| asig.getCurso() == 0) {
				headers.set("Message", "Ni NOMBRE ni DESCRIPCION ni CURSO pueden ser nulos");
				return new ResponseEntity<>(headers, HttpStatus.NOT_ACCEPTABLE);	
			}
			
			Asignatura as = asignaturaService.inserta(asig);
			URI newPath = new URI("/api/asignaturas/"+as.getId());
			headers.setLocation(newPath);
			headers.set("Message", "Asignatura insertada correctamente con id: "+as.getId());
			
			return new ResponseEntity<> (as, headers, HttpStatus.CREATED);
		}
		catch (Exception ex) {
			return new ResponseEntity<> (null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

}
