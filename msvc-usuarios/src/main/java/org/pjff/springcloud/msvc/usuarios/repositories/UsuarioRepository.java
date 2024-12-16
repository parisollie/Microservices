package org.pjff.springcloud.msvc.usuarios.repositories;

import org.pjff.springcloud.msvc.usuarios.models.entity.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

//Vid 12,paso 2, importamos el  CrudRepository nuestra clase usuario.
//Le pasamos Long que es el tipo de dato del id
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    // Vid 29,devuelve un Optional del tipo usuario,buscar el campo email
    Optional<Usuario> findByEmail(String email);

    // Vid 30, se hace una simplifacion mejor, IMPORTANTe
    @Query("select u from Usuario u where u.email=?1")
    Optional<Usuario> porEmail(String email);

    boolean existsByEmail(String email);
}
