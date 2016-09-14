import com.google.inject.Inject
import ratpack.groovy.handling.GroovyChainAction
import ratpack.rx.RxRatpack

import static ratpack.jackson.Jackson.json

class ApiResource extends GroovyChainAction{

  @Inject
  ApiService apiService
  @Override
  void execute() throws Exception {
    path('origin') {
      byMethod {
        get {
          RxRatpack.promiseSingle(apiService.fetchOriginsWithHystrix()).then {
            context.response.contentType('application/json;charset=UTF-8')
            context.render json(it)
          }
        }
      }
    }
  }
}
