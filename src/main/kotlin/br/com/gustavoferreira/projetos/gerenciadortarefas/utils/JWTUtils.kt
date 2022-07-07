package br.com.gustavoferreira.projetos.gerenciadortarefas.utils

import io.jsonwebtoken.Claims
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

    //METODO PARA VALIDAR TOKEN
    fun isTokenValido (token : String) : Boolean {
        val clains = getClaimsToken(token)

        //TOKEN VALIDO
        if (clains != null){
            val idUsuario = clains.subject
            if (!idUsuario.isNullOrEmpty() && !idUsuario.isNullOrBlank()){
                return true
            }
        }
        //TOKEN NAO VALIDO
        return false
    }

    //RECEBE O TOKEN SE FUNCIONAR PEGA O PARSER DO JWTS,
    //PASSA A CHAVE DE SEGURANÇA DO NOSSO TOKEN E
    //PARSEA NOSSO TOKEN EM CLAIMS
    private fun getClaimsToken(token: String) = try {
            Jwts.parser().setSigningKey(chaveSeguranca.toByteArray()).parseClaimsJws(token).body
        }catch (exception : Exception){
            null
        }

    //DEVOLVE O ID DO USUARIO
    fun  getUsuario (token : String) : String? {
        val claims = getClaimsToken(token)
        return claims?.subject
    }
}