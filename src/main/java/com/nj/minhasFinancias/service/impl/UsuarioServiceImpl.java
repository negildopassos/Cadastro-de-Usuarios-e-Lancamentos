package com.nj.minhasFinancias.service.impl;

import com.nj.minhasFinancias.entity.model.Usuario;
import com.nj.minhasFinancias.exception.ErroAutenticacao;
import com.nj.minhasFinancias.exception.RegraNegocioException;
import com.nj.minhasFinancias.model.repository.UsuarioRepository;
import com.nj.minhasFinancias.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {


    private UsuarioRepository repository;


    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
    Optional<Usuario> usuario = repository.findByEmail(email);
        if (!usuario.isPresent()){
            throw new ErroAutenticacao("Usuario não encontrado para o email informado.");
        }
        if (!usuario.get().getSenha().equals(senha)){
            throw new ErroAutenticacao("Senha inválida");
        }
        return usuario.get();
    }

    @Override
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
    validarEmail(usuario.getEmail());
    return repository.save(usuario);

    }

    @Override
    public void validarEmail(String email) {
    boolean existe = repository.existsByEmail(email);
    if(existe){
        throw new RegraNegocioException("Já existe um Usuario Cadastrado com esse Email");
    }


    }

    @Override
    public Optional<Usuario> obterPorId(Long id) {
        return repository.findById(id);
    }
}
