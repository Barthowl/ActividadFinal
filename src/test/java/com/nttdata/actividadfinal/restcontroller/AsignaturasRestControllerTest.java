package com.nttdata.actividadfinal.restcontroller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.nttdata.actividadfinal.repository.AsignaturaRepoJPA;
import com.nttdata.actividadfinal.repository.entity.Asignatura;
import com.nttdata.actividadfinal.service.AsignaturaService;


@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
class AsignaturasRestControllerTest {
	
	private Asignatura a1, a2;
	
	@Autowired
	AsignaturaService service;
	
	@Autowired
	AsignaturaRepoJPA repo;
	
	@Autowired
	AsignaturasRestController controller;
	
	@Mock // --> Simular
	AsignaturaService serviceMock;
	
	@InjectMocks
	AsignaturasRestController controllerMock;

	
	
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
		assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD");
		// WHEN
		ResponseEntity<List<Asignatura>> re = controller.consultarTodos();
	
		// THEN
		assertAll (
				() -> assertEquals(HttpStatus.CREATED, re.getStatusCode(), "Codigo 201: Muestra Asignatura"),
				() -> assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD")
		);
	}
	
	@Test
	void testConsultarTodosException() throws Exception {
		// GIVEN
		when ( serviceMock.consultarTodos()).thenThrow (new ArrayIndexOutOfBoundsException());
		
		// WHEN
		ResponseEntity<List<Asignatura>> re = controllerMock.consultarTodos();
		
		// THEN
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Excepción");
	}

	@Test
	void testConsultarPorID() {
		// GIVEN
		// Existen 2 asignaturas
		assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD");
		
		// WHEN
		Asignatura a3 = service.consultarPorID(a1.getId());
		ResponseEntity<Asignatura> re = controller.consultarPorID(a3.getId());
		
		// THEN
		assertAll (
				() -> assertEquals(HttpStatus.NO_CONTENT, re.getStatusCode(), "Codigo 204: No Content"),
				() -> assertEquals(a1.getId(), a3.getId(), " Mismo ID"),
				() -> assertNotNull(a3, "asignatura válida")
		);
	}
	
	@Test
	void testConsultarPorIdNull() {
		// GIVEN
		// Existen 2 asignaturas
		assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD");
		
		// WHEN
		ResponseEntity<Asignatura> re = controllerMock.consultarPorID(null);
		
		// THEN -> al ser id null debería ir al not acceptable
		assertEquals(HttpStatus.NOT_ACCEPTABLE, re.getStatusCode(), "Error 406 id es null");
	}
	
	@Test
	void testConsultarPorIdException() throws Exception {
		// GIVEN
		when ( serviceMock.consultarPorID(a1.getId())).thenThrow (new ArrayIndexOutOfBoundsException());
		
		// WHEN
		ResponseEntity<Asignatura> re = controllerMock.consultarPorID(a1.getId());
		
		// THEN
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Excepción");
	}

	@Test
	void testEliminarTodos() {
		// GIVEN
		// Existen 2 asignaturas
		assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD");
		
		// WHEN 
		ResponseEntity<Asignatura> re = controller.eliminarTodos();
		
		// THEN
		assertAll (
				() -> assertEquals(HttpStatus.OK, re.getStatusCode(), "Codigo 200: OK"),
				() -> assertEquals(0, service.consultarTodos().size(), "hay 0 asignaturas en la BDD")
		);
	}
	
	@Test
	void testEliminarTodosException() throws Exception {
		// GIVEN 
		doThrow(new ArrayIndexOutOfBoundsException()).when(serviceMock).eliminarTodos();
		
		// WHEN
		ResponseEntity<Asignatura> re = controllerMock.eliminarTodos();
		
		// THEN
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Excepción");
	}
	
	@Test
	void testEliminarPorID() {
		// GIVEN
		// Existen 2 asignaturas
		assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD");
		
		// WHEN
		ResponseEntity<Asignatura> re = controller.eliminarPorID(a1.getId());
		
		// THEN
		assertAll (
				() -> assertEquals(HttpStatus.OK, re.getStatusCode(), "Codigo 200: OK"),
				() -> assertEquals(1, service.consultarTodos().size(), "hay 1 asignatura en la BDD")
		);
	}
	
	@Test
	void testEliminarPorIDNull() {
		// GIVEN
		// Existen 2 asignaturas
		assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD");
		
		// WHEN
		ResponseEntity<Asignatura> re = controllerMock.eliminarPorID(null);
		
		// THEN
		assertAll (
				() -> assertEquals(HttpStatus.NOT_ACCEPTABLE, re.getStatusCode(), "Codigo 406: el id no puede ser nulo"),
				() -> assertEquals(2, service.consultarTodos().size(), "hay 2 asignaturas en la BDD")
		);
	}
	
	@Test
	void testEliminarPorIdException() throws Exception {
		// GIVEN
		doThrow(new ArrayIndexOutOfBoundsException()).when(serviceMock).eliminarPorID(a1.getId());
		
		// WHEN
		System.out.println(a1.getId());
		ResponseEntity<Asignatura> re = controllerMock.eliminarPorID(a1.getId());
		System.out.println(re.toString());
		
		// THEN
		assertEquals(HttpStatus.NOT_ACCEPTABLE, re.getStatusCode(), "Excepción");
	}
	

	@Test
	void testModificar() {
		// GIVEN
		// Existen 2 asignaturas
		assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD");
		
		// WHEN
		Asignatura a3 = new Asignatura();
		a3.setId(1);
		a3.setNombre("Prueba modifica");
		a3.setDescripcion("Prueba modifica");
		a3.setCurso(4);
		ResponseEntity<Asignatura> re = controller.modificar(a3);
		
		// THEN
		assertEquals(HttpStatus.CREATED, re.getStatusCode(), "Codigo 201: Modificada asignatura");
	}
	
	@Test
	void testModificarIdIsNotNull() {
		// GIVEN
		// Existen 2 asignaturas
		assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD");
		
		// WHEN
		Asignatura a3 = new Asignatura();
		a3.setNombre("Prueba modifica");
		a3.setDescripcion("Prueba modifica");
		a3.setCurso(4);
		ResponseEntity<Asignatura> re = controller.modificar(a1);
		
		// THEN
		assertEquals(HttpStatus.NOT_ACCEPTABLE, re.getStatusCode(), "Codigo 406: debe tener id");
	}
	
	@Test
	void testModificarNombreNotNull() {
		// GIVEN
		// Existen 2 asignaturas
		assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD");
		
		// WHEN
		Asignatura a3 = new Asignatura();
		a3.setId(1);
		a3.setNombre("");
		a3.setDescripcion("Prueba modificar");
		a3.setCurso(4);
		ResponseEntity<Asignatura> re = controller.insertar(a1);
		
		Asignatura a4 = new Asignatura();
		a4.setId(1);
		a4.setNombre(null);
		a4.setDescripcion("Prueba modificar");
		a4.setCurso(4);
		ResponseEntity<Asignatura> re2 = controller.insertar(a1);
		
		// THEN
		assertEquals(HttpStatus.NOT_ACCEPTABLE, re.getStatusCode(), "Codigo 406: Nombre no debe ser vacio");
		assertEquals(HttpStatus.NOT_ACCEPTABLE, re2.getStatusCode(), "Codigo 406: Nombre no debe ser null");
	}
	
	@Test
	void testModificarDescripcionNotNull() {
		// GIVEN
		// Existen 2 asignaturas
		assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD");
		
		// WHEN
		Asignatura a3 = new Asignatura();
		a3.setId(1);
		a3.setNombre("Prueba modificar");
		a3.setDescripcion("");
		a3.setCurso(4);
		ResponseEntity<Asignatura> re = controller.insertar(a1);
		
		Asignatura a4 = new Asignatura();
		a4.setId(1);
		a4.setNombre("Prueba modificar");
		a4.setDescripcion(null);
		a4.setCurso(4);
		ResponseEntity<Asignatura> re2 = controller.insertar(a1);
		
		// THEN
		assertEquals(HttpStatus.NOT_ACCEPTABLE, re.getStatusCode(), "Codigo 406: Descripción no debe ser vacia");
		assertEquals(HttpStatus.NOT_ACCEPTABLE, re2.getStatusCode(), "Codigo 406: Descripción no debe ser null");
	}
	
	@Test
	void testModificarCursoNotZero() {
		// GIVEN
		// Existen 2 asignaturas
		assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD");
		
		// WHEN
		Asignatura a3 = new Asignatura();
		a3.setId(1);
		a3.setNombre("Prueba inserta");
		a3.setDescripcion("Prueba inserta");
		a3.setCurso(0);
		ResponseEntity<Asignatura> re = controller.modificar(a1);
		
		// THEN
		assertEquals(HttpStatus.NOT_ACCEPTABLE, re.getStatusCode(), "Codigo 406: Curso no debe ser 0");
	}
	
	@Test
	void testModificarExceptionArrayIndexOutOfBoundsException() throws Exception {
		// GIVEN
		Asignatura a3 = new Asignatura();
		a3.setId(900000);
		when ( serviceMock.modificar(a3)).thenThrow (new ArrayIndexOutOfBoundsException());
		
		// WHEN
		ResponseEntity<Asignatura> re = controllerMock.modificar(a3);
		
		// THEN
		assertEquals(HttpStatus.NOT_ACCEPTABLE, re.getStatusCode(), "Excepción esa id no existe en el array");
	}
	
	@Test
	void testModificarException() throws Exception {
		// GIVEN
		when ( serviceMock.modificar(null)).thenThrow (new ArrayIndexOutOfBoundsException());
		
		// WHEN
		ResponseEntity<Asignatura> re = controllerMock.modificar(null);
		
		// THEN
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Excepción");
	}

	@Test
	void testInsertar() {
		// GIVEN
		// Existen 2 asignaturas
		assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD");
		
		// WHEN
		Asignatura a3 = new Asignatura();
		a3.setNombre("Prueba inserta");
		a3.setDescripcion("Prueba inserta");
		a3.setCurso(4);
		ResponseEntity<Asignatura> re = controller.insertar(a3);
		
		// THEN
		assertEquals(HttpStatus.CREATED, re.getStatusCode(), "Codigo 201: asignatura insertada");
	}
	
	
	@Test
	void testInsertarIdIsNotNull() {
		// GIVEN
		// Existen 2 asignaturas
		assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD");
		
		// WHEN
		Asignatura a3 = new Asignatura();
		a3.setId(999);
		a3.setNombre("Prueba inserta");
		a3.setDescripcion("Prueba inserta");
		a3.setCurso(4);
		ResponseEntity<Asignatura> re = controller.insertar(a3);
		
		// THEN
		assertEquals(HttpStatus.NOT_ACCEPTABLE, re.getStatusCode(), "Codigo 406: no debe tener id");
	}
	
	@Test
	void testInsertarNombreNotNull() {
		// GIVEN
		// Existen 2 asignaturas
		assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD");
		
		// WHEN
		Asignatura a3 = new Asignatura();
		a3.setNombre("");
		a3.setDescripcion("Prueba inserta");
		a3.setCurso(4);
		ResponseEntity<Asignatura> re = controller.insertar(a3);
		
		Asignatura a4 = new Asignatura();
		a4.setNombre(null);
		a4.setDescripcion("Prueba inserta");
		a4.setCurso(4);
		ResponseEntity<Asignatura> re2 = controller.insertar(a4);
		
		// THEN
		assertEquals(HttpStatus.NOT_ACCEPTABLE, re.getStatusCode(), "Codigo 406: Nombre no debe ser vacio");
		assertEquals(HttpStatus.NOT_ACCEPTABLE, re2.getStatusCode(), "Codigo 406: Nombre no debe ser null");
	}
	
	@Test
	void testInsertarDescripcionNotNull() {
		// GIVEN
		// Existen 2 asignaturas
		assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD");
		
		// WHEN
		Asignatura a3 = new Asignatura();
		a3.setNombre("Prueba inserta");
		a3.setDescripcion("");
		a3.setCurso(4);
		ResponseEntity<Asignatura> re = controller.insertar(a3);
		
		Asignatura a4 = new Asignatura();
		a4.setNombre("Prueba inserta");
		a4.setDescripcion(null);
		a4.setCurso(4);
		ResponseEntity<Asignatura> re2 = controller.insertar(a4);
		
		// THEN
		assertEquals(HttpStatus.NOT_ACCEPTABLE, re.getStatusCode(), "Codigo 406: Descripción no debe ser vacia");
		assertEquals(HttpStatus.NOT_ACCEPTABLE, re2.getStatusCode(), "Codigo 406: Descripción no debe ser null");
	}
	
	@Test
	void testInsertarCursoNotZero() {
		// GIVEN
		// Existen 2 asignaturas
		assertEquals(2,service.consultarTodos().size(),"2 asignaturas en BDD");
		
		// WHEN
		Asignatura a3 = new Asignatura();
		a3.setNombre("Prueba inserta");
		a3.setDescripcion("Prueba inserta");
		a3.setCurso(0);
		ResponseEntity<Asignatura> re = controller.insertar(a3);
		
		// THEN
		assertEquals(HttpStatus.NOT_ACCEPTABLE, re.getStatusCode(), "Codigo 406: Curso no debe ser 0");
	}
	
	@Test
	void testInsertarException() throws Exception {
		// GIVEN
		Asignatura a3 = new Asignatura();
		a3.setNombre("Prueba inserta");
		a3.setDescripcion("Prueba inserta");
		a3.setCurso(4);
		when ( serviceMock.inserta(a3)).thenThrow (new ArrayIndexOutOfBoundsException());
		
		// WHEN
		ResponseEntity<Asignatura> re = controllerMock.insertar(a3);
		
		// THEN
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, re.getStatusCode(), "Excepción");
	}

}
