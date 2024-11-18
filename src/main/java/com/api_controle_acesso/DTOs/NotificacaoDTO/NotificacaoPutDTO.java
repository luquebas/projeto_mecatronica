package com.api_controle_acesso.DTOs.NotificacaoDTO;
import com.api_controle_acesso.models.Usuario;
import jakarta.validation.constraints.NotNull;

public record NotificacaoPutDTO(@NotNull Long id, Usuario usuario, String mensagem, boolean lida ) {
    
}
