import ratpack.rx.RxRatpack

import static ratpack.groovy.Groovy.ratpack

ratpack {
  bindings {
    RxRatpack.initialize()
    module ApiModule
  }

  handlers {
    prefix('forkEach') {
      all chain(registry.get(ApiResource))
    }
  }
}
