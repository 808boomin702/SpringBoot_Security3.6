package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    DirectorRepository directorRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/login?logout=true";
    }

    @RequestMapping("/admin")
    public String admin () {
        return "admin";
    }

    @RequestMapping("/secure")
    public String secure(Principal principal, Model model){
        String username = principal.getName();
        model.addAttribute("user", userRepository.findByUsername(username));
        return "secure";
    }
    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @RequestMapping("/")
    public String index(Model model) {
        // First lets create a director
        Director director = new Director ();

        director.setName("Stephen Bullock");
        director.setGenre("Sci Fi");

        // Now lets create a movie
        Movie movie = new Movie();
        movie.setTitle("Star Movie");
        movie.setYear(2017);
        movie.setDescription("About Stars...");

        // add the movie to an empty list
        Set<Movie> movies = new HashSet<Movie>();
        movies.add(movie);

        movie = new Movie();
        movie.setTitle("DeathStar Eworks");
        movie.setYear(2011);
        movie.setDescription("About Eworks on the DeathStar...");
        movies.add(movie);

        // Add the list of movies to the director's movie list
        director.setMovies(movies);

        //save the director to the database
        directorRepository.save(director);

        //Grab all the directors from the database and send them to
        // the template
        model.addAttribute("directors", directorRepository.findAll());
        return "index";
    }
}
