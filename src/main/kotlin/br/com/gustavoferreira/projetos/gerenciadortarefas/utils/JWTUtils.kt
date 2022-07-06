package br.com.gustavoferreira.projetos.gerenciadortarefas.utils

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component


@Component
class JWTUtils {
    private val chaveSeguranca = "MinhaChaveDeSegurancaSuperSecretaKotlinNaoCompartilhar"

    //Método para gerar TOKEN de SEGURANÇA
    fun gerarToken(idUsuario : String) : String{
        return Jwts.builder()
            .setSubject(idUsuario)
            //qual o algoritmo da criptografia e a chave
            .signWith(SignatureAlgorithm.HS512, chaveSeguranca.toByteArray())
            //Compacta tudo em uma string
            .compact()
    }
}