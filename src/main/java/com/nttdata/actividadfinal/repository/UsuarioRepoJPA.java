package com.nttdata.actividadfinal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nttdata.actividadfinal.repository.entity.Usuario;


public interface UsuarioRepoJPA extends JpaRepository<Usuario, String> {
	
	@Query(value="select * from usuario where rol_id=?1",nativeQuery=true)
	public List<Usuario> listarUsuarioPorRol(Integer id);
}
