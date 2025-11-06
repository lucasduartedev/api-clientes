package com.clientes.api_clientes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.clientes.api_clientes.dto.DadosAtualizacaoCliente;
import com.clientes.api_clientes.entity.Cliente;
import com.clientes.api_clientes.repository.ClienteRepository;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente cliente;

    @BeforeEach
    void setup() {
        clienteRepository.deleteAll();;

        cliente = new Cliente("Carlos Oliveira", "12345678901");

        cliente = clienteRepository.save(cliente);

    }

    @Test
    void deveCadastrarClienteComSucesso() {
        // * Arrange
        Cliente novoCliente = new Cliente("Julia Silva", "45645645699");

        // * Act
        Cliente resultado = clienteService.cadastrarCliente(novoCliente);

        // * Assert
        assertNotNull(resultado.getId());
        assertEquals("Julia Silva", resultado.getNome());
        assertEquals("45645645699", resultado.getCpf());
        assertTrue(resultado.getAtivo());

    }

    @Test
    void deveAtualizarClienteExistente() {

        DadosAtualizacaoCliente novocDados = new DadosAtualizacaoCliente(
            cliente.getId(),
            "Carlos Oliveira Atualizado",
            null);

        Optional<Object> resultado = clienteService.atualizarCliente(novocDados);

        assertTrue(resultado.isPresent());

        Cliente clienteAtualizado = clienteRepository.findById(cliente.getId()).get();

        assertEquals("Carlos Oliveira Atualizado", clienteAtualizado.getNome());
        assertEquals("12345678901", clienteAtualizado.getCpf());

    }

    @Test
    void deveDeletarClientePermanentente() {
        List<Cliente> resultado = clienteService.deletarCliente(cliente.getId());

        Optional<Cliente> clienteDeletar = clienteRepository.findById(cliente.getId());
        assertTrue(clienteDeletar.isEmpty());
        assertTrue(resultado.isEmpty());
    }
    
}
