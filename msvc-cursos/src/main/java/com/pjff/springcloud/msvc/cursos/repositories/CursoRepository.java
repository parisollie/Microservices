package com.pjff.springcloud.msvc.cursos.repositories;

import org.springframework.data.repository.CrudRepository;

import com.pjff.springcloud.msvc.cursos.entity.Curso;

//PASO 2 ,VID 14,crud
public interface CursoRepository extends CrudRepository<Curso, Long> {
}
