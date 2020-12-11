package com.github.edsongustavotofolo.service.impl;

import com.github.edsongustavotofolo.domain.entity.Cliente;
import com.github.edsongustavotofolo.domain.entity.ItemPedido;
import com.github.edsongustavotofolo.domain.entity.Pedido;
import com.github.edsongustavotofolo.domain.entity.Produto;
import com.github.edsongustavotofolo.domain.enums.StatusPedido;
import com.github.edsongustavotofolo.domain.repository.IClientesRepository;
import com.github.edsongustavotofolo.domain.repository.IItensPedidoRepository;
import com.github.edsongustavotofolo.domain.repository.IPedidosRepository;
import com.github.edsongustavotofolo.domain.repository.IProdutosRepository;
import com.github.edsongustavotofolo.exception.PedidoNaoEncontradoException;
import com.github.edsongustavotofolo.exception.RegraNegocioException;
import com.github.edsongustavotofolo.rest.dto.ItemPedidoDTO;
import com.github.edsongustavotofolo.rest.dto.PedidoDTO;
import com.github.edsongustavotofolo.service.IPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements IPedidoService {

    private final IPedidosRepository pedidosRepository;
    private final IClientesRepository clientesRepository;
    private final IProdutosRepository produtosRepository;
    private final IItensPedidoRepository itensPedidoRepository;

    @Override
    @Transactional // abre uma transação e commita tudo no final, assim garante que o pedido e os itens serão salvos numa mesma transação
    public Pedido salvar(PedidoDTO dto) {
        Integer clienteId = dto.getCliente();

        Cliente cliente = this.clientesRepository
                .findById(clienteId)
                .orElseThrow(() -> new RegraNegocioException("Código do cliente inválido!")); // Exception tratada no ApplicationControllerAdvice

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> items = converterItens(pedido, dto.getItems());

        this.pedidosRepository.save(pedido);
        this.itensPedidoRepository.saveAll(items);

        pedido.setItens(items);

        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidosRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido status) {
        pedidosRepository.findById(id).map(pedido -> {
            pedido.setStatus(status);
            return pedidosRepository.save(pedido);
        }).orElseThrow(PedidoNaoEncontradoException::new);
    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> items) {
        if (items.isEmpty()) {
            throw  new RegraNegocioException("Não é possível realizar um pedido sem itens");
        }
        return items
                .stream()
                .map(dto -> {
                    Integer produtoId = dto.getProduto();

                    Produto produto = this.produtosRepository
                            .findById(produtoId)
                            .orElseThrow(() -> new RegraNegocioException("Código do produto inválido. ".concat(String.valueOf(produtoId))));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);

                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
