
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import ru.spbau.mit.SecondPartTasks;

import java.io.UncheckedIOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class SecondPartTasksTest {

    @Test(expected = UncheckedIOException.class)
    public void testFindQuotesFileNotFound() {
        SecondPartTasks.findQuotes(Arrays.asList(""), "null");
    }

    @Test()
    public void testFindQuotes() {
        List<String> paths = Arrays.asList(Paths.get("src/main/resources/words.txt").toAbsolutePath().toString(),
                                            Paths.get("src/main/resources/words.txt").toAbsolutePath().toString());
        List<String> elements = SecondPartTasks.findQuotes(paths, "output");
        assertEquals(2, elements.size());
        assertEquals(elements, Arrays.asList("karacuba output file output", "karacuba output file output"));
    }

    @Test()
    public void testPiDividedBy4() {
        for (int i = 0; i < 10; i++) {
            assertEquals(Math.PI / 4.0, SecondPartTasks.piDividedBy4(), 1e-3);
        }
    }

    @Test
    public void testFindPrinter() {
        Map<String, List<String>> values = ImmutableMap.of(
                "one", Arrays.asList("expiration date time", "valuation date time"),
                "two", Arrays.asList("fair market price", "market data")
        );
        assertEquals("one", SecondPartTasks.findPrinter(values));
    }

    @Test
    public void testCalculateGlobalOrder() {
        List<Map<String, Integer>> shares =
                Arrays.asList(
                        ImmutableMap.of(
                                "Apple", 10,
                                "BMW", 100,
                                "Android", 5
                        ),
                        ImmutableMap.of(
                                "Apple", 12,
                                "Audi", 150,
                                "BMW", 12
                        )
                );

        Map<String, Integer> sum = SecondPartTasks.calculateGlobalOrder(shares);

        assertEquals(4, sum.size());
        assertEquals(22, sum.get("Apple").intValue());
        assertEquals(112, sum.get("BMW").intValue());
        assertEquals(150, sum.get("Audi").intValue());
        assertEquals(5, sum.get("Android").intValue());
    }
}