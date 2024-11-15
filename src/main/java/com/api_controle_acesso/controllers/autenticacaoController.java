package com.api_controle_acesso.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.api_controle_acesso.DTOs.AuthDTO.LoginDTO;
import com.api_controle_acesso.DTOs.AuthDTO.RequestCodeDTO;
import com.api_controle_acesso.DTOs.AuthDTO.ResetPasswordDTO;
import com.api_controle_acesso.DTOs.AuthDTO.TokenDTO;
import com.api_controle_acesso.DTOs.AuthDTO.VerifyCodeDTO;
import com.api_controle_acesso.models.Usuario;
import com.api_controle_acesso.services.EmailService;
import com.api_controle_acesso.services.JWTService;
import com.api_controle_acesso.services.PasswordResetTokenService;
import com.api_controle_acesso.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
public class autenticacaoController {
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    HttpServletRequest request;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDTO loginDto) {

        var authToken = new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.senha());
        var usuario = usuarioService.findUsuarioByEmail(loginDto.email());

        var authentication = authenticationManager.authenticate(authToken);

        var jwtToken = jwtService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), jwtToken, usuario.getRole()));
    }

    @GetMapping("/validarToken")
    public ResponseEntity<Object> validarToken(@RequestHeader(name = "Authorization", defaultValue = "") String auth) throws Exception {

        jwtService.tokenValido(auth);
        
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/resetpassword")
    public ResponseEntity<String> resetarSenha(@RequestHeader(name = "Authorization", defaultValue = "") String token, @RequestBody ResetPasswordDTO newPassword) {
        boolean isValidToken = jwtService.tokenValido(token);
        if (isValidToken) {
            var usuario = usuarioService.findUsuarioByEmail(jwtService.getSubject(token));
            usuario.setSenha(passwordEncoder.encode(newPassword.senha()));
            
            usuarioService.updateUsuario(usuario);

            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/requestcode")
    public ResponseEntity<String> requestCode(@RequestBody RequestCodeDTO requestCodeDTO) {
        var usuario = usuarioService.findUsuarioByEmail(requestCodeDTO.email());
        if (usuario != null) {
            String verificationCode = passwordResetTokenService.generateVerificationCode(usuario.getEmail());
            emailService.sendPasswordResetCode(usuario.getEmail(), verificationCode);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/verifycode")
    public ResponseEntity<Object> verifyCode(@RequestBody VerifyCodeDTO verifyCodeDTO) {
        boolean isValidCode = passwordResetTokenService.verifyVerificationCode(verifyCodeDTO.email(), verifyCodeDTO.code());
        if (!isValidCode) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Código de verificação inválido.");
        }

        var usuario = usuarioService.findUsuarioByEmail(verifyCodeDTO.email());
        
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado.");
        }

        String jwtToken = jwtService.gerarToken(usuario);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok(new TokenDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), jwtToken, usuario.getRole()));

    }
}