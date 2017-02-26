/**
 * Created by user on 23.02.2017.
 */

import junit.framework.Assert;
import org.junit.Test;

import static junit.framework.Assert.*;

public class TrieTest {
    @Test
    public void typicalTest1()
    {
        Trie trie = new Trie();
        trie.add("Hello");
        Assert.assertTrue(trie.contains("Hello"));
        Assert.assertFalse(trie.contains("Hell"));
        Assert.assertEquals(trie.size(), 1);
        trie.remove("Hello");
        Assert.assertEquals(trie.size(), 0);
        Assert.assertFalse(trie.contains("Hello"));
    }

    @Test
    public void typicalTest2()
    {
        Trie trie = new Trie();
        trie.add("Hello");
        Assert.assertTrue(trie.contains("Hello"));
        Assert.assertEquals(trie.howManyStartsWithPrefix("Hell"), 1);
        Assert.assertEquals(trie.size(), 1);
        trie.remove("Hello");
        Assert.assertEquals(trie.size(), 0);
        Assert.assertEquals(trie.howManyStartsWithPrefix("Hell"), 0);
    }

    @Test
    public void twoAdded() {
        Trie trie = new Trie();
        trie.add("Hello");
        trie.add("Hello");
        Assert.assertEquals(trie.size(), 1);
        Assert.assertEquals(trie.howManyStartsWithPrefix("Hell"), 1);
    }

    @Test
    public void twoRemove() {
        Trie trie = new Trie();
        trie.add("Hello");
        trie.remove("Hello");
        trie.remove("Hello");
        Assert.assertEquals(trie.size(), 0);
        Assert.assertEquals(trie.howManyStartsWithPrefix("Hell"), 0);
    }

    @Test
    public void bigDataTest() {
        Trie trie = new Trie();
        trie.add("Hello");
        trie.add("Hella");
        trie.add("Hel");
        trie.add("H");
        trie.add("Vas");
        trie.add("V");
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
