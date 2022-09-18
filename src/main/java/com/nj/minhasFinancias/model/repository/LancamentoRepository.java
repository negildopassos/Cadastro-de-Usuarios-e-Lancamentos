package com.nj.minhasFinancias.model.repository;

import com.nj.minhasFinancias.entity.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}
