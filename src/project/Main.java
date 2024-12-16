package project;

public class Main {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        FileProcessor.process();
        long end = System.currentTimeMillis();
        long diff = end - start;
        System.out.println("Time taken: " + diff + " ms");
    }
}
