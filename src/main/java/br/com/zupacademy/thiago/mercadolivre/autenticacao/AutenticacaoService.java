package br.com.zupacademy.thiago.mercadolivre.autenticacao;

import br.com.zupacademy.thiago.mercadolivre.usuario.Usuario;
import br.com.zupacademy.thiago.mercadolivre.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Optional<Usuario> optionalUsuario = usuarioRepository.findByLogin(login);
        if (optionalUsuario.isPresent()) {
            return optionalUsuario.get();
        }

        throw new UsernameNotFoundException("Usuário não existe");
    }
}
