package com.api_controle_acesso.controllers;
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
import com.api_controle_acesso.DTOs.SetorDTO.SetorPostDTO;
import com.api_controle_acesso.DTOs.SetorDTO.SetorPutDTO;
import com.api_controle_acesso.DTOs.SetorDTO.SetorReturnGetDTO;
import com.api_controle_acesso.services.SetorService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/setor")
public class SetorController {

    @Autowired
    private SetorService setorService;

    @PostMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> criarSetor(@RequestBody @Valid SetorPostDTO setorPostDTO, UriComponentsBuilder uriComponentsBuilder) {
        var setor = setorService.criarSetor(setorPostDTO);
        var uri = uriComponentsBuilder.path("curso/{id}").buildAndExpand(setor.getId()).toUri();

        return ResponseEntity.created(uri).body(new SetorReturnGetDTO(setor));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Page<SetorReturnGetDTO>> visualizarSetores(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        return ResponseEntity.ok().body(setorService.visualizarSetores(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> visualizarSetor(@PathVariable Long id) {
        
        var setor = setorService.visualizarSetor(id);
        return ResponseEntity.ok().body(new SetorReturnGetDTO(setor));
    }

    @PutMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> atualizarSetor(@RequestBody @Valid SetorPutDTO setorPutDTO) {

        var setor = setorService.visualizarSetor(setorPutDTO.id());
        setor.update(setorPutDTO);

        return ResponseEntity.ok().body(new SetorReturnGetDTO(setor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> deleteSetor(@PathVariable Long id) {
        setorService.deleteSetor(id);

        return ResponseEntity.noContent().build();
    } 
}
