package com.api_controle_acesso.DTOs.NotificacaoDTO;
import com.api_controle_acesso.DTOs.UsuarioDTO.UsuarioReturnDTO;
import com.api_controle_acesso.models.Notificacao;

public record NotificacaoReturnGetDTO(Long id, UsuarioReturnDTO usuario, String mensagem, boolean lida) {
    
    public NotificacaoReturnGetDTO(Notificacao notificacao) {
        this(notificacao.getId(), new UsuarioReturnDTO(notificacao.getUsuario()), notificacao.getMensagem(), notificacao.isLida());
    }
}
