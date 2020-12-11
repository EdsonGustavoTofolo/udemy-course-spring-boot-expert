package com.github.edsongustavotofolo.service;

import com.github.edsongustavotofolo.domain.entity.Pedido;
import com.github.edsongustavotofolo.domain.enums.StatusPedido;
import com.github.edsongustavotofolo.rest.dto.PedidoDTO;

import java.util.Optional;

public interface IPedidoService {
    Pedido salvar(PedidoDTO dto);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido status);
}
