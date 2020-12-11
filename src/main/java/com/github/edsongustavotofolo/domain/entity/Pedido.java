package com.github.edsongustavotofolo.domain.entity;

import com.github.edsongustavotofolo.domain.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    private LocalDate dataPedido;
    @Column(name = "total", precision = 20, scale = 2)
    private BigDecimal total;
    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens;
    @Enumerated(EnumType.STRING)
    private StatusPedido status;

}
