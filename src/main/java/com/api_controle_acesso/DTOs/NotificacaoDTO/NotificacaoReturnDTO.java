package com.api_controle_acesso.DTOs.NotificacaoDTO;
import com.api_controle_acesso.models.Notificacao;

public record NotificacaoReturnDTO(Long id, String mensagem, boolean lida) {
    
    public NotificacaoReturnDTO(Notificacao notificacao) {
        this(notificacao.getId(), notificacao.getMensagem(), notificacao.isLida());
    }
}
