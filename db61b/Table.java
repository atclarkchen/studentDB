package db61b;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import static db61b.Utils.*;

/** A single table in a database.
 *  @author P. N. Hilfinger
 */
class Table implements Iterable<Row> {
    /** A new Table whose columns are given by COLUMNTITLES, which may
     *  not contain duplicate names. */

    /** A string array representing column titles of a Table. */
    private String[] _columnTitles;

    /** Constructor for Table that takes in COLUMNTITLES. */
    Table(String[] columnTitles) {
        for (int i = columnTitles.length - 1; i >= 1; i -= 1) {
            for (int j = i - 1; j >= 0; j -= 1) {
                if (columnTitles[i].equals(columnTitles[j])) {
                    throw error("duplicate column name: %s",
                                columnTitles[i]);
                }
            }
        }

        this._columnTitles = columnTitles;
    }

    /** A new Table whose columns are give by COLUMNTITLES. */
    Table(List<String> columnTitles) {
        this(columnTitles.toArray(new String[columnTitles.size()]));
    }

    /** Return the number of columns in this table. */
    public int columns() {
        return _columnTitles.length;
    }

    /** Return the title of the Kth column.  Requires 0 <= K < columns(). */
    public String getTitle(int k) {
        return this._columnTitles[k];
    }

    /** Return the number of the column whose title is TITLE, or -1 if
     *  there isn't one. */
    public int findColumn(String title) {
        for (int i = 0; i < _columnTitles.length; i++) {
            if (title.equals(_columnTitles[i])) {
                return i;
            }
        }
        return -1;
    }

    /** Return the number of Rows in this table. */
    public int size() {
        return _rows.size();
    }

    /** Returns an iterator that returns my rows in an unspecfied order. */
    @Override
    public Iterator<Row> iterator() {
        return _rows.iterator();
    }

    /** Add ROW to THIS if no equal row already exists.  Return true if anything
     *  was added, false otherwise. */
    public boolean add(Row row) throws Error {
        /**
         * Add if _rows does not already contain row to be added
         * however, if the toAdd has a length that does not match
         * up with columnTitles length, throw exception/error.
         */
        if (_rows.contains(row)) {
            return false;
        }
        if (row.size() != _columnTitles.length) {
            return false;
        } else {
            _rows.add(row);
            return true;
        }
    }

    /**
     * Returns hashSet ROWS so that other classes may access and use this data.
     */
    public HashSet<Row> getRows() {
        return _rows;
    }

