package com.example.demo.cache;

import com.example.demo.external.ExternalRestClient;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
@RequestScope
public class PerRequestExternalCache {

  private final ExternalRestClient externalRestClient;
  // a future representing the (possibly in-progress) fetch
  private volatile CompletableFuture<Map<String, Object>> future;

  public PerRequestExternalCache(ExternalRestClient externalRestClient) {
    this.externalRestClient = externalRestClient;
  }

  /**
   * Returns the external response. The first caller triggers the external call.
   * Concurrent callers will join the same future and wait for the single call to complete.
   */
  public Map<String, Object> get() {
    CompletableFuture<Map<String, Object>> f = future;
    if (f != null) {
      return f.join();
    }

    synchronized (this) {
      if (future == null) {
        CompletableFuture<Map<String, Object>> newFuture = new CompletableFuture<>();
        future = newFuture;
        try {
          Map<String, Object> result = externalRestClient.fetchRemote();
          newFuture.complete(result);
        } catch (Throwable t) {
          newFuture.completeExceptionally(t);
          throw t;
        }
      }
      return future.join();
    }
  }
}
