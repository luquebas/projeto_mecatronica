package com.api_controle_acesso.models;
import com.api_controle_acesso.DTOs.AtivoDTO.AtivoPostDTO;
import com.api_controle_acesso.DTOs.AtivoDTO.AtivoPutDTO;
import com.api_controle_acesso.models.enums.Criticidade;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Ativo")
@Table(name = "ativo")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class Ativo {
    public Ativo(AtivoPostDTO ativoPostDTO) {
        this.nome = ativoPostDTO.nome();
        this.criticidade = ativoPostDTO.criticidade();
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "criticidade")
    private Criticidade criticidade;

    public void update(@Valid AtivoPutDTO ativoPutDTO) {
        if (ativoPutDTO.nome() != null)
            this.nome = ativoPutDTO.nome();

        if(ativoPutDTO.criticidade() != null) {
            this.criticidade = ativoPutDTO.criticidade();
        }
    }
}
