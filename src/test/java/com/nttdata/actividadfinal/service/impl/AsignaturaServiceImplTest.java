package com.nttdata.actividadfinal.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.nttdata.actividadfinal.repository.AsignaturaRepoJPA;
import com.nttdata.actividadfinal.repository.entity.Asignatura;
import com.nttdata.actividadfinal.service.AsignaturaService;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class AsignaturaServiceImplTest {
	private Asignatura a1, a2;
	
	@Autowired
	AsignaturaService service;
	
	@Autowired
	AsignaturaRepoJPA repo;
	
	@BeforeEach
	void setUp() throws Exception {
		repo.deleteAll();
		
		a1 = new Asignatura();
		a1.setNombre("Física");
		a1.setDescripcion("ejercicio");
		a1.setCurso(2);
		a1 = repo.save(a1);
		
		a2 = new Asignatura();
		a2.setNombre("Matematicas");
		a2.setDescripcion("números");
		a2.setCurso(1);
		a2 = repo.save(a2);
		
		
	}

	@AfterEach
	void tearDown() throws Exception {
		repo.deleteAll();
	}

	@Test
	void testConsultarTodos() {
		// GIVEN
		// Existen 2 asignaturas
		assertEquals(2 ,service.consultarTodos().size(),"2 asignaturas en BDD");
	
		// WHEN
		List<Asignatura> le = service.consultarTodos();
		
		// THEN
		assertEquals(2, le.size(), "hay 2 asignaturas en la BDD");
	}

	@Test
	void testConsultarPorID() throws Exception {
		// GIVEN
		// Existen 2 asignaturas
		assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD");
		
		// WHEN
		Asignatura a3 = service.consultarPorID(a1.getId());
		
		// THEN
		assertEquals(a1.getId(), a3.getId(), " Mismo ID");
		assertNotNull(a3, "asignatura válida");
	}

	@Test
	void testEliminarTodos() {
		// GIVEN
		// Existen 2 asignaturas
		assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD");
		
		// WHEN
		service.eliminarTodos();
		
		// THEN
		assertEquals(0, service.consultarTodos().size(), " hay 0 asignaturas en BDD");
	}

	@Test
	void testEliminarPorID() {
		// GIVEN
		// Existen 2 asignaturas
		assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD");
		
		// WHEN
		service.eliminarPorID(a2.getId());
		
		// THEN
		assertEquals(1, service.consultarTodos().size(), " hay 1 asignatura en BDD");
	}

	@Test
	void testModificar() throws Exception {
		// GIVEN
		// Existen 2 asignaturas
		assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD");
		
		// WHEN
		String nuevoNombre = "Test Asignatura2";
		a2.setNombre(nuevoNombre);
		service.modificar(a2);
		
		// THEN
		assertEquals(2, service.consultarTodos().size(), " hay 2 asignaturas en BDD");
		assertEquals(nuevoNombre, service.consultarPorID(a2.getId()).getNombre(), " modificado nombre");
	}

	@Test
	void testInserta() {
		// GIVEN
		// Existen 2 asignaturas
		assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD");
		
		// WHEN
		
		Asignatura a3 = new Asignatura();
		a3.setNombre("Religión");
		a3.setDescripcion("Estudio de creencias religiosas");
		a3.setCurso(3);
		a3 = service.inserta(a3);
		
		// THEN
		assertEquals(3, service.consultarTodos().size(), "Hay 3 asignaturas");
	}

}