    /** Read the contents of the file NAME.db, and return as a Table.
     *  Format errors in the .db file cause a DBException. */
    static Table readTable(String name) {
        BufferedReader input;
        Table table;
        input = null;
        table = null;
        try {
            input = new BufferedReader(new FileReader(name + ".db"));
            String header = input.readLine();

            if (header == null) {
                throw error("missing header in DB file");
            }
            String[] columnNames = header.split(",");

            table = new Table(columnNames);

            String toAdd = null;
            while ((toAdd = input.readLine()) != null) {
                String[] add = toAdd.split(",");
                Row r = new Row(add);
                table.add(r);
            }
        } catch (FileNotFoundException e) {
            throw error("could not find %s.db", name);
        } catch (IOException e) {
            throw error("problem reading from %s.db", name);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.out.println("IOException");
                }
            }
        }
        return table;
    }

    /** Write the contents of TABLE into the file NAME.db. Any I/O errors
     *  cause a DBException. */

    void writeTable(String name) {
        PrintStream output;
        output = null;
        try {
            String sep;
            sep = "";
            output = new PrintStream(name + ".db");

            int j = 0;
            for (String s : this._columnTitles) {
                j++;
                output.print(s);
                if (j < this._columnTitles.length) {
                    output.print(",");
                }
            }
            for (Row r : _rows) {
                String[] data = r.getData();
                int i = 0; output.println("");
                for (String s : data) {
                    i++;
                    output.print(s);
                    if (i < data.length) {
                        output.print(sep + ",");
                    }
                }
            }


        } catch (IOException e) {
            throw error("trouble writing to %s.db", name);
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }

    /** Print my contents on the standard output. */
    void print() {
        for (Row r : _rows) {
            String[] data = r.getData();
            int i = 0;
            System.out.print("  ");

            for (String s : data) {
                i++;
                System.out.print(s);
                if (i < data.length) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    /** Return a new Table whose columns are COLUMNNAMES, selected from
     *  rows of this table that satisfy CONDITIONS. */
    Table select(List<String> columnNames, List<Condition> conditions) {
        Table result = new Table(columnNames);

        ArrayList<Column> columns = new ArrayList<Column>();
        for (String col : columnNames) {
            Column c = new Column(col, this);
            columns.add(c);
        }


        if (conditions == null || conditions.isEmpty()) {
            for (Row r : _rows) {
                Row rAdd = new Row(columns, r);
                result.add(rAdd);
            }
            return result;
        } else {
            for (Row r : _rows) {
                if (Condition.test(conditions, r)) {
                    Row rAdd = new Row(columns, r);
                    result.add(rAdd);
                }
            }
            return result;
        }
    }

    /** Return a new Table whose columns are COLUMNNAMES, selected
     *  from pairs of rows from this table and from TABLE2 that match
     *  on all columns with identical names and satisfy CONDITIONS. */
    Table select(Table table2, List<String> columnNames,
                 List<Condition> conditions) {

        Table result = new Table(columnNames);
        List<Column> common1 = new ArrayList<Column>();
        List<Column> common2 = new ArrayList<Column>();
        LinkedHashSet<String> allColumnNames = new LinkedHashSet<String>();

        ArrayList<Column> selectColumns = new ArrayList<Column>();
        for (String c : columnNames) {
            Column add = new Column(c, this, table2);
            selectColumns.add(add);
        }

        for (String s1 : this._columnTitles) {
            allColumnNames.add(s1);
        }
        for (String s2 : table2._columnTitles) {
            allColumnNames.add(s2);
        }
        int size = allColumnNames.size();
        String[] allCols = allColumnNames.toArray(new String[size]);

        for (String col : allCols) {
            int col1Index = this.findColumn(col);
            int col2Index = table2.findColumn(col);

            if ((col1Index != -1) && (col2Index != -1)) {
                Column add1 = new Column(col, this);
                Column add2 = new Column(col, table2);
                common1.add(add1);
                common2.add(add2);
            }
        }

        for (Row r1 : this._rows) {
            for (Row r2 : table2._rows) {
                if (conditions == null || Condition.test(conditions, r1, r2)) {
                    if (common1.isEmpty() || common2.isEmpty()) {
                        Row add = new Row(selectColumns, r1, r2);
                        result.add(add);
                    } else if (equijoin(common1, common2, r1, r2)) {
                        Row toAdd = new Row(selectColumns, r1, r2);
                        result.add(toAdd);
                    }
                }
            }
        }
        return result;
    }

    /** Return true if the columns COMMON1 from ROW1 and COMMON2 from
     *  ROW2 all have identical values.  Assumes that COMMON1 and
     *  COMMON2 have the same number of elements and the same names,
     *  that the columns in COMMON1 apply to this table, those in
     *  COMMON2 to another, and that ROW1 and ROW2 come, respectively,
     *  from those tables. */
    private static boolean equijoin(List<Column> common1, List<Column> common2,
                                    Row row1, Row row2) {
        if (common1.isEmpty() || common2.isEmpty()) {
            return true;
        }
        int i = 0;
        for (Column c : common1) {
            if (!c.getFrom(row1).equals(common2.get(i).getFrom(row2))) {
                return false;
            }
            i++;
        }
        return true;
    }
    /** My rows. */
    private HashSet<Row> _rows = new HashSet<>();
}

