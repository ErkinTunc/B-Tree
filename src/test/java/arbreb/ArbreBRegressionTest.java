
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
                arbreb.recherche("Paris"));

        // Aynı anahtar için tek mantıksal kayıt olmalı.
        assertEquals(
                List.of("75000"),
                arbreb.rechercheIntervalle("Paris", "Paris"));
    }

    @Test
    public void staleRangeMetadataTest() {
        ArbreB arbreb = new ArbreB();

        arbreb.ajouter("B", "2");
        arbreb.ajouter("C", "3");
        arbreb.ajouter("D", "4");
        arbreb.ajouter("E", "5");

        // Parent metadata güncellendikten sonra,
        // child split olmadan yeni minimum ekleniyor.
        arbreb.ajouter("A", "1");

        assertEquals("1", arbreb.recherche("A"));

        assertEquals(
                List.of("1"),
                arbreb.rechercheIntervalle("A", "A"));
    }
}