package com.pjff.springcloud.msvc.cursos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pjff.springcloud.msvc.cursos.entity.Curso;
import com.pjff.springcloud.msvc.cursos.repositories.CursoRepository;

import java.util.List;
import java.util.Optional;

//Paso 4, Vid 23 ,implementamos todos los metodos con service que es la semantica de un componente
@Service
public class CursoServiceImpl implements CursoService {

    // Inyectamos el repositprio
    @Autowired
    private CursoRepository repository;

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
}
