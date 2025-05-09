package com.fittracker.user.hateoas;

import com.fittracker.user.controller.UserController;
import com.fittracker.user.entity.User;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, UserModel> {
    @Override
    public UserModel toModel(User user) {
        UserModel model = new UserModel(
                user.getId(),
                user.getName(),
                user.getEmail()
        );

        model.add(linkTo(methodOn(UserController.class).getById(user.getId())).withSelfRel());
        model.add(linkTo(methodOn(UserController.class).getAll()).withRel("users"));
        model.add(linkTo(methodOn(UserController.class).delete(user.getId())).withRel("delete"));

        return model;
    }

    @Override
    public CollectionModel<UserModel> toCollectionModel(Iterable<? extends User> entities) {
        CollectionModel<UserModel> models = RepresentationModelAssembler.super.toCollectionModel(entities);
        models.add(linkTo(methodOn(UserController.class).getAll()).withSelfRel());
        return models;
    }
}
