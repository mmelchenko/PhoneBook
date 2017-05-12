package phone_book_tests.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.Charset;

public class TestUtil {

    public static final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
           MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    public static byte[] convertToJsonBytes(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        try {
            return mapper.writeValueAsBytes(object);
        } catch (IOException e) {
            System.err.println("Exception during conversion into json.");
            e.printStackTrace();
        }

        return null;
    }
}
