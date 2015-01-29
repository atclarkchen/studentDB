package db61b;

import static org.junit.Assert.*;

import org.junit.Test;

public class DatabaseTest {

    @Test
    public void testOperations() {
        Database db = new Database();
        Table t1 = Table.readTable("students");
        Table t2 = Table.readTable("enrolled");
        db.put("apples", t1);
        db.put("bananas", t2);

        Table t1out = db.get("apples");
        Table t2out = db.get("oranges");
        Table t3out = db.get("bananas");

        assertEquals(t1, t1out);
        assertEquals(null, t2out);
        assertEquals(t2, t3out);

        assertEquals(t1out.getTitle(0), "SID");
        assertEquals(t3out.getTitle(1), "CCN");
    }
}
