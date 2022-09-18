package com.nj.minhasFinancias.model.repository;


import com.nj.minhasFinancias.entity.model.Usuario;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void verificarAExistenciaDeUmEmailIgual() {
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);


        boolean result = repository.existsByEmail("usuario@email.com");


        Assertions.assertThat(result).isTrue();
    }


    @Test
     void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {


        boolean result = repository.existsByEmail("usuario@email.com");

        Assertions.assertThat(result).isFalse();
    }

    @Test
    void devePersistirUmUsuarioNaBaseDeDados(){
        Usuario usuario = criarUsuario();

          Usuario usuarioSalvo = repository.save(usuario);

          Assertions.assertThat(usuarioSalvo.getId()).isNotNull();

    }


    @Test
    void deveBuscarUsuarioPorEmail(){
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);


        Optional<Usuario> result = repository.findByEmail("usuario@email.com");


        Assertions.assertThat(result.isPresent()).isTrue();


    }


    @Test
    void deveRetornarVazioAoBuscarUsuarioPorEmailQuandoNaoExisteNaBase(){



        Optional<Usuario> result = repository.findByEmail("usuario@email.com");


        Assertions.assertThat(result.isPresent()).isFalse();


    }


    public static Usuario criarUsuario(){
        return  Usuario.builder().nome("usuario").email("usuario@email.com").senha("senha").build();
    }



}


