package com.api_controle_acesso.models;
import com.api_controle_acesso.DTOs.SubconjuntoDTO.SubconjuntoPostDTO;
import com.api_controle_acesso.DTOs.SubconjuntoDTO.SubconjuntoPutDTO;
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

@Entity(name = "Subconjunto")
@Table(name = "subconjunto")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class Subconjunto {

    public Subconjunto(SubconjuntoPostDTO subconjuntoPostDTO) {
        this.nome = subconjuntoPostDTO.nome();
        this.ativo = subconjuntoPostDTO.ativo();
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @JoinColumn(name = "ativo_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Ativo ativo;

    public void update(@Valid SubconjuntoPutDTO subconjuntoPutDTO) {

        if (subconjuntoPutDTO.nome() != null)
        this.nome = subconjuntoPutDTO.nome();
        
        if (subconjuntoPutDTO.ativo() != null)
            this.ativo = subconjuntoPutDTO.ativo();

    }
}
