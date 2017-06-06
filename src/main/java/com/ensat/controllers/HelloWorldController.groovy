import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Scope
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.RequestContextHolder

@RestController
@Scope("session")
class HelloWorldController implements Serializable {
    @Value('${app.version}')
    String version;
    Integer count = 0;

    @RequestMapping(value="/helloworld", method=RequestMethod.GET, produces="application/json")
    def helloworld() {
        HelloWorldResponse response = new HelloWorldResponse(
                hostname: hostname(),
                sessionId: RequestContextHolder.currentRequestAttributes().getSessionId(),
                count: ++count,
                version: version
                );

        return response;
    }

    @RequestMapping("/version")
    def version() {
        return version
    }

    def hostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (all) {
            return "unknown";
        }
    }
}

class HelloWorldResponse {
    String hostname;
    String sessionId;
    int count;
    String version;
}