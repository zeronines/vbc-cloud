package z9.cloud.journal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;
import z9.cloud.core2.Z9HttpRequest;

import java.io.IOException;

@Table("user_event")
public class UserEvent {
    private static ObjectMapper mapper = new ObjectMapper();
    @PrimaryKey
    private UserEventKey pk;

    @Column(value="content")
    private String content;


    public UserEventKey getPk() {
        return pk;
    }

    public void setPk(UserEventKey pk) {
        this.pk = pk;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserEvent(String message) {
        try {
            Z9HttpRequest input = mapper.readValue(message, Z9HttpRequest.class);
            this.pk = new UserEventKey(input);
            this.content = message;
        } catch(IOException e) {
            // do nothing
        }
    }
}
