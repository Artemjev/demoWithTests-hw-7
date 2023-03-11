package com.example.demowithtests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class test {
    private static final String urlStr = "http://localhost:8087/api/users/mass-test-update/";
//    private static final String urlStr = "http://localhost:8087/api/users/mass-test-update/";

    public static void main(String[] args) throws Exception {



        ExecutorService executor = Executors.newFixedThreadPool(10); // Создаем пул потоков

        for (int i = 0; i < 1000; i++) {
            final int j = i;
            executor.execute(() -> {
                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);

                    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                    writer.write("updated data");
                    writer.flush();
                    writer.close();

                    int responseCode = conn.getResponseCode();
                    // обработка ответа
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            executor.execute(() -> {
                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("PATCH");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);

                    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                    writer.write("updated data");
                    writer.flush();
                    writer.close();

                    int responseCode = conn.getResponseCode();
                    // обработка ответа
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Все задачи выполнены");
    }
}


