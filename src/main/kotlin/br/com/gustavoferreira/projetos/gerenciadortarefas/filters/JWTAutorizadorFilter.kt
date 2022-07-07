package br.com.gustavoferreira.projetos.gerenciadortarefas.filters

import br.com.gustavoferreira.projetos.gerenciadortarefas.authorization
import br.com.gustavoferreira.projetos.gerenciadortarefas.bearer
import br.com.gustavoferreira.projetos.gerenciadortarefas.impl.UsuarioDetailImpl
import br.com.gustavoferreira.projetos.gerenciadortarefas.models.Usuario
import br.com.gustavoferreira.projetos.gerenciadortarefas.utils.JWTUtils
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAutorizadorFilter(authenticationManager: AuthenticationManager, val jwtUtils : JWTUtils)
    : BasicAuthenticationFilter(authenticationManager) {

    //RECEBE A REQUISIÇÃO, RESPOSTA E A CHAIN
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        //PEGO NOS HEADERS REQUEST SE VEIO
        val authorizationHeader = request.getHeader(authorization)

        //SE TEM O AUTHORIZATION COM O INICIO NA PALAVRA BEARER = SE TIVER ELE VAI SER TRATADO
        if (authorizationHeader != null && authorizationHeader.startsWith(bearer)){
            //DEVOLVE AUTORIZADO
            val autorizado = getAuthetication(authorizationHeader)
            //PASSA O CONTEXTO
            SecurityContextHolder.getContext().authentication = autorizado
        }
        //CADEIA DE FILTROS
        chain.doFilter(request, response)
    }

    //PASSA O TOKEN AUTENTICADO
    private fun getAuthetication(authorization: String): UsernamePasswordAuthenticationToken {
        val token = authorization.substring(7)
        if (jwtUtils.isTokenValido(token)){
            val idString = jwtUtils.getUsuario(token)
            if (!idString.isNullOrEmpty() && !idString.isNullOrBlank()){
                val usuario = Usuario(idString.toLong(), "userTest","user@gmail.com","1234")
                val usuarioImpl = UsuarioDetailImpl(usuario)
                return UsernamePasswordAuthenticationToken(
                    usuarioImpl,
                    null,
                    usuarioImpl.authorities)
            }
        }
        throw UsernameNotFoundException("Token informado não esta valiado, ou não tem uma informação identificação do usuario")
    }
}