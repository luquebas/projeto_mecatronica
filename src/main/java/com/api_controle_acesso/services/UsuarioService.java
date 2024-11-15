package com.api_controle_acesso.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.api_controle_acesso.DTOs.UsuarioDTO.UsuarioPostDTO;
import com.api_controle_acesso.DTOs.UsuarioDTO.UsuarioReturnDTO;
import com.api_controle_acesso.models.Usuario;
import com.api_controle_acesso.models.enums.Role;
import com.api_controle_acesso.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SetorService setorService;

    @Autowired
    private PasswordEncoder passwordEncoder; 

    public Usuario criarUsuario(UsuarioPostDTO dadosUsuario) {
        var usuario = new Usuario(dadosUsuario);
        var setor = setorService.visualizarSetor(dadosUsuario.setor().getId());

        usuario.setSetor(setor);
        usuario.setRole(Role.ROLE_USER);
        usuario.setSenha(passwordEncoder.encode((dadosUsuario.senha())));

        return usuarioRepository.save(usuario);
    }

    public Page<UsuarioReturnDTO> visualizarUsuarios(Pageable pageable) {
        var page = usuarioRepository.findAll(pageable).map(UsuarioReturnDTO::new);
        return page;
    }

    public Usuario visualizarUsuario(Long id) {
        return usuarioRepository.getReferenceById(id);
    }

    public void deleteUsuario(Long id) {
        var usuario = usuarioRepository.getReferenceById(id);
        try {
            usuarioRepository.delete(usuario);      
        } catch (Exception e) {
            throw new RuntimeException("Não foi Possível deletar esse Usuário!");
        }
    }

    public Usuario findUsuarioByEmail(String email) {
        var usuario = usuarioRepository.findByEmail(email);
        return usuario;
    }

    public void updateUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}
