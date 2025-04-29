package core;

import java.io.IOException;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws IOException {
        System.out.println(Utils.getPrefixDir());
        System.out.println(Utils.listFilesRelativePath("docs", Arrays.asList("txt","pdf"))); //relativo a PrefixDir
        System.out.println(Utils.listFilesAbsolutePath("Z:/EDA/RepositorioLucene/docs", Arrays.asList("txt","pdf")));
    }
}
