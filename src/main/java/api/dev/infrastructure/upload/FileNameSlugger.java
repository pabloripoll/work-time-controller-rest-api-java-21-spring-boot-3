package api.dev.infrastructure.upload;

import java.text.Normalizer;
import java.util.UUID;

public class FileNameSlugger {

    public static String slug(String originalFilename) {
        if (originalFilename == null || originalFilename.isBlank()) {
            return UUID.randomUUID().toString();
        }

        int dotIndex = originalFilename.lastIndexOf('.');
        String name = dotIndex > 0 ? originalFilename.substring(0, dotIndex) : originalFilename;
        String ext  = dotIndex > 0 ? originalFilename.substring(dotIndex).toLowerCase() : "";

        String slugged = Normalizer.normalize(name, Normalizer.Form.NFD)
            .replaceAll("[^\\p{ASCII}]", "")   // remove accents
            .toLowerCase()
            .trim()
            .replaceAll("[^a-z0-9]+", "-")     // non-alphanumeric → dash
            .replaceAll("^-|-$", "");          // trim leading/trailing dashes

        // e.g. "my photo.JPG" → "my-photo-a1b2c3d4-...-uuid.jpg"
        return slugged + "-" + UUID.randomUUID() + ext;
    }
}
