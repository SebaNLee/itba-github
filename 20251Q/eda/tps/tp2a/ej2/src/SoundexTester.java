public class SoundexTester {

    public static void main(String[] args) {
        Soundex soundex = new Soundex();

        
        System.out.println(soundex.representation("threshold"));
        System.out.println(soundex.representation("hold"));
        System.out.println(soundex.representation("phone"));
        System.out.println(soundex.representation("foun"));
    }
}
