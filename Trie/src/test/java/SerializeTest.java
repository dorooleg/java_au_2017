import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
        Assert.assertFalse(trie.add(""));
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
        Assert.assertEquals(1, trie.size());
        trie.remove("Hello");
        Assert.assertEquals(0, trie.size());
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
        Assert.assertEquals(1, trie.howManyStartsWithPrefix("Hell"));
        Assert.assertEquals(1, trie.size());
        trie.remove("Hello");
        Assert.assertEquals(0, trie.size());
        Assert.assertEquals(0, trie.howManyStartsWithPrefix("Hell"));
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

        Assert.assertEquals(1, trie.size());
        Assert.assertEquals(1, trie.howManyStartsWithPrefix("Hell"));
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

        Assert.assertEquals(0, trie.size());
        Assert.assertEquals(0, trie.howManyStartsWithPrefix("Hell"));
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

        Assert.assertEquals(6, trie.size());
        Assert.assertEquals(2, trie.howManyStartsWithPrefix("Hell"));

        trie.remove("Hel");
        Assert.assertEquals(2, trie.howManyStartsWithPrefix("Hel"));
        Assert.assertFalse(trie.contains("Hel"));

        trie.remove("V");
        Assert.assertEquals(1, trie.howManyStartsWithPrefix("V"));
        Assert.assertFalse(trie.contains("V"));
    }
}
