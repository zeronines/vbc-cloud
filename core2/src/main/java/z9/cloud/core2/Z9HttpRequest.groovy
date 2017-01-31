package z9.cloud.core2

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.apache.http.HttpRequest
import org.apache.http.entity.ByteArrayEntity
import org.apache.http.message.BasicHeader
import org.apache.http.message.BasicHttpEntityEnclosingRequest
import org.apache.http.message.BasicHttpRequest
import org.apache.http.util.EntityUtils
/**
 * Created by david on 1/10/17.
 */
@EqualsAndHashCode(excludes = ['origin', 'newZid'])
@ToString(includeNames = true, includePackage = false, ignoreNulls = true)
class Z9HttpRequest implements Serializable {
    private static final long serialVersionUID = 1L

    Z9RequestLine requestLine = null

    Z9Header[] headers = []

    String origin
    String newZid

    byte[] content = null



    BasicHttpRequest toBasicHttpRequest() {
        def out
        if (!content)   {
            out = new BasicHttpRequest(requestLine.toBasicRequestLine())
        }
        else {
            out = new BasicHttpEntityEnclosingRequest(requestLine.toBasicRequestLine())
            out.entity = new ByteArrayEntity(content)

        }
        out.headers = headers.collect{it.toBasicHeader()}
        if (newZid) {
            out.addHeader(new BasicHeader('zid', newZid))
        }
        return out
    }

    static Z9HttpRequest toZ9HttpRequest(HttpRequest input) {
        def out = new Z9HttpRequest(requestLine: Z9RequestLine.toZ9RequestLine(input.requestLine))
        out.headers = input.allHeaders.collect {Z9Header.toZ9Header(it)}
        if (input instanceof BasicHttpEntityEnclosingRequest && input.entity) {
            out.content = EntityUtils.toByteArray(input.entity)
        }
        return out
    }
}
