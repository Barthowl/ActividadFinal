package com.nttdata.actividadfinal.service;

import java.util.List;

import com.nttdata.actividadfinal.repository.entity.Usuario;

public interface UsuarioService {

	public List<Usuario> listar();
	
	public List<Usuario> listarUsuarioPorId(Integer id);

	Usuario buscarPorUsername(String nombre);
	
}
