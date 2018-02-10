package resilient.service

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong


class RequestInterceptor {
    final int MAX_REQUEST = 3
    final int MAX_TIME_SECONDS = 5
    AtomicInteger numberOfRequest = new AtomicInteger(0)
    AtomicLong lastRequestTime = new AtomicLong(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC))
    RequestInterceptor(){
        matchAll()
    }

    boolean before() {
        def now = LocalDateTime.now()
        def elapsedTime = now.toEpochSecond(ZoneOffset.UTC) - lastRequestTime
        System.out.println("Time Elapsed since last request: " + elapsedTime + "s")
        if(elapsedTime < MAX_TIME_SECONDS){
            numberOfRequest.getAndIncrement()
            System.out.println("number of requests with " + MAX_TIME_SECONDS+ "s:" + numberOfRequest)
            if(numberOfRequest.get() > MAX_REQUEST){
                System.out.println("Exceeded Bandwidth, blocking Request!")
                Thread.sleep(10000)
                numberOfRequest.set(0)
            }
        } else if(elapsedTime > MAX_TIME_SECONDS){
            numberOfRequest.set(0)
        }
        true
    }

    boolean after() {
        def now = LocalDateTime.now()
        lastRequestTime.set(now.toEpochSecond(ZoneOffset.UTC))
        true
    }

    void afterView() {
        // no-op
    }
}
