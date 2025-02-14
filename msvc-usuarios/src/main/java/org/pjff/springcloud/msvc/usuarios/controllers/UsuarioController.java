package org.pjff.springcloud.msvc.usuarios.controllers;

import org.apache.catalina.core.ApplicationContext;
import org.pjff.springcloud.msvc.usuarios.models.entity.Usuario;
import org.pjff.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import jakarta.validation.*;
//import jakarta.validation.constraints.*;

//V-14,paso 15, le ponemos RestController que es para trabajar con API-REST con POST-man
@RestController
public class UsuarioController {

    // Paso 16,Necesitamos interactuar con el service ,asi que la inyectamos con la
    // interfaz no con IMPIL, SIEMPRE con la interfaz .
    @Autowired
    private UsuarioService service;

    /*
     * Paso 17, Definimos los metódos
     * 
     * @GetMapping
     * // devuelve una lista de usuarios
     * public List<Usuario> listar() {
     * // devolvemos un json que es una lista , esto se convierte en un json por el
     * // RESTCONTROLLER
     * return service.listar();
     * }
     */

    // Vid 136
    @Autowired
    private ApplicationContext context;

    @GetMapping("/crash")
    public void crash() {
        // Hacemos un cast
        ((ConfigurableApplicationContext) context).close();
    }

    // Vid 57
    @GetMapping
    public Map<String, List<Usuario>> listar() {
        return Collections.singletonMap("users", service.listar());
    }

    // Paso 18,La ruta es cuando venga el id, es una variable y se expresa así {id}
    @GetMapping("/{id}")
    // inyeccion de dependecia, @PathVariable Long i, para variables de la ruta
    // Response entitity ? lo dejamos en forma generica para que nos devuelva un
    // tipo void
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        // service.porId(id), devuelve un opcional
        Optional<Usuario> usuarioOptional = service.porId(id);
        // si esta presente
        if (usuarioOptional.isPresent()) {
            // devolvemos el objeto tipo 200
            return ResponseEntity.ok(usuarioOptional.get());
        }
        // sino devolvemos una respuesta no econtrado 404,no existes y evitamos el null
        return ResponseEntity.notFound().build();
    }

    // V-15, paso 19, guarda con el post
    @PostMapping
    // @ResponseStatus(HttpStatus.CREATED)
    /*
     * devuelve el usuario creado, el contenido que este en el request ,lo
     * inyectamos acá y se convierte en el objeto usuario.
     * Agregamos validacion @Valid
     * BindingResult result mensaje de las validaciones
     */
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result) {

        // V-28, si tiene errores
        if (result.hasErrors()) {
            // regresamos la respuesta con el JSON
            return validar(result);
        }

        // V-30,!usuario.getEmail().isEmpty(),si no esta vacio
        if (!usuario.getEmail().isEmpty() && service.existePorEmail(usuario.getEmail())) {
            // Vid 29
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("mensaje", "Ya existe un usuario con ese correo electronico!"));
        }

        // Paso 20,le mandamos el 201, que es created
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario));
    }

    // Paso 21, Para editar el id es variable, mandamos el objeto usuario y el valor
    // que es el id
    @PutMapping("/{id}")
    /*
     * El orden (@Valid @RequestBody Usuario usuario, BindingResult result,
     * 
     * @PathVariable Long id) es importante
     */
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {

        // Vid 28 , copiamos la misma validacion que la de guardar.
        if (result.hasErrors()) {
            // Reutilizamos el metódo validar.
            return validar(result);
        }
        // Lo buscamos en la base de datos por el id
        Optional<Usuario> o = service.porId(id);
        // Paso 22, si esta presente
        if (o.isPresent()) {
            // Modificamos el usuario en la base de datos con get
            Usuario usuarioDb = o.get();
            /*
             * Vid 29, el correo debe ser distinto y que no exista en la bd,
             * equalsIgnoreCase ignorar miniscula sy mayusculas
             * Vid 30 ,!usuario.getEmail().isEmpty()
             */
            if (!usuario.getEmail().isEmpty() &&
                    !usuario.getEmail().equalsIgnoreCase(usuarioDb.getEmail()) &&
                    service.porEmail(usuario.getEmail()).isPresent()) {
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("mensaje", "Ya existe un usuario con ese correo electronico!"));
            }
            // Paso 23 , Modificamos el usuario
            usuarioDb.setNombre(usuario.getNombre());
            usuarioDb.setEmail(usuario.getEmail());
            usuarioDb.setPassword(usuario.getPassword());
            // Paso 24,Lo Guardamos en la base de datos
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuarioDb));
        }
        return ResponseEntity.notFound().build();
    }

    // V-16 Paso 25,le pasamos el id
    @DeleteMapping("/{id}")
    // No content m y solo le pasamoos el path
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        // Le pasamos por el id , por si existe
        Optional<Usuario> o = service.porId(id);
        // para validar si existe el id
        if (o.isPresent()) {
            service.eliminar(id);
            // devolvemos la respuesta que es un 204
            return ResponseEntity.noContent().build();
        }
        // sino existe el id en la base de datos
        return ResponseEntity.notFound().build();
    }

    // V-39
    @GetMapping("/usuarios-por-curso")
    public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.listarPorIds(ids));
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        // Vid 28,Se lo mandamos con tipo Map
        Map<String, String> errores = new HashMap<>();
        // Iteramos con un foreach para buscar el error
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
