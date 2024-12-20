package com.api_controle_acesso.controllers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.api_controle_acesso.DTOs.NotificacaoDTO.NotificacaoPostDTO;
import com.api_controle_acesso.DTOs.NotificacaoDTO.NotificacaoPutDTO;
import com.api_controle_acesso.DTOs.NotificacaoDTO.NotificacaoReturnGetDTO;
import com.api_controle_acesso.models.Usuario;
import com.api_controle_acesso.services.NotificacaoService;
import com.api_controle_acesso.services.UsuarioService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private UsuarioService usuarioService;

     Logger logger = LoggerFactory.getLogger(NotificacaoController.class);

    @PostMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INTERMEDIATE', 'ROLE_USER')")
    public ResponseEntity<Object> criarNotificacao(@RequestBody @Valid NotificacaoPostDTO notificacaoPostDTO, UriComponentsBuilder uriComponentsBuilder) {
        Usuario usuario = usuarioService.visualizarUsuario(notificacaoPostDTO.usuario().getId());

        var notificacao = notificacaoService.criarNotificacao(notificacaoPostDTO, usuario);
        var uri = uriComponentsBuilder.path("notificacao/{id}").buildAndExpand(notificacao.getId()).toUri();

        return ResponseEntity.created(uri).body(new NotificacaoReturnGetDTO(notificacao));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INTERMEDIATE')")
    public ResponseEntity<Object> visualizarNotificacao(@PathVariable Long id) {
        
        var notificacao = notificacaoService.visualizarNotificacao(id);
        return ResponseEntity.ok().body(new NotificacaoReturnGetDTO(notificacao));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INTERMEDIATE')")
    public ResponseEntity<Page<NotificacaoReturnGetDTO>> visualizarNotificacoes(@PageableDefault(size = 10, sort = {"usuario"}) Pageable pageable) {
        return ResponseEntity.ok().body(notificacaoService.visualizarNotificacoes(pageable));
    }

    @PutMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> atualizarNotificacao(@RequestBody @Valid NotificacaoPutDTO notificacaoPutDTO) {
        Usuario usuario = usuarioService.visualizarUsuario(notificacaoPutDTO.usuario().getId());

        var notificacao = notificacaoService.visualizarNotificacao(notificacaoPutDTO.id());
        notificacao.update(notificacaoPutDTO);
        notificacao.setUsuario(usuario);

        return ResponseEntity.ok().body(new NotificacaoReturnGetDTO(notificacao));
    }


    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteNotificacao(@PathVariable Long id) {
        notificacaoService.deleteNotificacao(id);

        return ResponseEntity.noContent().build();
    } 
}
