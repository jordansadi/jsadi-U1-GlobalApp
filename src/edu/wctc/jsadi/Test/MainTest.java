package edu.wctc.jsadi.Test;

import edu.wctc.jsadi.FileInput;
import edu.wctc.jsadi.FileOutput;
import org.junit.*;

import java.util.ArrayList;

public class MainTest extends junit.framework.TestCase {
    public MainTest() {}

    FileInput inFile, sortedFile;
    String line;
    String[] lines;
    ArrayList<String[]> toSort;
    FileOutput sorted;

    @Before
    public void setUp() throws Exception {
        inFile = new FileInput("places.csv");
        toSort = new ArrayList<>();
        sorted = new FileOutput("testSort.csv");
    }

    @After
    public void tearDown() throws Exception {
        inFile.fileClose();
        sortedFile.fileClose();
    }

    @Test
    public void testSortFile() {
        while ((line = inFile.fileReadLine()) != null) {
            lines = line.split(",");
            toSort.add(lines);

            int n = toSort.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (toSort.get(j)[1].compareTo(toSort.get(j + 1)[1]) > 0) {
                        String[] temp = toSort.get(j);
                        toSort.set(j, toSort.get(j + 1));
                        toSort.set(j + 1, temp);
                    }
                }
            }
        }

        sorted = new FileOutput("testSort.csv");

        for (String[] s : toSort) {
            sorted.fileWrite(s[0] + "," + s[1] + "," + s[2] + "," + s[3]);
        }

        sorted.fileClose();

        sortedFile = new FileInput("testSort.csv");

        line = sortedFile.fileReadLine();
        lines = line.split(",");

        assertEquals(lines[1], "Aarhus");
        assertNotSame(lines[0], "Afghanistan");
    }
}
