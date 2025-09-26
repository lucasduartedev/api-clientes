package com.clientes.api_clientes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clientes.api_clientes.dto.DadosAtualizacaoCliente;
import com.clientes.api_clientes.dto.DadosCadastroCliente;
import com.clientes.api_clientes.dto.DadosListagemClientes;
import com.clientes.api_clientes.entity.Cliente;
import com.clientes.api_clientes.repository.ClienteRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid DadosCadastroCliente dados) {
        clienteRepository.save(new Cliente(dados));
    }

    //Retornar apenas: id, nome - DadosListagemClientes
    @GetMapping
    public Page<DadosListagemClientes> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        return clienteRepository.findAllByAtivoTrue(paginacao).map(DadosListagemClientes::new);
    }

    @PutMapping
    @Transactional
    public void atualziar(@RequestBody @Valid DadosAtualizacaoCliente dados) {
        var cliente = clienteRepository.getReferenceById(dados.id());
        cliente.atualizarInformacoes(dados);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void excluir(@PathVariable Long id) {
        // clienteRepository.deleteById(id);
        var cliente = clienteRepository.getReferenceById(id);
        cliente.exluir();
    }

}
