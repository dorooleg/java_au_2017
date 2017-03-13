import junit.framework.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

import static junit.framework.Assert.assertFalse;

/**
 * Created by user on 13.03.2017.
 */
public class SerializeTest {

    @Test
    public void testAbc() throws IOException {
        Trie trie = new Trie();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        trie.serialize(out);
        ByteArrayInputStream is = new ByteArrayInputStream(out.toByteArray());
        trie = new Trie();
        trie.deserialize(is);

        assertFalse(trie.add(""));
    }


    @Test
    public void typicalTest1() throws IOException {
        Trie trie = new Trie();
        trie.add("Hello");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        trie.serialize(out);
        ByteArrayInputStream is = new ByteArrayInputStream(out.toByteArray());
        trie = new Trie();
        trie.deserialize(is);

        Assert.assertTrue(trie.contains("Hello"));
        Assert.assertFalse(trie.contains("Hell"));
        Assert.assertEquals(trie.size(), 1);
        trie.remove("Hello");
        Assert.assertEquals(trie.size(), 0);
        Assert.assertFalse(trie.contains("Hello"));
    }

    @Test
    public void typicalTest2() throws IOException {
        Trie trie = new Trie();
        trie.add("Hello");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        trie.serialize(out);
        ByteArrayInputStream is = new ByteArrayInputStream(out.toByteArray());
        trie = new Trie();
        trie.deserialize(is);

        Assert.assertTrue(trie.contains("Hello"));
        Assert.assertEquals(trie.howManyStartsWithPrefix("Hell"), 1);
        Assert.assertEquals(trie.size(), 1);
        trie.remove("Hello");
        Assert.assertEquals(trie.size(), 0);
        Assert.assertEquals(trie.howManyStartsWithPrefix("Hell"), 0);
    }

    @Test
    public void twoAdded() throws IOException {
        Trie trie = new Trie();
        trie.add("Hello");
        trie.add("Hello");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        trie.serialize(out);
        ByteArrayInputStream is = new ByteArrayInputStream(out.toByteArray());
        trie = new Trie();
        trie.deserialize(is);

        Assert.assertEquals(trie.size(), 1);
        Assert.assertEquals(trie.howManyStartsWithPrefix("Hell"), 1);
    }

    @Test
    public void twoRemove() throws IOException {
        Trie trie = new Trie();
        trie.add("Hello");
        trie.remove("Hello");
        trie.remove("Hello");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        trie.serialize(out);
        ByteArrayInputStream is = new ByteArrayInputStream(out.toByteArray());
        trie = new Trie();
        trie.deserialize(is);

        Assert.assertEquals(trie.size(), 0);
        Assert.assertEquals(trie.howManyStartsWithPrefix("Hell"), 0);
    }

    @Test
    public void bigDataTest() throws IOException {
        Trie trie = new Trie();
        trie.add("Hello");
        trie.add("Hella");
        trie.add("Hel");
        trie.add("H");
        trie.add("Vas");
        trie.add("V");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        trie.serialize(out);
        ByteArrayInputStream is = new ByteArrayInputStream(out.toByteArray());
        trie = new Trie();
        trie.deserialize(is);

        Assert.assertTrue(trie.contains("Hello"));
        Assert.assertTrue(trie.contains("Hella"));
        Assert.assertTrue(trie.contains("Hel"));
        Assert.assertTrue(trie.contains("H"));
        Assert.assertTrue(trie.contains("Vas"));
        Assert.assertTrue(trie.contains("V"));

        Assert.assertFalse(trie.contains("Hellow"));
        Assert.assertFalse(trie.contains("Va"));
        Assert.assertFalse(trie.contains(""));
        Assert.assertFalse(trie.contains("Ha"));
        Assert.assertFalse(trie.contains("Tha"));

        Assert.assertEquals(trie.size(), 6);
        Assert.assertEquals(trie.howManyStartsWithPrefix("Hell"), 2);

        trie.remove("Hel");
        Assert.assertEquals(trie.howManyStartsWithPrefix("Hel"), 2);
        Assert.assertFalse(trie.contains("Hel"));

        trie.remove("V");
        Assert.assertEquals(trie.howManyStartsWithPrefix("V"), 1);
        Assert.assertFalse(trie.contains("V"));
    }
}
