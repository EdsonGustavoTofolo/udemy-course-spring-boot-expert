package com.github.edsongustavotofolo.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacaoPedidoDTO {
    private Integer codigo;
    private String cpf;
    private String nome;
    private String dataPedido;
    private BigDecimal total;
    private String status;
    private List<InformacaoItemPedidoDTO> items;
}
