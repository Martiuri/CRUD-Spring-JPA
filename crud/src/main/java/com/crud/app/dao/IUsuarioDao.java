package com.crud.app.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.crud.app.entity.Usuario;

public interface IUsuarioDao extends PagingAndSortingRepository<Usuario, Long>  {
	
	public Usuario findByUsername(String username);

}
