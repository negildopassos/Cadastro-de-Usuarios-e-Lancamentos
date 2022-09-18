package com.nj.minhasFinancias.service;

import com.nj.minhasFinancias.entity.model.Usuario;



public interface UsuarioService {

    Usuario autenticar(String email, String senha);


    Usuario salvarUsuario(Usuario usuario);

    void validarEmail(String email);
}
