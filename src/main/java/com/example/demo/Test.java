package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Test {

    public static void main(String[] args) throws Exception {
        ReaderTest readerTest = new ReaderTest(System.in);
        new Thread(readerTest).start();
        Thread.sleep(3000L);
        System.out.println("About to close...");
        System.in.close(); // Should cause thread to throw exception and terminate, but just hangs
        System.out.println("All closed...");
    }

    private static class ReaderTest implements Runnable {
        private final InputStream inputStream;

        public ReaderTest(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            try {
                System.out.println("Runner: starting"); // me
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("Read line:");
                    System.out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
