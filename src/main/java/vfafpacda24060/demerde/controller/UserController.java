package vfafpacda24060.demerde.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vfafpacda24060.demerde.model.User;
import vfafpacda24060.demerde.service.UserService;

import java.sql.SQLException;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value={"/", "/home"})
    public String home(Model model) {
        Iterable<User> listUsers = userService.findAll();
        model.addAttribute("listUsers", listUsers);
        return "home";
    }

    @GetMapping(value="/user/{id}")
    public String user(@PathVariable("id") final Integer idUser, Model model) {
        User userFound = userService.findById(idUser);
        model.addAttribute("user", userFound);
        return "user";
    }

    @PostMapping(value="/user")
    public String createUser(Model model) {
        User newUser = new User();
        model.addAttribute("user", newUser);
        return "user";
    }

    @PostMapping(value="/saveuser/{id}")
    public ModelAndView saveUser(@PathVariable("id") final Integer idUser,
                              @ModelAttribute("user") User user) {
        // Récupérer les paramètres du formulaire
        String firstname =user.getFirstname();
        String lastname = user.getLastname();
        String phone = user.getPhone();
        String email = user.getEmail();
        String password = user.getPassword();
       // String confirmPassword = user.getComfirmPassword();
        // Validation
        StringBuilder errors = new StringBuilder();
        if (firstname == null || firstname.trim().isEmpty()) {
            errors.append("Le prénom est obligatoire. ");}
        if (lastname == null || lastname.trim().isEmpty()) {
            errors.append("Le nom est obligatoire. ");}
        if (phone == null || !phone.matches("\\d{10}")) {
            errors.append("Le téléphone doit contenir exactement 10 chiffres. ");}
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.append("L'email n'est pas valide. ");}
        if (password == null || password.length() < 8) {
            errors.append("Le mot de passe doit contenir au moins 8 caractères. ");}
       // if (!password.equals(confirmPassword)) {
         //   errors.append("Les mots de passe ne correspondent pas. ");}
       /* // Vérifier les doublons
        try {
            if (userService.emailExists(email)) {
                errors.append("Cet email est déjà utilisé. ");
            }
            if (userService.phoneExists(phone)) {
                errors.append("Ce numéro de téléphone est déjà utilisé. ");
            }
        } catch (SQLException e) {
            errors.append("Erreur lors de la vérification des doublons. ");
            e.printStackTrace();
        }

        // Si erreurs, retourner au formulaire
        if (errors.length() > 0) {
            request.setAttribute("error", errors.toString());
            request.setAttribute("firstname", firstname);
            request.setAttribute("lastname", lastname);
            request.setAttribute("phone", phone);
            request.setAttribute("email", email);
            request.setAttribute("idAddress", idAddressStr);
            request.setAttribute("idRole", idRoleStr);
            request.setAttribute("idAdmin", idAdminStr);
            request.getRequestDispatcher("/WEB-INF/jsp/signup_users.jsp").forward(request, response);
            return;
        }*/

        userService.saveUser(user);
        return new ModelAndView ("redirect:/");
    }

    @DeleteMapping(value="/deleteuser/{id}")
    public String deleteUser(@PathVariable("id") final Integer idUser) {
        userService.deleteUser(idUser);
        return  "redirect:/home";
    }
}
