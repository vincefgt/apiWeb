package vfafpacda24060.demerde.repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import vfafpacda24060.demerde.config.CustomProperty;
import vfafpacda24060.demerde.model.User;

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
        ResponseEntity<Iterable<User>> response = restTemplate.exchange(getUsersURL, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                }
        );


        //log.debug("Get Persons call {}", response.getStatusCode());
        // envoi de la réponse.
        return (List<User>) response.getBody();
    };
}
