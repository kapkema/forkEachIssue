import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import ratpack.impose.ImpositionsSpec
import ratpack.impose.UserRegistryImposition
import ratpack.registry.Registry

class ApiUnderTest extends GroovyRatpackMainApplicationUnderTest {
  Registry registry

  public ApiUnderTest() {
    this.getAddress()
  }

  @Override
  protected void addImpositions(ImpositionsSpec impositions) {
    impositions.add(UserRegistryImposition.of {
      registry = it
    })
  }
}
