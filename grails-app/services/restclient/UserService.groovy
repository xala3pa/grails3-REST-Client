package restclient

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty
import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder

class UserService {

    @HystrixCommand(fallbackMethod = "getStaticList", commandProperties = [
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),
        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")])
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

    def getStaticList() {
        List<User> users = new ArrayList<>()
        users.add(new User(id: 11, name: "Ironman"))
        users.add(new User(id: 12, name: "Hulk"))
        users.add(new User(id: 13, name: "Captain America"))

        return users
    }
}
