package com.pjff.springcloud.msvc.cursos.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.pjff.springcloud.msvc.cursos.models.entity.Curso;

//PASO 2 ,VID 14,crud
public interface CursoRepository extends CrudRepository<Curso, Long> {
    // Vid 41, cada vez que se usa ,modifica y elimina query usamos odifying
    @Modifying
    @Query("delete from CursoUsuario cu where cu.usuarioId=?1")
    void eliminarCursoUsuarioPorId(Long id);
}
