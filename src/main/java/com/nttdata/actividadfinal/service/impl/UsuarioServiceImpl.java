package com.nttdata.actividadfinal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nttdata.actividadfinal.repository.UsuarioRepoJPA;
import com.nttdata.actividadfinal.repository.entity.Usuario;
import com.nttdata.actividadfinal.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {
	
	@Autowired
	UsuarioRepoJPA usuarioRepoJPA;

	@Override
	public List<Usuario> listar() {
		return usuarioRepoJPA.findAll();
	}
	
	public List<Usuario> listarUsuarioPorId(Integer id) {
		return usuarioRepoJPA.listarUsuarioPorRol(id);
	}

	@Override
	public Usuario buscarPorUsername(String nombre) {
		Usuario us = usuarioRepoJPA.findById(nombre).get();
		return usuarioRepoJPA.findById(nombre).get();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return buscarPorUsername(username);
	}


}
