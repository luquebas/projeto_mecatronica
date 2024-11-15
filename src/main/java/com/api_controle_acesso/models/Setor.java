package com.api_controle_acesso.models;
import java.util.List;

import com.api_controle_acesso.DTOs.SetorDTO.SetorPostDTO;
import com.api_controle_acesso.DTOs.SetorDTO.SetorPutDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Setor")
@Table(name = "setor")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Getter
@Setter
public class Setor {
    
    public Setor(SetorPostDTO cursoPostDTO) {
        this.nome = cursoPostDTO.nome();
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @OneToMany(mappedBy = "setor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Usuario> usuarios;

    public void update(@Valid SetorPutDTO setorPutDTO) {
        if (setorPutDTO.nome() != null)
            this.nome = setorPutDTO.nome();
    }
}
