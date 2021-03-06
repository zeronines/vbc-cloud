package z9.cloud.http

import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import z9.cloud.core2.Input
import z9.cloud.core2.Output


/**
 * Created by dshue1 on 3/15/16.
 */
class RestTemplateTest {
	private RestTemplate restTemplate
	private HttpHeaders headers = new HttpHeaders(contentType: MediaType.APPLICATION_JSON)

	@Before
	void before() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory()
		factory.setReadTimeout(120_000)
		factory.setConnectTimeout(60_000)
		restTemplate = new RestTemplate(factory)
	}

	@Test
	@Ignore
	void testHttp() {
		Input input = new  Input(name: 'david shue', age: 22)

		HttpEntity<Input> entity = new HttpEntity<>(input, headers)

		ResponseEntity<Output> res = restTemplate.exchange('http://localhost:8005/node/v1/test',
			HttpMethod.POST, entity, Output.class)

		println res.statusCode
		println res.body
	}
}
