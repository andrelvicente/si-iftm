package com.andrelvicente.prova01.services;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.andrelvicente.prova01.models.dto.UserDTO;
import com.andrelvicente.prova01.models.User;
import com.andrelvicente.prova01.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Lista todos (sem paginação)
    public List<UserDTO> getAll() {
        List<User> usuarios = userRepository.findAll();
        return usuarios.stream()
                       .map(UserDTO::convert)
                       .collect(Collectors.toList());
    }

    // Busca por ID
    public UserDTO findById(String userId) {
        User usuario = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserDTO.convert(usuario);
    }

    // Salva um novo usuário
    public UserDTO save(UserDTO userDTO) {
        userDTO.setDataCadastro(LocalDateTime.now());
        User user = userRepository.save(User.convert(userDTO));
        return UserDTO.convert(user);
    }

    // Deleta por ID e retorna o registro deletado (como DTO)
    public UserDTO delete(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
        return UserDTO.convert(user);
    }

    // Busca por CPF
    public UserDTO findByCpf(String cpf) {
        User user = userRepository.findByCpf(cpf);
        if (user != null) {
            return UserDTO.convert(user);
        }
        return null;
    }

    // Query por nome (like)
    public List<UserDTO> queryByName(String name) {
        List<User> usuarios = userRepository.queryByNomeLike(name);
        return usuarios.stream()
                       .map(UserDTO::convert)
                       .collect(Collectors.toList());
    }

    // Edição parcial de campos selecionados
    public UserDTO editUser(String userId, UserDTO userDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userDTO.getEmail() != null && !user.getEmail().equals(userDTO.getEmail())) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getTelefone() != null && !user.getTelefone().equals(userDTO.getTelefone())) {
            user.setTelefone(userDTO.getTelefone());
        }
        if (userDTO.getEndereco() != null && !user.getEndereco().equals(userDTO.getEndereco())) {
            user.setEndereco(userDTO.getEndereco());
        }

        user = userRepository.save(user);
        return UserDTO.convert(user);
    }

    // Lista com paginação
    public Page<UserDTO> getAllPage(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(UserDTO::convert);
    }
}
