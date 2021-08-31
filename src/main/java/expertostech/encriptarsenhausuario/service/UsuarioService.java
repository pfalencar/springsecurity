package expertostech.encriptarsenhausuario.service;

import expertostech.encriptarsenhausuario.model.UsuarioModel;
import expertostech.encriptarsenhausuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRespository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRespository) {
        this.usuarioRespository = usuarioRespository;
    }

    public List<UsuarioModel> findAll() {
        return usuarioRespository.findAll();
    }

    public UsuarioModel save(UsuarioModel usuarioModel) {
        return usuarioRespository.save(usuarioModel);
    }

    public Optional<UsuarioModel> findByLogin(String login) {
        return usuarioRespository.findByLogin(login);
    }
}
