package com.github.edsongustavotofolo.domain.repository;

import com.github.edsongustavotofolo.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IClientesRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByNomeLike(String nome);

    List<Cliente> findByNomeOrId(String nome, Integer id);

    void deleteByNome(String nome);

    @Query("select c from Cliente c left join fetch c.pedidos p where c.id = :id")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);

    @Query("delete from Cliente c where c.nome = :nome")
    @Modifying
    void deletarPorNome(String nome);

    @Query("select c from Cliente c where c.nome like :nome") //HQL
    List<Cliente> encontrarPorNome(@Param("nome") String nome);

    @Query(value = "select c.* from cliente c where c.nome like '%:nome%'", nativeQuery = true) //SQL Nativo
    List<Cliente> encontrarPorId(@Param("nome") String nome);
}
