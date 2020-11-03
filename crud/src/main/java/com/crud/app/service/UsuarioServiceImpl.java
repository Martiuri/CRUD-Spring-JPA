package com.crud.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.crud.app.dao.IUsuarioDao;
import com.crud.app.entity.Usuario;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

	@Autowired
	public IUsuarioDao usuarioDao;
	
	
	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> findAllUsuario(Pageable pageable) {
		return usuarioDao.findAll(pageable);
	}

	@Override
	@Transactional
	public void saveUsuario(Usuario usuario) {
		usuarioDao.save(usuario);
		
	}

	@Override
	@Transactional
	public void deleteUsuario(Long id) {
		usuarioDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findOne(Long id) {
		return usuarioDao.findById(id).orElse(null);
	}

	
}
