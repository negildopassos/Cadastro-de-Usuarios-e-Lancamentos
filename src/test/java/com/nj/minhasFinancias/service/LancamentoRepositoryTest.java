package com.nj.minhasFinancias.service;


import com.nj.minhasFinancias.entity.model.Lancamento;
import com.nj.minhasFinancias.enums.StatusLancamento;
import com.nj.minhasFinancias.enums.TipoLancamento;
import com.nj.minhasFinancias.model.repository.LancamentoRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

    @Autowired
    LancamentoRepository repository;

    @Autowired
    TestEntityManager entityManager;


    @Test
    public void deveSalvarUmLancamento(){
        Lancamento lancamento = criarLancamento();

        lancamento = repository.save(lancamento);

        Assertions.assertThat(lancamento.getId()).isNotNull();
    }






    @Test
    public void deveDeletarUmLancamento(){
        Lancamento lancamento = criarLancamento();
        lancamento = entityManager.persist(lancamento);

       lancamento =  entityManager.find(Lancamento.class, lancamento.getId());

       repository.delete(lancamento);

       Lancamento lancamentoInexsistente = entityManager.find(Lancamento.class, lancamento.getId());
       Assertions.assertThat(lancamentoInexsistente).isNull();

    }


    private Lancamento criarLancamento(){
        return Lancamento.builder().ano(2019).mes(1)
                .descricao("lancamento qualquer").valor(BigDecimal.valueOf(10))
                .tipo(TipoLancamento.RECEITA)
                .status(StatusLancamento.PENDENTE)
                .dataCadastro(LocalDate.now())
                .build();
    }
}
