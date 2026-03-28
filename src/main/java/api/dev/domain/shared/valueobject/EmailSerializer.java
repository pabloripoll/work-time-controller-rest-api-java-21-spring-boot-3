package api.dev.domain.shared.valueobject;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class EmailSerializer extends JsonSerializer<Email> {
    @Override
    public void serialize(Email email, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(email.value());
    }
}
