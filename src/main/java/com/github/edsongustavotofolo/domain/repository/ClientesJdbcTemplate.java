package com.github.edsongustavotofolo.domain.repository;

import com.github.edsongustavotofolo.domain.entity.Cliente;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClientesJdbcTemplate {

    private static final String SELECT_ALL = "select * from cliente";
    private static final String SQL_INSERT = "insert into cliente (nome) values (?)";
    private static final String SQL_UPDATE = "update cliente set nome = ? where id = ?";
    private static final String SQL_DELETE = "delete from cliente where id = ?";

    private final JdbcTemplate jdbcTemplate;

    public ClientesJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Cliente salvar(Cliente cliente) {
        jdbcTemplate.update(SQL_INSERT, new Object[]{cliente.getNome()});
        return cliente;
    }

    public Cliente atualizar(Cliente cliente) {
        jdbcTemplate.update(SQL_UPDATE, new Object[]{cliente.getNome(), cliente.getId()});
        return cliente;
    }

    public void deletar(Cliente cliente) {
        deletar(cliente.getId());
    }

    public void deletar(Integer id) {
        jdbcTemplate.update(SQL_DELETE, new Object[]{id});
    }

    public List<Cliente> obterPorNome(String nome) {
        return jdbcTemplate.query(
                SELECT_ALL.concat(" where nome like ?"),
                new Object[]{"%".concat(nome).concat("%")},
                obterClienteMapper());
    }

    public List<Cliente> obterTodos() {
        return jdbcTemplate.query(SELECT_ALL, obterClienteMapper());
    }

    private RowMapper<Cliente> obterClienteMapper() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Cliente(resultSet.getInt("id"), resultSet.getString("nome"), resultSet.getString("cpf"));
            }
        };
    }

}
