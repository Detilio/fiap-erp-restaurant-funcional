package br.com.fiap.tech.challenge.erp_restaurant.service;

import br.com.fiap.tech.challenge.erp_restaurant.dto.UserRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.dto.UserResponseDTO;
import br.com.fiap.tech.challenge.erp_restaurant.entity.UserEntity;
import br.com.fiap.tech.challenge.erp_restaurant.mapper.UserMapper;
import br.com.fiap.tech.challenge.erp_restaurant.model.User;
import br.com.fiap.tech.challenge.erp_restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final String SECRET = "Bearer Y29tLmV4YW1wbGUudG9rZW4uZ2VuZXJhdG9yLkdlcmFyVG9rZW5BbGVhdG9yaW8=";
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Autenticação simples (simulada)
    public String authenticate(String login, String password) {
        Optional<UserEntity> userEntityOptional = userRepository.findByLoginAndPasswordIgnoreCase(login, password);
        return userEntityOptional
                .filter(user -> user.getPassword().equals(password))
                .map(user -> SECRET)
                .orElse(null);
    }

    // Valida o token
    private Boolean validarToken(String token) {
        return token != null && token.equalsIgnoreCase(SECRET);
    }

    // Cadastro de novo usuário
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        Optional<UserEntity> existingUser = userRepository.findByLoginIgnoreCase(userRequestDTO.getLogin());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Já existe usuário cadastrado com este login!");
        }

        userRequestDTO.setType(User.UserType.NORMAL); // Ajuste se enum estiver no DTO
        UserEntity userEntity = UserMapper.toEntity(userRequestDTO);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return UserMapper.toResponseDTO(savedUserEntity);
    }

    // Lista todos os usuários
    public List<UserResponseDTO> listUsers(String token) {
        if (!validarToken(token)) {
            throw new IllegalArgumentException("Token inválido!");
        }

        return userRepository.findAll().stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Lista usuário específico
    public List<UserResponseDTO> listUser(Long id, String token) {
        if (!validarToken(token)) {
            throw new IllegalArgumentException("Token inválido!");
        }

        return userRepository.findById(id).stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Atualiza um usuário
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO, String token) {
        if (!validarToken(token)) {
            throw new IllegalArgumentException("Token inválido!");
        }

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado."));

        userEntity.setName(userRequestDTO.getName());
        userEntity.setEmail(userRequestDTO.getEmail());
        userEntity.setLogin(userRequestDTO.getLogin());
        userEntity.setPassword(userRequestDTO.getPassword());
        userEntity.setType(userRequestDTO.getType());
        userEntity.setDateChange(new Date());

        UserEntity updatedUser = userRepository.save(userEntity);
        return UserMapper.toResponseDTO(updatedUser);
    }

    // Exclui um usuário
    public void deleteUser(Long id, String token) {
        if (!validarToken(token)) {
            throw new IllegalArgumentException("Token inválido!");
        }

        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("Usuário não encontrado.");
        }

        userRepository.deleteById(id);
    }
}
