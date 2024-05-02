package com.mballem.demoparkapi.web.controller;


import com.mballem.demoparkapi.entity.Usuario;
import com.mballem.demoparkapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    /*response ele encapsula sua resposta,
    nesse caso ira encapsular o ususario
    depois vai transformar em json e mandar para o cliente */
    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario){
        Usuario user = usuarioService.salvar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id){
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> updatePassword(@PathVariable Long id, @RequestBody Usuario usuario){
        Usuario user = usuarioService.editarSenha(id, usuario.getPassword());
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll(){
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(users);
    }

}
