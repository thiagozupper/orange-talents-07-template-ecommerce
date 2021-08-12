package br.com.zupacademy.thiago.mercadolivre.autenticacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/autenticacao")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<AutenticacaoResponse> autenticar(@RequestBody @Valid AutenticacaoForm dto) {

        try {
            UsernamePasswordAuthenticationToken dadosLogin = dto.dadosLogin();
            Authentication authentication = authenticationManager.authenticate(dadosLogin); //Vai chamar o AutenticacaoService
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.ok(new AutenticacaoResponse(token, "Bearer"));
        } catch (AuthenticationException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
