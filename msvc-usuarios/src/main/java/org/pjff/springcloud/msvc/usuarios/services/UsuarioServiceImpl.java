package org.pjff.springcloud.msvc.usuarios.services;

import org.pjff.springcloud.msvc.usuarios.clients.CursoClienteRest;
import org.pjff.springcloud.msvc.usuarios.models.entity.Usuario;
import org.pjff.springcloud.msvc.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//V-13, paso 9, implementamos la interfaz ,es un componente tipo service
@Service
public class UsuarioServiceImpl implements UsuarioService {

    // Paso 10, Inyectamos y hacemos uso del objeto con @Autowired
    @Autowired
    private UsuarioRepository repository;

    // Vid 42
    @Autowired
    private CursoClienteRest client;

    @Override
    // Pas 12, ponemos el transaccion es de spring
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        // find.all devuelve un iterable, hacemos un cast con ()y le mandamos el tipo<>
        // Paso 11
        return (List<Usuario>) repository.findAll();
    }

    // Paso 12, Manejamos transacciones, el listar es de lectura asi que ponemos
    // ,readOnly =
    // true
    @Transactional(readOnly = true)
    @Override
    public Optional<Usuario> porId(Long id) {
        // Buscamos al usuario por el id
        return repository.findById(id);
    }

    // Paso 13
    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        return repository.save(usuario);
    }

    // Paso 14
    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
        // V-42
        client.eliminarCursoUsuarioPorId(id);
    }

    // V-39
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarPorIds(Iterable<Long> ids) {
        return (List<Usuario>) repository.findAllById(ids);
    }

    // V-29
    @Override
    public Optional<Usuario> porEmail(String email) {
        return repository.porEmail(email);
    }

    // V-30
    @Override
    public boolean existePorEmail(String email) {
        return repository.existsByEmail(email);
    }
}
