package com.nttdata.actividadfinal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.actividadfinal.repository.AsignaturaRepoJPA;
import com.nttdata.actividadfinal.repository.entity.Asignatura;
import com.nttdata.actividadfinal.service.AsignaturaService;

@Service
public class AsignaturaServiceImpl implements AsignaturaService {
	
	@Autowired
	AsignaturaRepoJPA asignaturaRepoJPA;
	


	@Override
	public List<Asignatura> consultarTodos() {
		return asignaturaRepoJPA.findAll();
	}

	@Override
	public Asignatura consultarPorID(Integer id) {
		return asignaturaRepoJPA.findById(id).orElse(null);
	}
	
	@Override
	public void eliminarTodos() {
		asignaturaRepoJPA.deleteAll();
		
	}

	@Override
	public void eliminarPorID(Integer id) {
		asignaturaRepoJPA.deleteById(id);
		
	}
	
	@Override
	public Asignatura modificar(Asignatura asig) {
		return asignaturaRepoJPA.save(asig);
	}

	@Override
	public Asignatura inserta(Asignatura asig) {
		return asignaturaRepoJPA.save(asig);
	}


	
}
