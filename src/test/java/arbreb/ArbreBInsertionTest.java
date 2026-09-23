package arbreb;

// regression test
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArbreBInsertionTest {

    @Test
    void simpleInsertionTest() {
        ArbreB arbreb = new ArbreB();

        String cle = "Paris";
        String valeur = "76";

        arbreb.ajouter(cle, valeur);
        assertEquals(valeur, arbreb.recherche(cle));

    }

    @Test
    void missingKeyTest() {
        ArbreB arbreb = new ArbreB();

        arbreb.ajouter("Paris", "76");

        assertNull(arbreb.recherche("Lyon"));
    }
}
