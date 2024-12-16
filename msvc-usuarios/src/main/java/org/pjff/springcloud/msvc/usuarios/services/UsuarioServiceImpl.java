package org.pjff.springcloud.msvc.usuarios.services;

import org.pjff.springcloud.msvc.usuarios.clients.CursoClienteRest;
import org.pjff.springcloud.msvc.usuarios.models.entity.Usuario;
import org.pjff.springcloud.msvc.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//Vid 14, paso 4, implementamos la interfaz ,es un componente tipo service
@Service
public class UsuarioServiceImpl implements UsuarioService {

    // Inyectamos y hacemos uso del objeto con @Autowired
    @Autowired
    private UsuarioRepository repository;

    // Vid 42
    @Autowired
    private CursoClienteRest client;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        // find.all devuelve un iterable hacemos un cast con ()y le mandamos el tipo<>
        return (List<Usuario>) repository.findAll();
    }

    // Manejamos transacciones, el listar es de lectura asi que ponemos ,readOnly =
    // true
    @Transactional(readOnly = true)
    @Override
    public Optional<Usuario> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
        // Vid 42
        client.eliminarCursoUsuarioPorId(id);
    }

    // Vid 39
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarPorIds(Iterable<Long> ids) {
        return (List<Usuario>) repository.findAllById(ids);
    }

    // Vid 29
    @Override
    public Optional<Usuario> porEmail(String email) {
        return repository.porEmail(email);
    }

    // Vid 30
    @Override
    public boolean existePorEmail(String email) {
        return repository.existsByEmail(email);
    }
}
