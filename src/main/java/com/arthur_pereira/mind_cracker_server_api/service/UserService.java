package com.arthur_pereira.mind_cracker_server_api.service;

import com.arthur_pereira.mind_cracker_server_api.data.Email;
import com.arthur_pereira.mind_cracker_server_api.data.GameName;
import com.arthur_pereira.mind_cracker_server_api.data.Password;
import com.arthur_pereira.mind_cracker_server_api.data.Usertag;
import com.arthur_pereira.mind_cracker_server_api.dto.UserCreationDTO;
import com.arthur_pereira.mind_cracker_server_api.exception.DuplicatedResourceException;
import com.arthur_pereira.mind_cracker_server_api.exception.ResourceNotFoundException;
import com.arthur_pereira.mind_cracker_server_api.model.User;
import com.arthur_pereira.mind_cracker_server_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserCreationDTO userCreationDTO) {
        Email email = new Email(userCreationDTO.email());
        Password password = new Password(userCreationDTO.password());
        Usertag usertag = new Usertag(userCreationDTO.usertag());
        boolean uniqueUsertag = false;
        boolean uniqueEmail = false;
        try {
            findByUsertag(usertag);
        } catch (ResourceNotFoundException e) {
            uniqueUsertag = true;
        }
        try {
            findByEmail(email);
        } catch (ResourceNotFoundException e) {
            uniqueEmail = true;
        }
        if(uniqueUsertag) {
            if(uniqueEmail) {
                User user = new User(email, password, usertag);
                return userRepository.save(user);
            }
            throw new DuplicatedResourceException("The given E-Mail is already being used.");
        }
        throw new DuplicatedResourceException("The given Usertag is already being used.");
    }

    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Couldn't find User with the provided id."));
    }

    public User findByUsertag(Usertag usertag) {
        return userRepository.findByUsertag(usertag.getValue()).orElseThrow(() -> new ResourceNotFoundException("Couldn't find User with the provided id."));
    }

    public User findByEmail(Email email) {
        return userRepository.findByEmail(email.getValue()).orElseThrow(() -> new ResourceNotFoundException("Couldn't find User with the provided id."));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByEmail(new Email(username));
    }
}
