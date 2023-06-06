package com.alancss.devmoneyapi.security;

import com.alancss.devmoneyapi.model.Usuario;
import com.alancss.devmoneyapi.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuarioOptional = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario e/ou senha incorretos"));

        return new User(usuarioOptional.getEmail(), usuarioOptional.getSenha(), getPermissoes(usuarioOptional));
    }

    private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuarioOptional) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        usuarioOptional.getPermissoes().forEach(permissao -> authorities.add(new SimpleGrantedAuthority(permissao.getDescricao().toUpperCase())));
        return authorities;
    }
}
