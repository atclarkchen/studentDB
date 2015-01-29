package db61b;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TableTest {

    @Test
    public void testBasics() {
        String[] colTitles = new String[]{"a", "b", "c", "d"};
        Table table = new Table(colTitles);

        ArrayList<String> lst = new ArrayList<String>();
        lst.add("cat"); lst.add("dog"); lst.add("mouse");
        Table t1 = new Table(lst);

        assertEquals(4, table.columns());
        assertEquals(3, t1.columns());

        assertEquals("a", table.getTitle(0));
        assertEquals("d", table.getTitle(3));
        assertEquals("cat", t1.getTitle(0));
        assertEquals("dog", t1.getTitle(1));

        assertEquals(1, table.findColumn("b"));
        assertEquals(3, table.findColumn("d"));
        assertEquals(2, t1.findColumn("mouse"));
        assertEquals(0, t1.findColumn("cat"));

        String[] temp = new String[]{"Clark", "Maharbiz", "dog", "cat", "meow"};
        Row toAdd = new Row(temp);
        assertEquals(false, table.add(toAdd));
        String[] temp2 = new String[]{"Clark", "Maharbiz", "dog", "cat"};
        Row toAdd1 = new Row(temp2);
        assertEquals(true, table.add(toAdd1));
    }

    Table t = Table.readTable("enrolled");
    @Test
    public void testReadTable() {
        assertEquals(t.getTitle(0), "SID");
        assertEquals(t.getTitle(2), "Grade");
        assertEquals(t.size(), 19);
        assertEquals(t.findColumn("CCN"), 1);

        boolean fileNotExist = false;
        try {
            Table t1 = Table.readTable("unicorns");
        } catch (DBException e) {
            fileNotExist = true;
        }
        assertTrue(fileNotExist);
    }

    /**
    * Writes the content of the Table (this) into Name.db.
    */
    @Test
    public void testWriteTable() {
        t.writeTable("enrollWT");

        Table t1 = Table.readTable("students");
        t1.writeTable("studentsWT");
    }

    @Test
    public void testPrintTable() {
        Table t1 = new Table(new String[]{"Name", "Surname", "Grade"});
        Row a = new Row(new String[]{"Jason", "Knowles", "B"});
        Row b = new Row(new String[]{"Shana", "Brown", "B+"});
        Row c = new Row(new String[]{"Yangfan", "Chan", "B"});
        Row d = new Row(new String[]{"Valerie", "Chan", "B+"});
        t1.add(a);
        t1.add(b);
        t1.add(c);
        t1.add(d);

        Table t2 = Table.readTable("students");
    }

    @Test
    public void testOneTableSelect() {
        Table tt = Table.readTable("students");
        System.out.println("TEST 1");
        System.out.println("testing 1 condition select w/column 1 Condition");
        Table expected = new Table(new String[]{"SID", "Firstname"});
        Row rr1 = new Row(new String[]{"102", "Valerie"});
        Row rr2 = new Row(new String[]{"106", "Yangfan"});
        expected.add(rr1); expected.add(rr2);
        System.out.println("Expected table is: ");
        expected.print();
        Column col1 = new Column("Lastname", tt);
        String relation = "="; String val1 = "Chan";
        Condition c = new Condition(col1, relation, val1);
        ArrayList<Condition> conditions = new ArrayList<Condition>();
        conditions.add(c);
        ArrayList<String> columnNames = new ArrayList<String>();
        columnNames.add("SID"); columnNames.add("Firstname");
        Table tSelect1 = tt.select(columnNames, conditions);
        System.out.println("Actual table is: ");
        tSelect1.print(); System.out.println();
        System.out.println("TEST 2");
        System.out.println("TESTING 1 CONDITION SELECT WITH 1 CONDITION");
        Table exp2 = new Table(new String[]{"SID"});
        Row rrr1 = new Row(new String[]{"102"});
        Row rrr2 = new Row(new String[]{"104"});
        Row rr3 = new Row(new String[]{"105"});
        Row rr4 = new Row(new String[]{"106"});
        exp2.add(rrr1); exp2.add(rrr2); exp2.add(rr3); exp2.add(rr4);
        System.out.println("Testing 1 condition select with 1 Condition Type2");
        ArrayList<Condition> conditions1 = new ArrayList<Condition>();
        Column col2 = new Column("Lastname", tt);
        Column coll1 = new Column("Firstname", tt); String rel = ">";
        Condition c1 = new Condition(coll1, rel, col2);
        conditions1.add(c1);
        ArrayList<String> colNames = new ArrayList<String>();
        colNames.add("SID");
        Table tSelect2 = tt.select(colNames, conditions1);
        System.out.println("Expected table is:"); exp2.print();
        System.out.println("Actual table is: "); tSelect2.print();
        System.out.println("TEST 3");
        System.out.println("testing 2 conditions select with SINGLE table...");
        Table exp3 = new Table(new String[]{"SID", "Lastname"});
        Row rrrr1 = new Row(new String[]{"101", "Knowles"});
        Row rrrr2 = new Row(new String[]{"104", "Armstrong"});
        exp3.add(rrrr1); exp3.add(rrrr2);
        System.out.println("Expected table is: "); exp3.print();
        colNames = new ArrayList<String>();
        colNames.add("SID"); colNames.add("Lastname");
        Column coL1A = new Column("YearEnter", tt);
        String vaL1 = "2003"; String reL = "=";
        Condition cond1 = new Condition(coL1A, reL, vaL1);
        Column coL2 = new Column("Major", tt);
        String vaL2 = "EECS";
        Condition cond2 = new Condition(coL2, reL, vaL2);
        ArrayList<Condition> conds = new ArrayList<Condition>();
        conds.add(cond1); conds.add(cond2);
        Table tSelect4 = tt.select(colNames, conds);
        System.out.println("Actual table is: ");
        tSelect4.print();
    }
    @Test
    public void testTwoTableSelect() {
        System.out.println("Testing Two Table Select...");
        Table t1 = Table.readTable("students");
        Table t2 = Table.readTable("enrolled");
        System.out.println();
        System.out.println("TEST 1");
        System.out.println("Select 3 COLUMNs from 2 TABLES with NO CONDITIONS");
        List<String> colnames = new ArrayList<String>();
        colnames.add("Firstname");
        colnames.add("Lastname");
        colnames.add("Grade");
        ArrayList<Condition> conditions = new ArrayList<Condition>();

        Table t1actual = t1.select(t2, colnames, conditions);
        t1actual.print();
        System.out.println("TEST 2");
        System.out.println("Select 3 COLUMNS from 2 TABLES with 1 CONDITION.");

        Table exp = new Table(new String[]{"Firstname", "Lastname", "Grade"});
        Row r1 = new Row(new String[]{"Jason", "Knowles", "B"});
        Row r2 = new Row(new String[]{"Shana", "Brown", "B+"});
        Row r3 = new Row(new String[]{"Yangfan", "Chan", "B"});
        Row r4 = new Row(new String[]{"Valerie", "Chan", "B+"});
        exp.add(r1); exp.add(r2); exp.add(r3); exp.add(r4);

        System.out.println("Expected Table is: ");
        exp.print();

        Column col1 = new Column("CCN", t1, t2);
        String val2 = "21001";
        String relation = "=";

        Condition cond1 = new Condition(col1, relation, val2);
        ArrayList<Condition> conds = new ArrayList<Condition>();
        conds.add(cond1);
        Table actual = t1.select(t2, colnames, conds);

        System.out.println("Actual Table is: ");
        actual.print();
    }
}
