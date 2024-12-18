package com.pjff.springcloud.msvc.cursos.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.pjff.springcloud.msvc.cursos.models.Usuario;

//Vid 35,nombre del microservicio que vamos a usar y la ruta en donde esta ubicado
@FeignClient(name = "msvc-usuarios", url = "msvc-usuarios:8001")
public interface UsuarioClientRest {

    // y ponemos la ruta del controlador
    @GetMapping("/{id}")
    // tiene lo mismo @PathVariable Long id
    Usuario detalle(@PathVariable Long id);

    @PostMapping("/")
    Usuario crear(@RequestBody Usuario usuario);

    // Vid 40
    @GetMapping("/usuarios-por-curso")
    List<Usuario> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids);
}
