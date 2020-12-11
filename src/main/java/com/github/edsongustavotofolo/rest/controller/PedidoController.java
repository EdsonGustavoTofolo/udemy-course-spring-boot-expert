package com.github.edsongustavotofolo.rest.controller;

import com.github.edsongustavotofolo.domain.entity.ItemPedido;
import com.github.edsongustavotofolo.domain.entity.Pedido;
import com.github.edsongustavotofolo.domain.enums.StatusPedido;
import com.github.edsongustavotofolo.rest.dto.AtualizacaoStatusPedidoDTO;
import com.github.edsongustavotofolo.rest.dto.InformacaoItemPedidoDTO;
import com.github.edsongustavotofolo.rest.dto.InformacaoPedidoDTO;
import com.github.edsongustavotofolo.rest.dto.PedidoDTO;
import com.github.edsongustavotofolo.service.IPedidoService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final IPedidoService service;

    public PedidoController(IPedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody PedidoDTO dto) {
        Pedido pedido = this.service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacaoPedidoDTO getById(@PathVariable Integer id) {
        return service
                .obterPedidoCompleto(id)
                .map(this::converter)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado"));
    }

    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto) {
        String novoStatus = dto.getNovoStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }

    private InformacaoPedidoDTO converter(Pedido pedido) {
        return InformacaoPedidoDTO.builder()
                    .codigo(pedido.getId())
                    .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                    .cpf(pedido.getCliente().getCpf())
                    .nome(pedido.getCliente().getNome())
                    .total(pedido.getTotal())
                    .status(pedido.getStatus().name())
                    .items(converter(pedido.getItens()))
                    .build();
    }

    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> lista) {
        if (CollectionUtils.isEmpty(lista)) {
            return Collections.emptyList();
        }
        return lista
                .stream()
                .map( item ->
                        InformacaoItemPedidoDTO.builder()
                                .descricao(item.getProduto().getDescricao())
                                .preco(item.getProduto().getPreco())
                                .quantidade(item.getQuantidade()).build()
                        ).collect(Collectors.toList());
    }

}
