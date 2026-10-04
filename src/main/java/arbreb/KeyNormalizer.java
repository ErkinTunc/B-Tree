package arbreb;

import java.text.Normalizer;
import java.util.Locale;

final class KeyNormalizer {

    private KeyNormalizer() {
        // Utility class
    }

    /**
     * Normalise une chaîne en la convertissant en minuscules et en supprimant
     * les accents.
     *
     * @param s la chaîne à normaliser
     * @return la chaîne normalisée
     */
    static String normalize(String s) {
        String lower = s.toLowerCase(Locale.ROOT);

        return Normalizer.normalize(lower, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", ""); // remove accents
    }
}