package restclient

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder
import grails.transaction.Transactional

@Transactional
class UserService {

    @HystrixCommand(fallbackMethod = "reliable")
    def userList() {
        RestBuilder restBuilder = new RestBuilder()
        def restRep = restBuilder.get("http://localhost:8080/users") {
            accept JSON
        }

        List<User> users = new ArrayList<>()
        restRep.json.each {
            jSONUser -> users.add(new User(jSONUser))
        }

        return users
    }

    def reliable() {
        List<User> users = new ArrayList<>()
        users.add(new User(id: 11, name: "Ironman"))
        users.add(new User(id: 12, name: "Hulk"))
        users.add(new User(id: 13, name: "Captain America"))

        return users
    }
}
