package com.exist.ecc.service;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import com.exist.ecc.model.Cell;

public class CellOperationsTest {
    @Test
    public void TestCount() {
        Cell cell = new Cell("aaas", "bqwe");

        assertEquals(0, CellOperations.countOccurencesInKey(cell, ""));
        assertEquals(0, CellOperations.countOccurencesInKey(cell, " "));
        assertEquals(0, CellOperations.countOccurencesInKey(cell, "ss"));
        assertEquals(3, CellOperations.countOccurencesInKey(cell, "a"));
        assertEquals(2, CellOperations.countOccurencesInKey(cell, "aa"));
        assertEquals(1, CellOperations.countOccurencesInKey(cell, "aaa"));
    }
}
