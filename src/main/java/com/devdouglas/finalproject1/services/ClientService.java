package com.devdouglas.finalproject1.services;

import com.devdouglas.finalproject1.dto.ClientDTO;
import com.devdouglas.finalproject1.entities.Client;
import com.devdouglas.finalproject1.repositories.ClientRepository;
import com.devdouglas.finalproject1.services.exceptions.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
        Page<Client> clients =  repository.findAll(pageRequest);

        return clients.map(ClientDTO::build);
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        Optional<Client> client = repository.findById(id);
        Client entity = client.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return ClientDTO.build(entity);
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto) {
        Client client = Client.builder()
                .name(dto.getName())
                .cpf(dto.getCpf())
                .income(dto.getIncome())
                .birthDate(dto.getBirthDate())
                .children(dto.getChildren())
                .build();

        client = repository.save(client);
        return ClientDTO.build(client);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            Client entity = repository.getReferenceById(id);
            entity.setName(dto.getName());
            entity.setCpf(dto.getCpf());
            entity.setIncome(dto.getIncome());
            entity.setBirthDate(dto.getBirthDate());
            entity.setChildren(dto.getChildren());

            entity = repository.save(entity);
            return ClientDTO.build(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found "+id);
        }
    }
}
