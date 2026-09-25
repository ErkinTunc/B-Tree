package arbreb;

// regression test
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArbreBInsertionTest {

    @Test
    public void simpleInsertionTest() {
        ArbreB arbreb = new ArbreB();

        String cle = "Paris";
        String valeur = "76";

        arbreb.ajouter(cle, valeur);
        assertEquals(valeur, arbreb.recherche(cle));

    }

    @Test
    public void missingKeyTest() {
        ArbreB arbreb = new ArbreB();

        arbreb.ajouter("Paris", "76");

        assertNull(arbreb.recherche("Lyon"));
    }

    @Test
    // Modified for every single M
    public void leafSplitTest() {
        ArbreB arbreb = new ArbreB();

        // Adding M keys and values
        for (int i = 0; i < ArbreB.M; i++) {
            String cle = "Key" + i;
            String valeur = "Value" + i;

            arbreb.ajouter(cle, valeur);
        }

        // Searching and comparing, testing values
        for (int i = 0; i < ArbreB.M; i++) {
            String cle = "Key" + i;
            String valeurAttendue = "Value" + i;

            assertEquals(valeurAttendue, arbreb.recherche(cle));
        }
    }

    @Test
    public void multipleInsertionsTest() {
        ArbreB arbreb = new ArbreB();

        arbreb.ajouter("Lyon", "69");
        arbreb.ajouter("Paris", "75");

        assertEquals("69", arbreb.recherche("Lyon"));
        assertEquals("75", arbreb.recherche("Paris"));
    }

    @Test
    public void unsortedInsertionsTest() {
        ArbreB arbreb = new ArbreB();

        arbreb.ajouter("Paris", "75");
        arbreb.ajouter("Bordeaux", "33");
        arbreb.ajouter("Lyon", "69");
        arbreb.ajouter("Marseille", "13");
        arbreb.ajouter("Amiens", "80");

        assertEquals("75", arbreb.recherche("Paris"));
        assertEquals("33", arbreb.recherche("Bordeaux"));
        assertEquals("69", arbreb.recherche("Lyon"));
        assertEquals("13", arbreb.recherche("Marseille"));
        assertEquals("80", arbreb.recherche("Amiens"));
    }

    @Test
    public void internalNodeSplitTest() {
        ArbreB arbreb = new ArbreB();

        assertEquals(3, ArbreB.M);

        for (int i = 0; i < 5; i++) {
            arbreb.ajouter("Key" + i, "Value" + i);
        }

        for (int i = 0; i < 5; i++) {
            assertEquals(
                    "Value" + i,
                    arbreb.recherche("Key" + i));
        }
    }
}
