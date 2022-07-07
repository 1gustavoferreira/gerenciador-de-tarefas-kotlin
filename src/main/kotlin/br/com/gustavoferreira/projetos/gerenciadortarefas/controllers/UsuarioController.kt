package br.com.gustavoferreira.projetos.gerenciadortarefas.controllers

import br.com.gustavoferreira.projetos.gerenciadortarefas.models.Usuario
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/usuario")
class UsuarioController {

    @GetMapping
    fun obterUsuario() = Usuario(1,"User Test", "email@test", "")
}