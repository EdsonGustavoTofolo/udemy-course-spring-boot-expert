package com.github.edsongustavotofolo.security.jwt;

import com.github.edsongustavotofolo.VendasApplication;
import com.github.edsongustavotofolo.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;
    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario) {
        long expString = Long.parseLong(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        Date data = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());

        /* Info extras quando necessário
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("emaildousuario", "email@mail.com");
        claims.put("idade", "35");
        claims.put("outrainformacaoutil", "aqui vai outra informacao util");
        */
        return Jwts.builder()
                .setSubject(usuario.getLogin())
                .setExpiration(data)
                // .setClaims(claims) aqui vai informações extras
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                .compact();
    }

    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();

    }

    public boolean tokenValido(String token) {
        try {
            Claims claims = obterClaims(token);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime data = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(data);
        } catch (Exception e) {
            return false;
        }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException {
        return (String) obterClaims(token).getSubject();
    }


    /* Somente para testes, verificar o token em jwt.io */
    public static void main(String[] args) {
        ConfigurableApplicationContext contexto = SpringApplication.run(VendasApplication.class);
        JwtService jwtService = contexto.getBean(JwtService.class);
        Usuario usuario = Usuario.builder().login("edson").build();

        String token = jwtService.gerarToken(usuario);
        System.out.println(token);

        boolean istokenValido = jwtService.tokenValido(token);
        System.out.println("O token está valido? " + istokenValido);

        System.out.println(jwtService.obterLoginUsuario(token));
    }
}
