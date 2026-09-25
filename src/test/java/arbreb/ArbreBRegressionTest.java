
package arbreb;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ArbreBRegressionTest {

    @Test
    public void duplicateKeyReplacementTest() {
        ArbreB arbreb = new ArbreB();

        arbreb.ajouter("Paris", "75");
        arbreb.ajouter("Paris", "75000");

        // Son eklenen değer bulunmalı.
        assertEquals(
            "75000",
            arbreb.recherche("Paris")
        );

        // Aynı anahtar için tek mantıksal kayıt olmalı.
        assertEquals(
            List.of("75000"),
            arbreb.rechercheIntervalle("Paris", "Paris")
        );
    }

    @Test
    public void staleRangeMetadataTest() {
        ArbreB arbreb = new ArbreB();

        // İlk leaf split'i tetikle.
        arbreb.ajouter("B", "2");
        arbreb.ajouter("C", "3");
        arbreb.ajouter("D", "4");

        // Sol leaf'e yeni minimum anahtarı ekle.
        arbreb.ajouter("A", "1");

        // Exact search kaydı bulabilmeli.
        assertEquals(
            "1",
            arbreb.recherche("A")
        );

        // Interval search de aynı kaydı bulabilmeli.
        assertEquals(
            List.of("1"),
            arbreb.rechercheIntervalle("A", "A")
        );
    }
}