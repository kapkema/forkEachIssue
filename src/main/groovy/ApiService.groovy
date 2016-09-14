import com.netflix.hystrix.HystrixCommandGroupKey
import com.netflix.hystrix.HystrixCommandKey
import com.netflix.hystrix.HystrixCommandProperties
import com.netflix.hystrix.HystrixObservableCommand
import rx.Observable

class ApiService {
  static HystrixObservableCommand.Setter hystrixSetter = buildSetter("api-service", "ApiService.fetchOriginsWithHystrix")

  static buildSetter(String commandGroupKey, String commandKey) {
    HystrixObservableCommand.Setter
        .withGroupKey(HystrixCommandGroupKey.Factory.asKey(commandGroupKey))
        .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
        .andCommandPropertiesDefaults(HystrixCommandProperties.defaultSetter())
  }

  Observable<Map> fetchOriginsWithHystrix() {
    return new HystrixObservableCommand<Map>(hystrixSetter) {
      @Override
      protected Observable<Map> construct() {
        return fetchOrigins()
      }

      @Override
      protected Observable<Map> resumeWithFallback() {
        return Observable.just([error: "wat!"])
      }
    }.toObservable()
  }

  Observable<Map> fetchOrigins() {
    Observable.from([
        new OriginRequest().get("foo", "bar"),
        new OriginRequest().get("baz", "bam"),
        new OriginRequest().get("hi", "howdy")
    ])
        .forkEach()
        .flatMap { it }
        .reduce([origins:[:]], this.&aggregateResponses)

  }

  protected static Map aggregateResponses(Map aggregate, Map originRequest) {
    aggregate.origins << [(originRequest.name): originRequest]
    return aggregate
  }

}
