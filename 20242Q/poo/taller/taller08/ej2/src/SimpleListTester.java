public class SimpleListTester {
 
    public static void main(String[] args) {
        SimpleList<Integer> simpleList = new SimpleArrayList<>();
 
        simpleList.add(1);
        simpleList.add(3);
        simpleList.add(5);
        simpleList.add(7);
 
        System.out.println(simpleList.size());
 
        System.out.println(simpleList.contains(0));
 
        SimpleList<Integer> otherSimpleList = simpleList.map(element -> element * 2);
 
        for(Integer element : otherSimpleList) {
            System.out.println(element);
        }
 
        SimpleList<String> anotherSimpleList =
                otherSimpleList.map(element -> String.format("<%d>", element));
 
        for(String element : anotherSimpleList) {
            System.out.println(element);
        }
    }
 
}