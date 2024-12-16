package com.pjff.springcloud.msvc.cursos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pjff.springcloud.msvc.cursos.clients.UsuarioClientRest;
import com.pjff.springcloud.msvc.cursos.models.Usuario;
import com.pjff.springcloud.msvc.cursos.models.entity.Curso;
import com.pjff.springcloud.msvc.cursos.models.entity.CursoUsuario;
import com.pjff.springcloud.msvc.cursos.repositories.CursoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Paso 4, Vid 23 ,implementamos todos los metodos con service que es la semantica de un componente
@Service
public class CursoServiceImpl implements CursoService {

    // Inyectamos el repositprio
    @Autowired
    private CursoRepository repository;

    // Vid 36 inyectamos client
    @Autowired
    private UsuarioClientRest client;

    @Override
    // Le ponemos el transactional
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        // Implementamos los metodos y hacemos el cast
        return (List<Curso>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(Long id) {
        return repository.findById(id);
    }

    // Vid 40
    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porIdConUsuarios(Long id) {
        Optional<Curso> o = repository.findById(id);
        // si esta presentende obtenemos el curso
        if (o.isPresent()) {
            Curso curso = o.get();
            // Si no esta vacia
            if (!curso.getCursoUsuarios().isEmpty()) {
                // sino obtenemos la lista de ids,devolvemos el objeto id
                List<Long> ids = curso.getCursoUsuarios().stream().map(cu -> cu.getUsuarioId())
                        .collect(Collectors.toList());
                // lo transformaremos a tipo list con : .collect(Collectors.toList()

                // Los vamos a buscar
                List<Usuario> usuarios = client.obtenerAlumnosPorCurso(ids);
                // le pasamos la lista de suarios
                curso.setUsuarios(usuarios);
            }
            // retornamos la lista modificada
            return Optional.of(curso);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return repository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    // Vid 41
    @Override
    @Transactional
    public void eliminarCursoUsuarioPorId(Long id) {
        repository.eliminarCursoUsuarioPorId(id);
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        // Vid 36,Buscamos si el cursoexiste
        Optional<Curso> o = repository.findById(cursoId);
        if (o.isPresent()) {
            // Nos comunicamos con el curso y lo buscamos con el cliente.
            Usuario usuarioMsvc = client.detalle(usuario.getId());

            // Obtenemos el curso
            Curso curso = o.get();
            // creamos la instancia que maneja la relacion
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            // asignamos el usuario
            curso.addCursoUsuario(cursoUsuario);
            repository.save(curso);
            // Devolvemos el usuario
            return Optional.of(usuarioMsvc);
        }

        return Optional.empty();
    }

    // Vid 36, Se parece al mismo metodo de alla ariba
    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = repository.findById(cursoId);
        if (o.isPresent()) {
            Usuario usuarioNuevoMsvc = client.crear(usuario);

            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioNuevoMsvc.getId());

            curso.addCursoUsuario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioNuevoMsvc);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    // Vid 36,
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = repository.findById(cursoId);
        if (o.isPresent()) {
            Usuario usuarioMsvc = client.detalle(usuario.getId());

            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.removeCursoUsuario(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMsvc);
        }

        return Optional.empty();
    }
}
