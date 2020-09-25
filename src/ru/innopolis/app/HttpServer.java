package ru.innopolis.app;

import java.io.*;

public class HttpServer {
    /**
     * Уровень вложенности файлов
     */
    private int nestingLevel = 0;

    /**
     * Метод читает запрос из потока is и отправляет response в поток os.
     * Если request метод GET, отправляет http response со статус кодом 200.
     * При получении любых другиз запросов выдается ошибка 404.
     * @param is - входной поток - request
     * @param os - выходной поток - response
     */
    public void handle(InputStream is, OutputStream os) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is));
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {
            String str = br.readLine();
            if (str.contains("GET")) {
                final String content = getFilesAndDirectories();
                bw.write("HTTP/1.1 200 OK\r\n");
                bw.write("Content-Type: text/html; charset=UTF-8\r\n");
                bw.write("Content-Length: " + content.length() + "\r\n");
                bw.write("\r\n");
                bw.write(content);
            } else {
                bw.write("HTTP/1.1 404 NotFound\r\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод возвращает список директорий
     *
     * @return список директорий
     */
    private String getFilesAndDirectories() {
        File dir = new File(".");
        return "<body>" +
                writeDirectory(dir) +
                "</body>";
    }

    /**
     * Формирует список файлов и директорий в директории dir.
     * При наличии вложенных файлов метод вызывается рекурсивно
     * @param dir - директория для которой сформируется список директорий
     * @return - список директорий
     */
    private String writeDirectory(File dir) {
        final StringBuilder stringBuilder = new StringBuilder();
        final File[] files = dir.listFiles();
        for (File file : files) {
            stringBuilder
                    .append("<pre>")
                    .append(indent())
                    .append("-")
                    .append(file.getName());
            if (file.isDirectory()) {
                stringBuilder
                        .append(":")
                        .append("</br>")
                        .append("</pre>");
                nestingLevel++;
                stringBuilder.append(writeDirectory(file));
                nestingLevel--;
            } else {
                stringBuilder
                        .append("</br>")
                        .append("</pre>");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Добавляет табуляцию для вложенных дирректориев
     * @return - возвращает табуляцию
     */
    private String indent() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < nestingLevel; i++) {
            stringBuilder.append("\t");
        }
        return stringBuilder.toString();
    }
}
