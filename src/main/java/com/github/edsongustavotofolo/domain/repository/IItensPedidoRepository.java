package com.github.edsongustavotofolo.domain.repository;

import com.github.edsongustavotofolo.domain.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItensPedidoRepository extends JpaRepository<ItemPedido, Integer> {
}
