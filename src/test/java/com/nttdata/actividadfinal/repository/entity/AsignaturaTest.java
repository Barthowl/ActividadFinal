package com.nttdata.actividadfinal.repository.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AsignaturaTest {

	@Test
	void test() {
		
		// Constructor
		Asignatura a1 = new Asignatura();
		
		// setter getter
		a1.setId(1);
		assertEquals(1,a1.getId(), "mismo ID");
		
		String nombre = "Historia";
		a1.setNombre(nombre);
		assertEquals(nombre,a1.getNombre(), "Mismo nombre");
		
		String descripcion = "Enseña la historia de España";
		a1.setDescripcion(descripcion);
		assertEquals(descripcion,a1.getDescripcion(), "Misma descripción");
		
		Integer curso = 2;
		a1.setCurso(curso);
		assertEquals(2,a1.getCurso(), "Misma descripción");
		
		// equals (id -> Asignatura.java)
		Asignatura a2 = new Asignatura();
		a2.setId(1);
		a2.setNombre(nombre);
		a2.setDescripcion(descripcion);
		a2.setCurso(curso);
		
		// pasar por los ifs de equals
		assertEquals(a1, a2, "Misma asignatura");
		assertEquals(a1, a1, "Mismo objeto");
		assertNotEquals(a1, nombre, "Distinto objeto");
		
		// hashcode (id -> Asignatura.java)
		assertEquals(a1.hashCode(), a2.hashCode(), "Mismo hash code");
	}

}
