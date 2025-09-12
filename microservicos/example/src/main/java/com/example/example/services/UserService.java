
package com.example.example.services;

import com.example.example.dtos.UserDTO;
import com.example.example.models.User;
import com.example.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    // LISTAR TODOS
    public ResponseEntity<List<UserDTO>> findAll() {
        var dbusers = repository.findAll();
        if (dbusers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var userDTOs = dbusers.stream().map(u -> {
            var userDTO = new UserDTO();
            userDTO.setId(u.getId());
            userDTO.setName(u.getName());
            userDTO.setAge(u.getAge());
            return userDTO;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(userDTOs);
    }

    // BUSCAR POR ID
    public ResponseEntity<UserDTO> findById(Object id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        var dbuser = repository.findById(id);
        if (dbuser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var u = dbuser.get();
        var dto = new UserDTO();
        dto.setId(u.getId());
        dto.setName(u.getName());
        dto.setAge(u.getAge());
        return ResponseEntity.ok(dto);
    }

    // SALVAR
    public ResponseEntity<UserDTO> save(User user) {
        // validar usuário
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        if (user.getName() == null || user.getAge() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        user.setId(null); // garante inclusão

        var saved = repository.save(user);

        var dto = new UserDTO();
        dto.setId(saved.getId());
        dto.setName(saved.getName());
        dto.setAge(saved.getAge());
        return ResponseEntity.ok(dto);
    }

    // ATUALIZAR
    public ResponseEntity<UserDTO> update(User user) {
        // validar
        if (user == null || user.getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        var dbuser = repository.findById(user.getId());
        if (dbuser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var entity = dbuser.get();
        // alterar
        entity.setName(user.getName());
        entity.setAge(user.getAge());

        var updated = repository.save(entity);

        var dto = new UserDTO();
        dto.setId(updated.getId());
        dto.setName(updated.getName());
        dto.setAge(updated.getAge());
        return ResponseEntity.ok(dto);
    }

    // DELETAR
    public ResponseEntity<?> delete(Object id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        repository.deleteById(id);

        var dbUser = repository.findById(id);
        if (dbUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
        return ResponseEntity.ok().build();
    }
}

