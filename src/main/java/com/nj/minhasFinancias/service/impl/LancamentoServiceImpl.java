package com.nj.minhasFinancias.service.impl;

import com.nj.minhasFinancias.entity.model.Lancamento;
import com.nj.minhasFinancias.enums.StatusLancamento;
import com.nj.minhasFinancias.exception.RegraNegocioException;
import com.nj.minhasFinancias.model.repository.LancamentoRepository;
import com.nj.minhasFinancias.service.LancamentoService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private LancamentoRepository repository;

    public LancamentoServiceImpl(LancamentoRepository repository){
        this.repository = repository;
    }

    @Override
    @Transactional
    public Lancamento salvar(Lancamento lancamento) {
        validar(lancamento);
        lancamento.setStatus(StatusLancamento.PENDENTE);
        return repository.save(lancamento);
    }

    @Override
    @Transactional
    public Lancamento atualizar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        validar(lancamento);
        return repository.save(lancamento);
    }

    @Override
    @Transactional
    public void deletar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId());
        repository.delete(lancamento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
        Example example = Example.of(lancamentoFiltro,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) );


        return repository.findAll(example) ;
    }

    @Override
    public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
        lancamento.setStatus(status);
        atualizar(lancamento);
    }

    @Override
    public void validar(Lancamento lancamento) {

        if(lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")){
            throw new RegraNegocioException("Informe uma Descrição válida");
        }
        if (lancamento.getMes() == null || lancamento.getMes() <1 || lancamento.getMes() >12){
            throw new RegraNegocioException("Informe um Mês válido");
        }

        if(lancamento.getAno() == null || lancamento.getAno().toString().length() != 4){
            throw new RegraNegocioException("Informe um Ano válido");
        }

        if(lancamento.getUsuario()== null|| lancamento.getUsuario().getId()==null){
            throw  new RegraNegocioException("Infomr um Usuário");
        }

        if(lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) <1 ){
            throw new RegraNegocioException("Informe um Valor válido");
        }

        if(lancamento.getTipo() == null){
            throw new RegraNegocioException("Informe um Tipo de Lançamento");
        }


    }
}