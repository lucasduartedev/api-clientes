package com.clientes.api_clientes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.clientes.api_clientes.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    @Transactional
    public ResponseEntity<Cliente> cadastrar(@RequestBody @Valid DadosCadastroCliente dados) {
        Cliente cliente = clienteService.cadastrarCliente(new Cliente(dados));
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemClientes>> listarClientes(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        try {
            Page<Cliente> clientes = clienteService.listarClientesAtivos(paginacao);
            Page<DadosListagemClientes> dados = clientes.map(DadosListagemClientes::new);
            return ResponseEntity.ok(dados);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> listarPorId(@PathVariable("id") Long id) {
        return clienteService.buscarPorId(id).map(cliente -> ResponseEntity.ok(cliente))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> atualziarDados(@RequestBody @Valid DadosAtualizacaoCliente dados) {
        // var cliente = clienteRepository.getReferenceById(dados.id());
        // cliente.atualizarInformacoes(dados);
        return clienteService.atualizarCliente(dados)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            var cliente = clienteRepository.getReferenceById(id);
            cliente.desativarCliente();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
