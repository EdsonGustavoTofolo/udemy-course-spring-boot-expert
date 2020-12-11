package com.github.edsongustavotofolo.service.impl;

import com.github.edsongustavotofolo.domain.entity.Usuario;
import com.github.edsongustavotofolo.domain.repository.UsuarioRepository;
import com.github.edsongustavotofolo.exception.SenhaInvalidaException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public UserDetails autenticar(Usuario usuario) {
        UserDetails userDetails = loadUserByUsername(usuario.getLogin());
        boolean matches = passwordEncoder.matches(usuario.getSenha(), userDetails.getPassword());
        if (matches) {
            return userDetails;
        } else {
            throw new SenhaInvalidaException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados"));

        String[] roles = usuario.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
        /*
        if (!username.equals("tofolo")) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }

        return User
                .builder()usu
                .password(passwordEncoder.encode("123"))
                .username("tofolo")
                .roles("USER", "ADMIN")
                .build();
         */
    }
}
