package com.clientes.api_clientes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.clientes.api_clientes.dto.DadosAtualizacaoCliente;
import com.clientes.api_clientes.entity.Cliente;
import com.clientes.api_clientes.repository.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;

    ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente cadastrarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Page<Cliente> listarClientesAtivos(Pageable paginacao) {
        
        return clienteRepository.findAllByAtivoTrue(paginacao);
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    public Optional<Object> atualizarCliente(DadosAtualizacaoCliente dados) {
        return clienteRepository.findById(dados.id())
            .map(cliente -> {
                cliente.atualizarInformacoes(dados);
                return cliente;
            });
    }

    // ! Deletar permanente
    public List<Cliente> deletarCliente(Long id) {
        clienteRepository.deleteById(id);
        return listarClientes();
    }

    // *
    @Transactional
    public void exclusaoLogica(Long id) {
        Cliente cliente = clienteRepository.getReferenceById(id);
        cliente.desativarCliente();
    }
    
}
