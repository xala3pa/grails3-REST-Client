package restclient

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker

@EnableCircuitBreaker
class UserController {
    UserService userService

    def index() {
        respond userService.userList()
    }
}
