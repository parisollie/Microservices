package org.pjff.springcloud.msvc.usuarios.services;

import java.util.List;
import java.util.Optional;

import org.pjff.springcloud.msvc.usuarios.models.entity.Usuario;

public interface UsuarioService {
    // Vid 13, paso 3, devuelve una lista de tipo usuario
    List<Usuario> listar();

    // Devuelve que el objeto este en la consulta,para que no devuelta un null
    // pointer exception
    Optional<Usuario> porId(Long id);

    // Devuleve el usuario y pasamos un objeto usuario
    Usuario guardar(Usuario usuario);

    // Eliminamos por Id
    void eliminar(Long id);

    // Vid 29, agregamos un nuevo
    Optional<Usuario> porEmail(String email);

    boolean existePorEmail(String email);
}
