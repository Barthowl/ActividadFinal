package com.nttdata.actividadfinal.service;

import java.util.List;

import com.nttdata.actividadfinal.repository.entity.Asignatura;

public interface AsignaturaService {
	public List<Asignatura> consultarTodos();
	Asignatura consultarPorID(Integer id);
	void eliminarTodos();
	void eliminarPorID(Integer id);
	Asignatura modificar(Asignatura asig);
	Asignatura inserta(Asignatura asig);
	
	

}
