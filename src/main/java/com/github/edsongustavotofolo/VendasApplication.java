package com.github.edsongustavotofolo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * o extends SpringBootServletInitializer é pra poder compilar o war
 */
@SpringBootApplication
public class VendasApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner runWithJpaRepository(
//            @Autowired IClientesRepository clientesRepository,
//            @Autowired IPedidosRepository pedidosRepository) {
//        return args -> {
//            Cliente edson = new Cliente();
//            edson.setNome("Edson");
//            clientesRepository.save(edson);
//
//            Pedido pedido = new Pedido(edson, LocalDate.now(), BigDecimal.valueOf(1000));
//            pedidosRepository.save(pedido);
//
//            System.out.println("PEDIDOS DO CLIENTE 1");
//            Cliente clienteFound = clientesRepository.findClienteFetchPedidos(edson.getId());
//            System.out.println(clienteFound);
//            System.out.println(clienteFound.getPedidos());
//
//            System.out.println("PEDIDOS DO CLIENTE 2");
//            pedidosRepository.findByCliente(clienteFound).forEach(System.out::println);
//
//            Cliente cliente = new Cliente();
//            cliente.setNome("Edson");
//            clientesRepository.save(cliente);
//
//            Cliente cliente2 = new Cliente();
//            cliente2.setNome("Yrla");
//            clientesRepository.save(cliente2);
//
//            System.out.println("Procurando todos clientes salvos");
//            List<Cliente> list = clientesRepository.findAll();
//            list.forEach(System.out::println);
//
//            cliente2.setNome("Yrla Ferreira");
//            clientesRepository.save(cliente2);
//
//            System.out.println("Procurando todos clientes atualizados");
//            list = clientesRepository.findAll();
//            list.forEach(System.out::println);
//
//            clientesRepository.delete(cliente);
//
//            System.out.println("Procurando todos clientes depois da deleção");
//            list = clientesRepository.findAll();
//            list.forEach(System.out::println);
//
//            System.out.println("Procurando todos clientes com nome Yrla");
//            list = clientesRepository.findByNomeLike("%Yrla%");
//            list.forEach(System.out::println);
//
//        };
//    }


//    @Bean
//    public CommandLineRunner runWithEntityManager(@Autowired ClientesEntityManager entityManager) {
//        return args -> {
//            Cliente cliente = new Cliente();
//            cliente.setNome("Edson");
//            entityManager.salvar(cliente);
//
//            Cliente cliente2 = new Cliente();
//            cliente2.setNome("Yrla");
//            entityManager.salvar(cliente2);
//
//            List<Cliente> list = entityManager.obterTodos();
//            list.forEach(System.out::println);
//
//            cliente2.setNome("Yrla Ferreira");
//            entityManager.atualizar(cliente2);
//
//            list = entityManager.obterTodos();
//            list.forEach(System.out::println);
//
//            entityManager.deletar(1);
//
//            list = entityManager.obterTodos();
//            list.forEach(System.out::println);
//        };
//    }

//    @Bean
//    public CommandLineRunner runWithJdbc(@Autowired ClientesJdbcTemplate clientesJdbcTemplate) {
//        return args -> {
//            Cliente cliente = new Cliente();
//            cliente.setNome("Edson");
//            clientesJdbcTemplate.salvar(cliente);
//
//            Cliente cliente2 = new Cliente();
//            cliente2.setNome("Yrla");
//            clientesJdbcTemplate.salvar(cliente2);
//
//            List<Cliente> list = clientesJdbcTemplate.obterTodos();
//            list.forEach(System.out::println);
//
//            cliente2.setNome("Yrla Ferreira França");
//            clientesJdbcTemplate.atualizar(cliente2);
//
//            list = clientesJdbcTemplate.obterPorNome("Ferreira");
//            list.forEach(System.out::println);
//
//            clientesJdbcTemplate.deletar(1);
//
//            list = clientesJdbcTemplate.obterTodos();
//            list.forEach(System.out::println);
//
//        };
//    }

}
