import com.google.inject.AbstractModule
import com.google.inject.Scopes
import com.google.inject.Singleton

class ApiModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(ApiResource).in(Scopes.SINGLETON)
  }

  @Singleton
  ApiService apiService() {
    return new ApiService()
  }
}
