package vfafpacda24060.web.repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import vfafpacda24060.web.config.CustomProperty;
import vfafpacda24060.web.model.User;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private CustomProperty customProperty;

    public List<User> findAll() {
        // recupération de ma proprietes contenant l'url de l'API
        String baseURL = customProperty.getApiURL();
        // construction de l'url pour appel à l'API
        String getUsersURL = baseURL + "/users";
        // construction de la requete HTTP
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<User>> response = restTemplate.exchange(
                getUsersURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        //log.debug("Get Persons call {}", response.getStatusCode());
        // envoi de la réponse.
        return (List<User>) response.getBody();
    }

    public User findById(Integer idUser) {
        String baseURL = customProperty.getApiURL();
        String getUsersURL = baseURL + "/user/" + idUser;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User> response = restTemplate.exchange(
                getUsersURL,
                HttpMethod.GET,
                null,
                User.class);
        return response.getBody();
    }

    public User createUser(User user) {
        String baseURL = customProperty.getApiURL();
        String getUsersURL = baseURL + "/user";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User> response = restTemplate.exchange(
                getUsersURL,
                HttpMethod.POST,
                null,
                User.class);
        return response.getBody();
    }

    public User updateUser(User user) {
        String baseURL = customProperty.getApiURL();
        String getUsersURL = baseURL + "/user/" + user.getIdUser();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<User> response = restTemplate.exchange(
                getUsersURL,
                HttpMethod.PUT,
                null,
                User.class);
        return response.getBody();
    }

    public void deleteUser(int idUser) {
        String baseApiUrl = customProperty.getApiURL();
        String deletePersonUrl = baseApiUrl + "/deleteuser/" + idUser;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> response = restTemplate.exchange(
                deletePersonUrl,
                HttpMethod.DELETE,
                null,
                Void.class);
    }
}