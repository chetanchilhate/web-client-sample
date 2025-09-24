package com.reactive.ecom.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatThreadTuningConfig {

  private static final long THREAD_STACK_SIZE = 1024 * 1024; // 1 MB
  private static final double HEAP_RATIO = 0.75; // assume 75% memory for heap
  private static final int IO_OVERSUBSCRIPTION_FACTOR = 75; // threads/core

  private int safeThreadCount;

  @PostConstruct
  public void calculateSafeThreads() {
    int cores = Runtime.getRuntime().availableProcessors();
    long maxMemory = Runtime.getRuntime().maxMemory();
    System.out.println("Max JVM Memory (MB): " + maxMemory/(1024*1024) + " MB");

    long memForThreads = (long) (maxMemory * (1 - HEAP_RATIO));

    long maxThreadsByMemory = memForThreads / THREAD_STACK_SIZE;
    long maxThreadsByCpu = (long) cores * IO_OVERSUBSCRIPTION_FACTOR;

    safeThreadCount = (int) Math.min(maxThreadsByMemory, maxThreadsByCpu);


    System.out.printf("[TomcatThreadTuning] %d cores, %.2f GB RAM -> Safe maxThreads=%d (CPU=%d, Memory=%d)%n",
        cores,
        maxMemory / (1024.0 * 1024 * 1024),
        safeThreadCount,
        maxThreadsByCpu,
        maxThreadsByMemory);
  }

}
