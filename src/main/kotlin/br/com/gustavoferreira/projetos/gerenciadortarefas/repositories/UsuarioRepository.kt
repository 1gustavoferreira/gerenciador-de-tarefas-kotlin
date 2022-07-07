package br.com.gustavoferreira.projetos.gerenciadortarefas.repositories

import br.com.gustavoferreira.projetos.gerenciadortarefas.models.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UsuarioRepository : JpaRepository<Usuario, Long> {
}