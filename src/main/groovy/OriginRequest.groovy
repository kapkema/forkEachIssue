import rx.Observable

class OriginRequest {
  public rx.Observable<Map> get(String name, String value) {
    return Observable.just([name: name, value: value])
  }
}
