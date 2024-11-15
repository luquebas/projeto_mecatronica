package com.api_controle_acesso.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.api_controle_acesso.DTOs.SetorDTO.SetorPostDTO;
import com.api_controle_acesso.DTOs.SetorDTO.SetorReturnGetDTO;
import com.api_controle_acesso.exceptions.ValidacaoException;
import com.api_controle_acesso.models.Setor;
import com.api_controle_acesso.repositories.SetorRepository;

@Service
public class SetorService {
    
    @Autowired
    private SetorRepository setorRepository;

    public Setor criarSetor(SetorPostDTO setorPostDTO) {
        if (setorRepository.verificarNomeExistente(setorPostDTO.nome()).isPresent())
            throw new ValidacaoException("Já existe um Setor com esse nome!");
        
        var setor = new Setor(setorPostDTO);

        return setorRepository.save(setor);
    }

    public Page<SetorReturnGetDTO> visualizarSetores(Pageable pageable) {
        var page = setorRepository.findAll(pageable).map(SetorReturnGetDTO::new);
        return page;
    }

    public Setor visualizarSetor(Long id) {
        return setorRepository.getReferenceById(id);
    }

    public void deleteSetor(Long id) {
        var setor = setorRepository.getReferenceById(id);
        try {
            setorRepository.delete(setor);
        } catch (Exception e) {
            throw new RuntimeException("Não foi Possível excluir o Setor!");
        }
    }
}
