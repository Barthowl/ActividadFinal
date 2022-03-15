package com.nttdata.actividadfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.nttdata.actividadfinal.service.AsignaturaService;
import com.nttdata.actividadfinal.service.UsuarioService;
import com.nttdata.actividadfinal.repository.entity.Usuario;

@Controller
public class WebController {
	
	@Autowired
	AsignaturaService asignaturaService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping("/")
	public String index(Model model) {
		Usuario u = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("usuario" , u);
		return "index";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/asignaturas")
	//@Cacheable(value="asignatura")
	public String listarAsig(Model model) {
		model.addAttribute("listaAsig" , asignaturaService.consultarTodos());
		return "asignaturas";
	}
	
	@GetMapping("/listado")
	public String listarUsu(Model model) {
		model.addAttribute("listaUsu" , usuarioService.listarUsuarioPorId(2));
		return "listado";
	}
	
	@GetMapping("/error")
	public String error_page() {
		return "error";
	}

}
