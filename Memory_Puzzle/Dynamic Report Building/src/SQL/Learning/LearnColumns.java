package SQL.Learning;

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
    String sql;
    String[] sqlStatements;


    //// CONSTRUCTOR
    LearnColumns(String largeSQLString) {
        this.sql = largeSQLString;
        sqlStatements = sql.split("~");

    }

}
