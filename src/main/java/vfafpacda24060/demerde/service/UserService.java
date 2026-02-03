package vfafpacda24060.demerde.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vfafpacda24060.demerde.model.User;
import vfafpacda24060.demerde.repository.UserRepository;

@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
}
