package com.api_controle_acesso.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.api_controle_acesso.DTOs.NotificacaoDTO.NotificacaoPostDTO;
import com.api_controle_acesso.DTOs.NotificacaoDTO.NotificacaoReturnGetDTO;
import com.api_controle_acesso.models.Notificacao;
import com.api_controle_acesso.models.Usuario;
import com.api_controle_acesso.repositories.NotificacaoRepository;

@Service
public class NotificacaoService {
    
    @Autowired
    private NotificacaoRepository notificacaoRepository;

    public Notificacao criarNotificacao(NotificacaoPostDTO notificacaoPostDTO, Usuario usuario) {
        
        var notificacao = new Notificacao(notificacaoPostDTO);
        notificacao.setUsuario(usuario);

        return notificacaoRepository.save(notificacao);
    }

    public Notificacao visualizarNotificacao(Long id) {
        return notificacaoRepository.getReferenceById(id);
    }

    public Page<NotificacaoReturnGetDTO> visualizarNotificacoes(Pageable pageable) {
        var page = notificacaoRepository.findAll(pageable).map(NotificacaoReturnGetDTO::new);
        return page;
    }


    public void deleteNotificacao(Long id) {
        var notificacao = notificacaoRepository.getReferenceById(id);
        try {
            notificacaoRepository.delete(notificacao);
        } catch (Exception e) {
            throw new RuntimeException("Não foi Possível excluir a Notificação!");
        }
    }
}
