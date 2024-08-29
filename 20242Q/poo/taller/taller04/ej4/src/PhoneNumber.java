public class PhoneNumber implements Comparable<PhoneNumber> {
    
    private int areaCode;
    private int prefix;
    private int lineNumber;

    public PhoneNumber(int areaCode, int prefix, int lineNumber) {
        this.areaCode = areaCode;
        this.prefix = prefix;
        this.lineNumber = lineNumber;
    }

    @Override
    public int compareTo(PhoneNumber o) {
        int temp;

        temp = compareLocal(areaCode, o.areaCode);
        
        if(temp == 0) {

            temp = compareLocal(prefix, o.prefix);
            
            if(temp == 0) {
                return compareLocal(lineNumber, o.lineNumber);
            }
        }

        return temp;
    }

    private int compareLocal(int a, int b) {
        return a - b;
    }

}
