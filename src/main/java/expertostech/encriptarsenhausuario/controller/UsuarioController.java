package expertostech.encriptarsenhausuario.controller;

import expertostech.encriptarsenhausuario.model.UsuarioModel;
import expertostech.encriptarsenhausuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private UsuarioService usuarioService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/listarTodos")
    public ResponseEntity<List<UsuarioModel>> listarTodos() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @PostMapping("/salvar")
    public ResponseEntity<UsuarioModel> salvar(@RequestBody UsuarioModel usuarioModel) {
        usuarioModel.setPassword(passwordEncoder.encode(usuarioModel.getPassword()));
        return ResponseEntity.ok(usuarioService.save(usuarioModel));
    }

    //pra saber se a minha senha é válida ou não
    @GetMapping("/validarSenha")
    public ResponseEntity<Boolean> validarSenha(@RequestParam String login, @RequestParam String password) {

        //procuro o usuário no BD pelo login passado por parâmetro pelo Postman
        Optional<UsuarioModel> optionalUsuarioModel = usuarioService.findByLogin(login);
        if (optionalUsuarioModel.isEmpty()) {
            //se o usuário não for encontrado pelo login eu já retorno um NÃO AUTORIZADO
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
        //se o usuário for encontrado:
        UsuarioModel usuarioModel = optionalUsuarioModel.get();
        //verifico se a senha informada pelo postman é a mesma que eu tenho no BD
        //"matches" compara as 2 senhas: a encriptada e a aberta
        boolean valid = passwordEncoder.matches(password, usuarioModel.getPassword());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);
    }
}
