package com.api_controle_acesso.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import com.api_controle_acesso.DTOs.ManutencaoDTO.ManutencaoPostDTO;
import com.api_controle_acesso.DTOs.ManutencaoDTO.ManutencaoPutDTO;
import com.api_controle_acesso.DTOs.ManutencaoDTO.ManutencaoReturnGetDTO;
import com.api_controle_acesso.exceptions.ValidacaoException;
import com.api_controle_acesso.models.enums.TipoManutencao;
import com.api_controle_acesso.services.ManutencaoService;
import com.api_controle_acesso.services.ValidadorDataUtilService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/manutencao")
public class ManutencaoController {
   
    @Autowired
    private ManutencaoService manutencaoService;

    @Autowired
    private ValidadorDataUtilService validadorDataUtilService;

    Logger logger = LoggerFactory.getLogger(ManutencaoController.class);

    @PostMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> criarManutencao(@RequestBody @Valid ManutencaoPostDTO manutencaoPostDTO, UriComponentsBuilder uriComponentsBuilder) {
        var manutencao = manutencaoService.criarManutencao(manutencaoPostDTO);

        var uri = uriComponentsBuilder.path("manutencao/{id}").buildAndExpand(manutencao.getId()).toUri();
        logger.info(manutencao.getAtivo().getNome());

        return ResponseEntity.created(uri).body(new ManutencaoReturnGetDTO(manutencao));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INTERMEDIATE')")
    public ResponseEntity<Object> visualizarManutencao(@PathVariable Long id) {
        
        var manutencao = manutencaoService.visualizarManutencao(id);
        return ResponseEntity.ok().body(new ManutencaoReturnGetDTO(manutencao));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INTERMEDIATE')")
    public ResponseEntity<Page<ManutencaoReturnGetDTO>> visualizarManutencoes(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        return ResponseEntity.ok().body(manutencaoService.visualizarManutencoes(pageable));
    }

    @GetMapping("/setor/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INTERMEDIATE')")
    public ResponseEntity<Page<ManutencaoReturnGetDTO>> visualizarManutencoesPorSetor(@PathVariable Long setorId,@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        return ResponseEntity.ok().body(manutencaoService.visualizarManutencoesPorSetor(setorId, pageable));
    }

    @GetMapping("/usuario/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INTERMEDIATE')")
    public ResponseEntity<Page<ManutencaoReturnGetDTO>> visualizarManutencoesPorUsuario(@PathVariable Long usuarioId,@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        return ResponseEntity.ok().body(manutencaoService.visualizarManutencoesPorUsuario(usuarioId, pageable));
    }

    @GetMapping("/ativo/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INTERMEDIATE')")
    public ResponseEntity<Page<ManutencaoReturnGetDTO>> visualizarManutencoesPorAtivo(@PathVariable Long ativoId, @PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        return ResponseEntity.ok().body(manutencaoService.visualizarManutencoesPorAtivo(ativoId, pageable));
    }

    @PutMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> atualizarManutencao(@RequestBody @Valid ManutencaoPutDTO manutencaoPutDTO) {
        
        if (manutencaoPutDTO.tipoManutencao() == TipoManutencao.PREVENTIVA) {
            if(validadorDataUtilService.isDiaUtil(manutencaoPutDTO.prazo())) {
                throw new ValidacaoException("Não é possível realizar Manutenções preventivas em dias úteis");
            }
        }

        var manutencao = manutencaoService.visualizarManutencao(manutencaoPutDTO.id());
        manutencao.update(manutencaoPutDTO);

        return ResponseEntity.ok().body(new ManutencaoReturnGetDTO(manutencao));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteManutencao(@PathVariable Long id) {
        manutencaoService.deleteManutencao(id);

        return ResponseEntity.noContent().build();
    } 
}