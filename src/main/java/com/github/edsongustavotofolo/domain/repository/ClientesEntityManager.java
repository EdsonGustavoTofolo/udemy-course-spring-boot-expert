package com.github.edsongustavotofolo.domain.repository;

import com.github.edsongustavotofolo.domain.entity.Cliente;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ClientesEntityManager {

    private final EntityManager entityManager;

    public ClientesEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Cliente salvar(Cliente cliente) {
        entityManager.persist(cliente);
        return cliente;
    }

    @Transactional
    public Cliente atualizar(Cliente cliente) {
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional
    public void deletar(Cliente cliente) {
        if (!entityManager.contains(cliente)) {
            cliente = entityManager.merge(cliente);
        }
        entityManager.remove(cliente);
    }

    @Transactional
    public void deletar(Integer id) {
        Cliente cliente = entityManager.find(Cliente.class, id);
        deletar(cliente);
    }

    @Transactional(readOnly = true)
    public List<Cliente> obterPorNome(String nome) {
        String jpql = "select c from Cliente c where c.nome like :nome";
        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("nome", "%".concat(nome).concat("%") );
        return query.getResultList();
    }

    @Transactional
    public List<Cliente> obterTodos() {
        return entityManager.createQuery("select c from Cliente c", Cliente.class).getResultList();
    }
}
