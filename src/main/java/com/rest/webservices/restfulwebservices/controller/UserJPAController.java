package com.rest.webservices.restfulwebservices.controller;

import com.rest.webservices.restfulwebservices.dao.PostDaoRepository;
import com.rest.webservices.restfulwebservices.dao.UserDaoRepository;
import com.rest.webservices.restfulwebservices.exception.PostNotFoundException;
import com.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.rest.webservices.restfulwebservices.model.Post;
import com.rest.webservices.restfulwebservices.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJPAController {

    @Autowired
    private UserDaoRepository userDaoRepository;
    @Autowired
    private PostDaoRepository postDaoRepository;

    @GetMapping(path = "/jpa/users")
    public List<User> retrieveAllUsers() {
        return userDaoRepository.findAll();
    }

    @GetMapping(path = "/jpa/users/{id}")
    public EntityModel<User> retrieveUserById(@PathVariable int id) {
        Optional<User> user = userDaoRepository.findById(id);

        if (!user.isPresent()) {
            throw new UserNotFoundException("User Not Found! id -> " + id);
        }

        // link for get all users
        EntityModel<User> resource = EntityModel.of(user.get());
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    @PostMapping(path = "/jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User newUser = userDaoRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "/jpa/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userDaoRepository.deleteById(id);
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrieveAllUserPosts(@PathVariable int id) {
        Optional<User> user = userDaoRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User Not Found! id -> " + id);
        }

        return user.get().getPosts();
    }

    @GetMapping("jpa/users/{id}/posts/{postId}")
    public Post retrievePostById(@PathVariable int postId) {
        Optional<Post> post = postDaoRepository.findById(postId);
        if (!post.isPresent()) {
            throw new PostNotFoundException("Post Not Found! id -> " + postId);
        }

        return post.get();
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) {
        Optional<User> userOptional = userDaoRepository.findById(id);

        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User Not Found! id -> " + id);
        }

        User user = userOptional.get();
        post.setUser(user);
        postDaoRepository.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{id}/posts/{postId}")
    public void deletePostById(@PathVariable int postId) {
        postDaoRepository.deleteById(postId);
    }

}
