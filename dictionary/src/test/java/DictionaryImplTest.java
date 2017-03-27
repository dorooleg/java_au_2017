import org.junit.Test;

import java.math.BigInteger;
import java.security.SecureRandom;

import static org.junit.Assert.*;

/**
 * Created by user on 09.03.2017.
 */
public class DictionaryImplTest {

    @Test
    public void testEmpty() {
        DictionaryImpl dictionary = new DictionaryImpl();
        assertNull(dictionary.get("Apple"));
        assertEquals(0, dictionary.size());
        dictionary.clear();
        assertNull(dictionary.remove("Apple"));
        assertFalse(dictionary.contains("Apple"));
    }

    @Test
    public void testOneElement() {
        DictionaryImpl dictionary = new DictionaryImpl();
        dictionary.put("Apple", "200 USD");
        assertEquals(1, dictionary.size());
        assertTrue(dictionary.contains("Apple"));
        assertEquals("200 USD", dictionary.get("Apple"));
        assertEquals("200 USD", dictionary.remove("Apple"));
        assertEquals(0, dictionary.size());
        assertNull(dictionary.remove("Apple"));

        dictionary.put("Apple", "200 USD");
        assertEquals(1, dictionary.size());
        assertTrue(dictionary.contains("Apple"));
        assertEquals("200 USD", dictionary.get("Apple"));
        dictionary.clear();
        assertEquals(0, dictionary.size());
        assertNull(dictionary.get("Apple"));
    }

    @Test
    public void testManyElements() {
        DictionaryImpl dictionary = new DictionaryImpl();
        dictionary.put("Apple", "200 USD");
        assertEquals(1, dictionary.size());
        assertEquals("200 USD", dictionary.put("Apple", "201 USD"));
        dictionary.put("BMW", "1000 USD");
        assertEquals(2, dictionary.size());
        assertEquals("201 USD", dictionary.get("Apple"));
        assertEquals("1000 USD", dictionary.get("BMW"));
    }


    @Test
    public void testRandom() {
        DictionaryImpl dictionary = new DictionaryImpl();
        SecureRandom random = new SecureRandom();
        int size = 0;
        for (int i = 0; i < 1000; i++) {
            String key = new BigInteger(130, random).toString(32);
            String value = new BigInteger(130, random).toString(32);
            if (dictionary.put(key, value) == null) {
                size++;
            }
        }
        assertEquals(size, dictionary.size());
        dictionary.clear();
        assertEquals(0, dictionary.size());

        {
            String keys[] = new String[1000];
            for (int i = 0; i < 1000; i++) {
                String key = new BigInteger(130, random).toString(32);
                String value = new BigInteger(130, random).toString(32);
                if (dictionary.put(key, value) == null) {
                    size++;
                }
                keys[i] = key;
            }

            for (int i = 0; i < 1000; i++) {
                dictionary.remove(keys[i]);
            }

            assertEquals(0, dictionary.size());
        }

    }

}