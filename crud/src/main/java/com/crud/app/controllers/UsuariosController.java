package com.crud.app.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.crud.app.entity.Usuario;
import com.crud.app.service.IUsuarioService;
import com.crud.app.util.paginator.PageRender;

@Controller
@SessionAttributes("usuario")
public class UsuariosController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping(value = {"/principal","/"})
	public String mostarTodos(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Usuario> usuarios = usuarioService.findAllUsuario(pageRequest);
		PageRender<Usuario> pageRender = new PageRender<> ("/principal", usuarios);
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "Lista de usuarios");
		return "principal";
	}
	
	@GetMapping("/newUsuario")
	public String agregar(Model model) {
		Usuario usuario = new Usuario();
		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", "Agregar usuario");
		return "newUsuario";
	}
	
	@PostMapping("/newUsuario")
	public String guardar(@Valid Usuario usuario, BindingResult result,RedirectAttributes flash, 
			SessionStatus status, Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Agregar Usuario");
			return "newUsuario";
		}

		String messageFlash = (usuario.getId() != null)? "Usuario Actualizado con exito" : "Usuario Registrado con exito";
		String sinCodificar = usuario.getPassword();
		//encripta la contraseña ingresada desde el form
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String codificada = passwordEncoder.encode(sinCodificar);
		usuario.setPassword(codificada);
		
		usuarioService.saveUsuario(usuario);
		status.setComplete();
		flash.addFlashAttribute("success", messageFlash);
		return "redirect:principal";
	}
	
	@GetMapping("/newUsuario/{id}")
	public String editar(@PathVariable(name = "id") Long id, Model model, RedirectAttributes flash) {
		Usuario usuario = null;
		if(id > 0) {
			usuario = usuarioService.findOne(id);
			model.addAttribute("usuario", usuario);
			model.addAttribute("titulo", "Editar Usuario");
			
			if(usuario == null) {
				flash.addFlashAttribute("error", "El id no existe en la base de datos");
				return "redirect:/principal";
			}
			
		}else {
			flash.addFlashAttribute("error", "Error! el id no puede ser cero");
			return "redirect:/principal";
		}
		
		return "newUsuario";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar (@PathVariable(value = "id")Long id, RedirectAttributes flash) {
		if(id > 0) {
			usuarioService.deleteUsuario(id);
			flash.addFlashAttribute("success", "usuario eliminado con exito");
		}
		return "redirect:/principal";
	}

}
