package com.example.demo.cache;

import com.example.demo.external.ExternalRestClient;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequestScope
public class PerRequestExternalCache {

  private final ExternalRestClient externalRestClient;
  private final ExecutorService virtualThreadExecutor;
  // a future representing the (possibly in-progress) fetch
  private volatile CompletableFuture<Map<String, Object>> future;

  public PerRequestExternalCache(ExternalRestClient externalRestClient) {
    this.externalRestClient = externalRestClient;
    this.virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();
  }

  /**
   * Returns the external response. The first caller triggers the external call via virtual thread.
   * Concurrent callers will join the same future and wait for the single call to complete.
   */
  public Map<String, Object> get() {
    CompletableFuture<Map<String, Object>> f = future;
    if (f != null) {
      return f.join();
    }

    synchronized (this) {
      if (future == null) {
        future = CompletableFuture.supplyAsync(
            externalRestClient::fetchRemote,
            virtualThreadExecutor
        );
      }
      return future.join();
    }
  }
}
