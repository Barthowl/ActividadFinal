package com.nttdata.actividadfinal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.actividadfinal.repository.AsignaturaRepoJPA;
import com.nttdata.actividadfinal.repository.entity.Asignatura;
import com.nttdata.actividadfinal.repository.entity.Usuario;
import com.nttdata.actividadfinal.service.AsignaturaService;

@Service
public class AsignaturaServiceImpl implements AsignaturaService {
	
	@Autowired
	AsignaturaRepoJPA asignaturaRepoJPA;
	


	@Override
	public List<Asignatura> listar() {
		return asignaturaRepoJPA.findAll();
	}


	
}
