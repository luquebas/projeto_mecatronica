package com.api_controle_acesso.controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.api_controle_acesso.DTOs.UsuarioDTO.UsuarioPostDTO;
import com.api_controle_acesso.DTOs.UsuarioDTO.UsuarioPutDTO;
import com.api_controle_acesso.DTOs.UsuarioDTO.UsuarioReturnDTO;
import com.api_controle_acesso.models.enums.Role;
import com.api_controle_acesso.services.SetorService;
import com.api_controle_acesso.services.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SetorService setorService;

    Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    
    @PostMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> criarUsuario(@RequestBody @Valid UsuarioPostDTO dadosUsuario, UriComponentsBuilder uriComponentsBuilder, Authentication authentication) {

        var usuario = usuarioService.criarUsuario(dadosUsuario);
        var uri = uriComponentsBuilder.path("usuario/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(uri).body(new UsuarioReturnDTO(usuario));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UsuarioReturnDTO>> getUsuarios(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {

        return ResponseEntity.ok(usuarioService.visualizarUsuarios(pageable));
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUsuario(@PathVariable Long id) {
        var usuario = usuarioService.visualizarUsuario(id);
        return ResponseEntity.ok(new UsuarioReturnDTO(usuario));
    }

    @PutMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> updateUsuario(@Valid @RequestBody UsuarioPutDTO usuarioPutDTO) {
        var usuario = usuarioService.visualizarUsuario(usuarioPutDTO.id());
        
        if (usuarioPutDTO.setor() != null) {
            var setor = setorService.visualizarSetor(usuarioPutDTO.setor().getId());
            usuario.setSetor(setor);
        }
        
        usuario.update(usuarioPutDTO);

        return ResponseEntity.ok().body(new UsuarioReturnDTO(usuario));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/admin/{id}")
    @Transactional
    public ResponseEntity<Object> updateRoleAdmin(@PathVariable Long id) {

        var usuario = usuarioService.visualizarUsuario(id);
        usuario.setRole(Role.ROLE_ADMIN);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping("/intermediate/{id}")
    @Transactional
    public ResponseEntity<Object> updateRoleIntermediate(@PathVariable Long id) {

        var usuario = usuarioService.visualizarUsuario(id);
        usuario.setRole(Role.ROLE_INTERMEDIATE);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> deleteUsuario(@PathVariable Long id) {
       
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
