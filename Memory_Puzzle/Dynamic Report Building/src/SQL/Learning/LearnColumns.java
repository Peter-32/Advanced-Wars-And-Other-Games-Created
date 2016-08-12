package SQL.Learning;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Peter on 8/11/2016.
 */

// Feed the program certain SQL reports to learn.  Stops at the FROM statement.
// generation of it using SQL (everything before the alias)
// ranking (Based on occurrence)
// The alias
// The table it comes from (Another program finds this later using CREATE TABLE statements)

//// OUTPUT:

// Store the data as a text file that can be fed to a program to find the table it comes from
// then pasted into Java later
// CREATION=&ALIAS=&TABLE=&RANKING=
// (repeat)

public class LearnColumns {

    //// FIELDS



    //// CONSTRUCTOR
    LearnColumns(String largeSQLString) {
        ArrayList<String> sqlSelects = getSelectStatementsFromAlreadySplitSqlStatementsByTilde(largeSQLString);
        sqlSelects = removeShortSelectStatements(sqlSelects);
        for (String sql : sqlSelects) {
            System.out.println(sql);
        }

    }

    ArrayList<String> getSelectStatementsFromAlreadySplitSqlStatementsByTilde(String sql) {
        String[] sqlStatementsArr = sql.split("~");
        ArrayList<String> sqlSelects = new ArrayList<String>(Arrays.asList(sqlStatementsArr));
        String[] tempSqlStatement;

        // Keep only the parts before the FROM statement
        int i = 0;
        for (String statement : sqlSelects) {
            tempSqlStatement = statement.split("(?i)FROM");
            sqlSelects.set(i, tempSqlStatement[0]);
            i++;
        }
        return sqlSelects;
    }

    ArrayList<String> removeShortSelectStatements(ArrayList<String> sqlSelects) {
        ArrayList<String> newSqlSelects = new ArrayList<String>();

        // remove anything shorter than 20 characters
        for (String statement : sqlSelects) {
            if (statement.length() > 50) {
                newSqlSelects.add(statement);
            }
        }
        return newSqlSelects;
    }

}
