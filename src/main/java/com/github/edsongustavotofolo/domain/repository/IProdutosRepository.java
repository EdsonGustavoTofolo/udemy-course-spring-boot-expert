package com.github.edsongustavotofolo.domain.repository;

import com.github.edsongustavotofolo.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProdutosRepository extends JpaRepository<Produto, Integer> {
}
