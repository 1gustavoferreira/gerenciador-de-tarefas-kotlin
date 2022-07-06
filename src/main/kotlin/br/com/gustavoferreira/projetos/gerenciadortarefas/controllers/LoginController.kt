package br.com.gustavoferreira.projetos.gerenciadortarefas.controllers



import br.com.gustavoferreira.projetos.gerenciadortarefas.dtos.ErroDTO
import br.com.gustavoferreira.projetos.gerenciadortarefas.dtos.LoginDTO
import br.com.gustavoferreira.projetos.gerenciadortarefas.dtos.LoginRespostaDTO
import br.com.gustavoferreira.projetos.gerenciadortarefas.utils.JWTUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.RuntimeException

@RestController
@RequestMapping("api/login")
class LoginController {

    private val LOGIN_TESTE = "1234"
    private val SENHA_TESTE = "1234"

    @PostMapping
    fun efetuarLogin(@RequestBody dto : LoginDTO) : ResponseEntity<Any>{
        try{
            if(dto == null || dto.login.isNullOrBlank() || dto.login.isNullOrEmpty()
                || dto.senha.isNullOrEmpty() || dto.senha.isNullOrBlank()
                || dto.login != LOGIN_TESTE || dto.senha != SENHA_TESTE){
                return ResponseEntity(ErroDTO(HttpStatus.BAD_REQUEST.value(),
                    "Parâmetros de entrada inválidos"), HttpStatus.BAD_REQUEST)
            }

            val idUsuario = 1
            val token = JWTUtils().gerarToken(idUsuario.toString())

            val usuarioTeste = LoginRespostaDTO("Usuário Teste", LOGIN_TESTE, token)
            return ResponseEntity(usuarioTeste, HttpStatus.OK)
        }catch (e: Exception){
            return ResponseEntity(ErroDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Não foi possível efetuar o login, tente novamente"), HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }
}