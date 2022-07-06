package br.com.gustavoferreira.projetos.gerenciadortarefas.dtos

data class LoginRespostaDTO(val nome : String, val email : String, val token : String  = "")
