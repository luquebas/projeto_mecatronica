package com.api_controle_acesso.models;

import java.time.LocalDate;

import com.api_controle_acesso.DTOs.ManutencaoDTO.ManutencaoPostDTO;
import com.api_controle_acesso.models.enums.Criticidade;
import com.api_controle_acesso.models.enums.TipoManutencao;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Manutencao")
@Table(name = "manutencao")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class Manutencao {

    public Manutencao(ManutencaoPostDTO manutencaoPostDTO) {
        this.tipoManutencao = manutencaoPostDTO.tipoManutencao();
        this.ativo = manutencaoPostDTO.criticidade();
        this.descricao = manutencaoPostDTO.descricao();
        this.concluida = manutencaoPostDTO.concluida();
        this.setor = manutencaoPostDTO.setor();
        this.usuario = manutencaoPostDTO.usuario();
        this.prazo = manutencaoPostDTO.prazo();
        this.subconjunto = manutencaoPostDTO.subconjunto();
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "tipo_manutencao")
    private TipoManutencao tipoManutencao;

    @JoinColumn(name = "ativo_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Ativo ativo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "concluida")
    private boolean concluida;

    @JoinColumn(name = "setor_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Setor setor;

    @JoinColumn(name = "usuario_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Usuario usuario;

    @Column(name = "prazo")
    private LocalDate prazo;

    @JoinColumn(name = "subconjunto_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Subconjunto subconjunto;
}
