import com.fasterxml.jackson.databind.ObjectMapper
import ratpack.registry.Registry
import ratpack.test.http.TestHttpClient
import rx.plugins.RxJavaPlugins
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class ApiFunctionalSpec extends Specification {
  @Shared
  ApiUnderTest aut

  @Shared
  Registry registry

  @Shared
  TestHttpClient client
  @Shared
  ObjectMapper mapper

  def setup() {
    aut = new ApiUnderTest()
    registry = aut.registry
    client = aut.httpClient
    mapper = new ObjectMapper()
  }

  def cleanup() {
    aut.close()
    registry = null
    aut = null
  }

  @Unroll
  def "Result count is equal to number forks - #description"() {
    when:
    def response = client.get(path)

    then:
    def result = mapper.readValue(response.body.bytes, Map)
    result.origins.keySet().size() == expected

    where:
    description   | path                | expected
    "first call"  | "/forkEach/origin" | 3
    "second call" | "/forkEach/origin" | 3
  }
}
