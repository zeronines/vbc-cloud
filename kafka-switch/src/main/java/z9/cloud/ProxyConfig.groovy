package z9.cloud

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import z9.cloud.core2.HttpRetry
import z9.cloud.http.HttpDelegate
import z9.cloud.http.HttpProxyExecutor
import z9.cloud.http.HttpProxyRequestHandler
/**
 * Created by dshue1 on 3/14/16.
 */

@Configuration
class ProxyConfig {
	@Value('${threadpool.queue.size.core}') private int coreSize
	@Value('${threadpool.queue.size.max}') private int maxSize
	@Value('${threadpool.queue.size.capacity}') private int capacity

	@Bean
	String env(Environment env) {
		env.activeProfiles.length == 0 ? 'default' : env.activeProfiles[0]
	}

	@Bean
	taskExecutor() {
		new ThreadPoolTaskExecutor(
			corePoolSize: coreSize,
			maxPoolSize: maxSize,
			queueCapacity: capacity
		)
	}

	@Bean
	httpDelegate() {
		new HttpDelegate()
	}

	@Bean
	httpHandler() {
		new HttpProxyRequestHandler(httpDelegate())
	}

	@Bean
	httpProxy(@Value('${proxy.http.port}') int port, @Value('${proxy.http.backlog}') int backlog) {
		ProxyExecutor proxy = new HttpProxyExecutor(handler: httpHandler(),
				taskExecutor: taskExecutor(),
				port: port,
				backlog: backlog)
		proxy.startExecutor()
		proxy
	}

	@Bean
	HttpRetry httpRetry() {
		new HttpRetry()
	}
}
