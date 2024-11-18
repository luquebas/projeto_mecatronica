package com.api_controle_acesso.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.api_controle_acesso.DTOs.ManutencaoDTO.ManutencaoPostDTO;
import com.api_controle_acesso.DTOs.ManutencaoDTO.ManutencaoReturnGetDTO;
import com.api_controle_acesso.exceptions.ValidacaoException;
import com.api_controle_acesso.models.Manutencao;
import com.api_controle_acesso.models.enums.TipoManutencao;
import com.api_controle_acesso.repositories.ManutencaoRepository;

@Service
public class ManutencaoService {
    
    @Autowired
    private ManutencaoRepository manutencaoRepository;

    @Autowired
    private ValidadorDataUtilService validadorDataUtilService;

    public Manutencao criarManutencao(ManutencaoPostDTO manutencaoPostDTO) {
        if (manutencaoPostDTO.tipoManutencao() == TipoManutencao.PREVENTIVA) {
            if(validadorDataUtilService.isDiaUtil(manutencaoPostDTO.prazo())) {
                throw new ValidacaoException("Não é possível realizar Manutenções preventivas em dias úteis");
            }
        }
        
        var manutencao = new Manutencao(manutencaoPostDTO);
        manutencao.setConcluida(false);
        manutencaoRepository.save(manutencao);

        return manutencaoRepository.getReferenceById(manutencao.getId());
    }

    public Manutencao visualizarManutencao(Long id) {
        return manutencaoRepository.getReferenceById(id);
    }

    public Page<ManutencaoReturnGetDTO> visualizarManutencoes(Pageable pageable) {
        var page = manutencaoRepository.findAll(pageable).map(ManutencaoReturnGetDTO::new);
        return page;
    }

    public Page<ManutencaoReturnGetDTO> visualizarManutencoesPorMes(Pageable pageable, int mes, int ano) {
        var page = manutencaoRepository.findByMonthAndYear(mes, ano, pageable).map(ManutencaoReturnGetDTO::new);
        return page;
    }

    public Page<ManutencaoReturnGetDTO> visualizarManutencoesPorSetor(Long setorId, Pageable pageable) {
        var page = manutencaoRepository.findBySetorId(setorId, pageable).map(ManutencaoReturnGetDTO::new);
        return page;
    }

    public Page<ManutencaoReturnGetDTO> visualizarManutencoesPorAtivo(Long ativoId, Pageable pageable ) {
        var page = manutencaoRepository.findByAtivoId(ativoId, pageable).map(ManutencaoReturnGetDTO::new);
        return page;
    }

    public Page<ManutencaoReturnGetDTO> visualizarManutencoesPorUsuario(Long usuarioId, Pageable pageable) {
        var page = manutencaoRepository.findByUsuarioId(usuarioId, pageable).map(ManutencaoReturnGetDTO::new);
        return page;
    }

    public void deleteManutencao(Long id) {
        var manutencao = manutencaoRepository.getReferenceById(id);
        try {
            manutencaoRepository.delete(manutencao);
        } catch (Exception e) {
            throw new RuntimeException("Não foi Possível excluir essa Manutenção!");
        }
    }
}
