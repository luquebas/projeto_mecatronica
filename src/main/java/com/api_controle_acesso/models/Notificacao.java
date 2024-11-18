package com.api_controle_acesso.models;
import com.api_controle_acesso.DTOs.NotificacaoDTO.NotificacaoPostDTO;
import com.api_controle_acesso.DTOs.NotificacaoDTO.NotificacaoPutDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Notificacao")
@Table(name = "notificacao")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class Notificacao {

    public Notificacao(NotificacaoPostDTO notificacaoPostDTO) {
        this.usuario = notificacaoPostDTO.usuario();
        this.mensagem = notificacaoPostDTO.mensagem();
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "usuario_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Usuario usuario;

    @Column(name = "mensagem")
    private String mensagem;

    @Column(name = "lida")
    private boolean lida;


    public void update(@Valid NotificacaoPutDTO notificacaoPutDTO) {

        if (notificacaoPutDTO.usuario() != null)
            this.usuario = notificacaoPutDTO.usuario();

        if (notificacaoPutDTO.mensagem() != null)
            this.mensagem = notificacaoPutDTO.mensagem();

        if (!notificacaoPutDTO.lida())
            this.lida = notificacaoPutDTO.lida();
        else
            this.lida = notificacaoPutDTO.lida();

    }

}
