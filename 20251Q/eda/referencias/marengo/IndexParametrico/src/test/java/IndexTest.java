import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IndexTest {
    private final IndexWithDuplicates<Integer> index = new IndexWithDuplicates<>();

    @Test
    void test1() {
        index.initialize(new Integer[]{6,6,6,6,6,6,6,6,6,6});

        Assertions.assertEquals(10,index.occurrences(6));
        index.insert(6);
        index.insert(6);
        index.insert(6);
        Assertions.assertEquals(13,index.occurrences(6));
        index.delete(10);
        Assertions.assertEquals(13,index.occurrences(6));
        Assertions.assertEquals(0,index.occurrences(10));
        Assertions.assertEquals(0,index.occurrences(4));
        for(int i=1; i<=3000; i++){
            index.insert(10);
            Assertions.assertEquals(i,index.occurrences(10));
        }

        for(int i=1; i<=2500; i++)
            index.delete(10);
        Assertions.assertEquals(500,index.occurrences(10));
    }

    @Test
    void test2() {
        index.initialize(new Integer[]{5,6,6,6,6,6,6,8,8,8,8,8,8,8,8,8,9});
        Assertions.assertEquals(6, index.occurrences(6));
        Assertions.assertEquals(9, index.occurrences(8));
        Assertions.assertEquals(1, index.occurrences(5));
        Assertions.assertEquals(1, index.occurrences(9));
        index.delete(10);
        Assertions.assertTrue(index.search(1200));
    }

    @Test
    void test3() {
        index.initialize(new Integer[]{4,4,4,4,4,4,4,4,4,4});
        Assertions.assertEquals(-1, index.binarySearch(3,0,9));
    }

    @Test
    void test4() {
        Assertions.assertThrows(Exception.class, () -> index.initialize(null));
        index.initialize(new Integer[]{1,2,3});
        index.delete(3);
        Assertions.assertEquals(0, index.occurrences(3));
        index.initialize(new Integer[]{4,5,6,7,9});
        System.out.println(index.firstOccurrence(6));
        System.out.println(index.lastOccurrence(10));
    }

    @Test
    void test5() {
        index.initialize(new Integer[]{1,2,3});
        Assertions.assertArrayEquals(new Integer[]{1,2,3}, index.range(1,3,true,true));
        Assertions.assertArrayEquals(new Integer[]{2}, index.range(1,3,false,false));
        Assertions.assertArrayEquals(new Integer[]{2,3}, index.range(1,3,false,true));
        Assertions.assertArrayEquals(new Integer[]{1,2}, index.range(1,3,true,false));
        index.initialize(new Integer[]{4,4,4,4,4,4,4});
        Assertions.assertArrayEquals(new Integer[]{}, index.range(1,3,true,true));
        Assertions.assertArrayEquals(new Integer[]{}, index.range(1,3,false,false));
        Assertions.assertArrayEquals(new Integer[]{}, index.range(1,3,false,true));
        Assertions.assertArrayEquals(new Integer[]{}, index.range(1,3,true,false));
        index.initialize(new Integer[]{4,4,4,4,4,4,4});
        Assertions.assertArrayEquals(new Integer[]{4,4,4,4,4,4,4}, index.range(4,5,true,true));
        Assertions.assertArrayEquals(new Integer[]{}, index.range(4,5,false,false));
        Assertions.assertArrayEquals(new Integer[]{}, index.range(4,5,false,true));
        Assertions.assertArrayEquals(new Integer[]{4,4,4,4,4,4,4}, index.range(4,5,true,false));
        index.initialize(new Integer[]{1,3,5,6,7,8,15});
        Assertions.assertArrayEquals(new Integer[]{6,7,8,15}, index.range(6,15,true,true));
        Assertions.assertArrayEquals(new Integer[]{7,8}, index.range(6,15,false,false));
        Assertions.assertArrayEquals(new Integer[]{7,8,15}, index.range(6,15,false,true));
        Assertions.assertArrayEquals(new Integer[]{6,7,8}, index.range(6,15,true,false));
    }

    @Test
    void test6() {
        index.initialize(new Integer[]{});
        Assertions.assertArrayEquals(new Integer[]{}, index.range(1,3,true,true));
    }

    @Test
    void test7() {
        index.initialize(new Integer[]{8});
        index.insert(5);
        index.insert(2);
        index.insert(10);
        index.insert(1);
        index.insert(3);
        Assertions.assertEquals(1, index.getMin());
        Assertions.assertEquals(10, index.getMax());
    }
}
