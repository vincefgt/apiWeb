package vfafpacda24060.demerde.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vfafpacda24060.demerde.Utility.PasswordUtil;
import vfafpacda24060.demerde.model.User;
import vfafpacda24060.demerde.repository.UserRepository;

@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Iterable<User> findAll() { return userRepository.findAll(); }
    public User findById(Integer id){ return userRepository.findById(id); }
    public void saveUser(User user){
        User userSaved;
        user.setPassword(PasswordUtil.hashPassword(user.getLastname().toUpperCase()));
        if (user.getIdUser() == null){
            userSaved = userRepository.createUser(user);
        } else {
            userSaved = userRepository.updateUser(user);
        }
    }
    public void deleteUser(int id){
        userRepository.deleteUser(id);
    }
}
