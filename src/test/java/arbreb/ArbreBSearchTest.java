
package arbreb;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ArbreBSearchTest {

    @Test
    public void emptyTreeSearchTest() {
        ArbreB arbreb = new ArbreB();

        assertNull(arbreb.recherche("Paris"));
    }

    @Test
    public void basicIntervalSearchTest() {
        ArbreB arbreb = new ArbreB();

        arbreb.ajouter("Lyon", "69");
        arbreb.ajouter("Paris", "75");

        List<String> result =
            arbreb.rechercheIntervalle("Lyon", "Paris");

        assertEquals(2, result.size());
        assertTrue(result.contains("69"));
        assertTrue(result.contains("75"));
    }

    @Test
    public void basicPrefixSearchTest() {
        ArbreB arbreb = new ArbreB();

        arbreb.ajouter("Évry", "91");
        arbreb.ajouter("Paris", "75");

        List<String> expected = List.of("Évry -> 91");

        assertEquals(
            expected,
            arbreb.recherchePrefixe("e")
        );

        assertEquals(
            expected,
            arbreb.recherchePrefixe("E")
        );
    }
}