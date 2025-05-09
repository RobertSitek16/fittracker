package com.fittracker.user

import com.fittracker.user.entity.User
import com.fittracker.user.exception.UserNotFoundException
import com.fittracker.user.hateoas.UserModel
import com.fittracker.user.hateoas.UserModelAssembler
import com.fittracker.user.repository.UserRepository
import com.fittracker.user.service.UserService
import org.springframework.hateoas.CollectionModel
import spock.lang.Specification

class UserServiceTest extends Specification {

    UserRepository userRepository = Mock(UserRepository)
    UserModelAssembler userModelAssembler = Mock(UserModelAssembler)
    UserService userService = new UserService(userRepository, userModelAssembler)

    def "should return all users from repository"() {
        given:
        def users = [
                new User("robert", "robert@example.com"),
                new User("joanna", "joanna@example.com")
        ]
        def userModels = [
                new UserModel(1L, "robert", "robert@example.com"),
                new UserModel(2L, "joanna", "joanna@example.com")
        ]
        userRepository.findAll() >> users
        userModelAssembler.toCollectionModel(users) >> new CollectionModel<>(userModels)

        when:
        def result = userService.getAll()

        then:
        result.size() == 2
        result.getContent().name.any { it == "robert" }
        result.getContent().name.any { it == "joanna" }
    }

    def "should find user by id"() {
        given:
        def user = new User("bob", "bob@example.com")
        def userId = 1L
        def userModel = new UserModel(userId, "bob", "bob@example.com")
        userRepository.findById(userId) >> Optional.of(user)
        userModelAssembler.toModel(user) >> userModel

        when:
        def result = userService.getById(userId)

        then:
        result != null
        result.name == "bob"
        result.email == "bob@example.com"
    }

    def "should throw exception when user not found by id"() {
        given:
        userRepository.findById(99L) >> Optional.empty()

        when:
        userService.getById(99L)

        then:
        thrown(UserNotFoundException)
    }

    def "should create user with valid input"() {
        given:
        def userName = "john_doe"
        def email = "john@example.com"
        def user = new User(userName, email)
        def userModel = new UserModel(1L, userName, email)

        when:
        def result = userService.createUser(user)

        then:
        1 * userRepository.save(user) >> user
        1 * userModelAssembler.toModel(user) >> userModel
        result != null
        result.name == userName
        result.email == email
    }

    def "should update existing user"() {
        given:
        def existingUser = new User("old_name", "old@example.com")
        def userId = 1L
        existingUser.setId(userId)
        def updatedUser = new User("new_name", "new@example.com")
        def updatedUserModel = new UserModel(userId, "new_name", "new@example.com")
        userRepository.findById(userId) >> Optional.of(existingUser)
        userModelAssembler.toModel(existingUser) >> updatedUserModel

        when:
        def result = userService.updateUser(userId, updatedUser)

        then:
        1 * userRepository.save(_) >> { it[0] }
        result.name == "new_name"
        result.email == "new@example.com"
    }

    def "should throw exception when updating non-existent user"() {
        given:
        def updatedUser = new User("alex", "alex@example.com")
        userRepository.findById(1L) >> Optional.empty()

        when:
        userService.updateUser(1L, updatedUser)

        then:
        thrown(UserNotFoundException)
    }

    def "should delete existing user"() {
        given:
        userRepository.existsById(1L) >> true

        when:
        userService.deleteUser(1L)

        then:
        1 * userRepository.deleteById(1L)
    }

    def "should throw exception when deleting non-existent user"() {
        given:
        userRepository.existsById(99L) >> false

        when:
        userService.deleteUser(99L)

        then:
        thrown(UserNotFoundException)
    }
}
