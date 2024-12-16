package com.pjff.springcloud.msvc.cursos.services;

import java.util.List;
import java.util.Optional;

import com.pjff.springcloud.msvc.cursos.models.Usuario;
import com.pjff.springcloud.msvc.cursos.models.entity.Curso;

public interface CursoService {
    // Paso 3, vid 14

    List<Curso> listar();

    Optional<Curso> porId(Long id);

    // Vid 40
    Optional<Curso> porIdConUsuarios(Long id);

    Curso guardar(Curso curso);

    void eliminar(Long id);

    // Vid 41
    void eliminarCursoUsuarioPorId(Long id);

    // Vid 34, le enviamos un objeto usuario

    Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);

    Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);

    Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);
}
