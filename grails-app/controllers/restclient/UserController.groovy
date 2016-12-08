package restclient

import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder

class UserController {
    def index() {
        RestBuilder restBuilder = new RestBuilder()
        def restRep = restBuilder.get("http://localhost:8080/users") {
            accept JSON
        }

        List<User> users = new ArrayList<>()
        restRep.json.each {
            jSONUser -> users.add(new User(jSONUser))
        }

        respond users
    }
}
