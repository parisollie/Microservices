package com.pjff.springcloud.msvc.cursos.services;

import java.util.List;
import java.util.Optional;

import com.pjff.springcloud.msvc.cursos.entity.Curso;

public interface CursoService {
    // Paso 3, vid 14

    List<Curso> listar();

    Optional<Curso> porId(Long id);

    Curso guardar(Curso curso);

    void eliminar(Long id);
}
