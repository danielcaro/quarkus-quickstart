package org.acme.rest

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager
import io.smallrye.reactive.messaging.connectors.InMemoryConnector

// https://github.com/quarkusio/quarkus-quickstarts/tree/master/kafka-quickstart
// https://github.com/quarkusio/quarkus/issues/6427
class KafkaResource : QuarkusTestResourceLifecycleManager {

    override fun start(): Map<String, String> {
        val env: MutableMap<String, String> = mutableMapOf<String,String>()
        val props1: Map<String, String> = InMemoryConnector.switchIncomingChannelsToInMemory("prices")
        val props2: Map<String, String> = InMemoryConnector.switchOutgoingChannelsToInMemory("my-data-stream")
        env.putAll(props1)
        env.putAll(props2)
        return env
    }

    override fun stop() {
        InMemoryConnector.clear()
    }
}