package me.baggi.cloud

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CloudstorageBackendApplication

fun main(args: Array<String>) {
    runApplication<CloudstorageBackendApplication>(*args)
}
