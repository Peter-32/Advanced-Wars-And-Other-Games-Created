package SQL.Learning

import org.junit.Assert;
import org.junit.Test

/**
 * Created by Peter on 8/11/2016.
 */
class LearnColumnsTest {
    LearnColumns learnColumns = new LearnColumns("abc");

    @Test
    void getSelectStatementsFromAlreadySplitSqlStatementsByTildeTest() {
        // Assemble
        String sql = "SELECT random_string, random_int, random_string2, random_int2, random_string3, random_int3,  FROM table1 WHERE true\n~" +
                "SELECT random_string, random_int AS int_ from table2 WHERE true\n~" +
                "SELECT random_string, random_double AS double_ from table3 WHERE false~";

        // Act
        ArrayList<String> output = learnColumns.getSelectStatementsFromAlreadySplitSqlStatementsByTilde(sql);

        // Assert
        Assert.assertEquals("value should be split before FROM", output.get(0),
                "SELECT random_string, random_int, random_string2, random_int2, random_string3, random_int3,  ");
        Assert.assertEquals("value should be split before FROM", output.get(1),
                "SELECT random_string, random_int AS int_ ");
        Assert.assertEquals("value should be split before FROM", output.get(2),
                "SELECT random_string, random_double AS double_ ");
        // removed the last space, it should not be equal to this
        Assert.assertNotEquals("value should be split before FROM", output.get(2),
                "SELECT random_string, random_double AS double_");

    }

    @Test
    void removeShortSelectStatements() {
        // Assemble
        String sql = "SELECT random_string, random_int, random_string2, random_int2, random_string3, random_int3,  FROM table1 WHERE true\n~" +
                "SELECT random_string, random_int AS int_ FROM table2 WHERE true\n~" +
                "SELECT random_string, random_double AS double_ FROM table3 WHERE false~"

        // Act
        ArrayList<String> output = learnColumns.getSelectStatementsFromAlreadySplitSqlStatementsByTilde(sql);
        output = learnColumns.removeShortSelectStatements(output);

        // Assert
        Assert.assertEquals("value should be split before FROM", output.get(0),
                "SELECT random_string, random_int, random_string2, random_int2, random_string3, random_int3,  ");
        Assert.assertTrue(output.size() == 1);

    }

}
