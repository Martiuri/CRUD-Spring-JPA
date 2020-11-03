package com.crud.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.crud.app.entity.Usuario;

public interface IUsuarioService {
	
	public Page<Usuario> findAllUsuario(Pageable pageable);
	
	public void saveUsuario(Usuario usuario);
	
	public void deleteUsuario(Long id);
	
	public Usuario findOne(Long id);
	
	

}
