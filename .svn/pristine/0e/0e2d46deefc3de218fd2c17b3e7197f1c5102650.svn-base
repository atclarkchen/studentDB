
package db61b;

import java.util.Arrays;
import java.util.List;

/** A single row of a database.
 *  @author ClarkChen
 */
class Row {
    /** A Row whose column values are DATA.  The array DATA must not be altered
     *  subsequently. */
    Row(String[] data) {
        _data = data;
    }

    /** Given N columns and rows, returns a new row containing one column from
     *  each of the supplied ROWS.
     *
     *  The value for the ith column of this new row will come from the ith row
     *  in ROWS, using the ith entry of COLUMNS as an effective column index.
     *
     *  There is a method in the Columns rclass that you'll need to use,
     *  see {@link db61b.Column#getFrom}).  you're wondering why this looks like
     *  a potentially clickable link it is! Just not in source. You might
     *  consider converting this spec to HTML using the Javadoc command.
     */

    Row(List<Column> columns, Row... rows) {
        _data = new String[columns.size()];
        for (int i = 0; i < columns.size(); i++) {
            String x = columns.get(i).getFrom(rows);
            _data[i] = x;
        }
    }

    /** Return my number of columns. */
    int size() {
        return _data.length;
    }

    /** Return the value of my Kth column.  Requires that 0 <= K < size().*/
    String get(int k) {
        return _data[k];
    }

    /** Helper function to return _data.*/
    public String[] getData() {
        return _data;
    }

    @Override
    public boolean equals(Object obj) throws IllegalArgumentException {
        if (!(obj instanceof Row)) {
            throw new IllegalArgumentException("object is not of type Row!");
        } else {
            boolean rtn;
            rtn = Arrays.deepEquals(((Row) (obj)).getData(), this.getData());
            if (!rtn) {
                return rtn;
            }
            return true;
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(_data);
    }

    /** Contents of this row. */
    private String[] _data;
}
