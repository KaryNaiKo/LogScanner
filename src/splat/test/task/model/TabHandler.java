package splat.test.task.model;

import splat.test.task.exeptions.ExceptionHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayDeque;

public class TabHandler {
    private String keyWord;
    private RandomAccessFile reader;
    private ArrayDeque<Long> positionsOfPreviousKeyWord;
    private long currentBytePos;
    private final int SIZE_FOR_READING = 1024;

    public TabHandler(String keyWord, Path path) throws FileNotFoundException {
        this.keyWord = keyWord;
        this.reader = new RandomAccessFile(path.toFile(), "r");
        this.positionsOfPreviousKeyWord = new ArrayDeque<>();
    }

    public String[] loadFirst() {
        try {
            long pos = findFistEntry();
            return readFrom(pos - SIZE_FOR_READING / 2);
        } catch (IOException e) {
            ExceptionHandler.logExeption(e);
        }
        return null;
    }

    public String[] loadNext() {
        try {
            long pos = findNextEntry();
            if (pos != -1) {
                return readFrom(pos - SIZE_FOR_READING / 2);
            }
        } catch (IOException e) {
            ExceptionHandler.logExeption(e);
        }
        return null;
    }

    public String[] loadPrevious() {
        try {
            long pos = findPreviousEntry();
            if (pos != -1) {
                return readFrom(pos - SIZE_FOR_READING / 2);
            }
        } catch (IOException e) {
            ExceptionHandler.logExeption(e);
        }
        return null;
    }

    private long findFistEntry() throws IOException {
        return findEntry(0);
    }

    private long findEntry(long start) throws IOException {
        reader.seek(start);
        currentBytePos = start;
        boolean isFound = false;
        int length;
        do {
            byte[] bytes = new byte[SIZE_FOR_READING];
            length = reader.read(bytes, 0, SIZE_FOR_READING);
            if(length != -1) {
                String str = new String(bytes, 0, length);
                if (str.contains(keyWord)) {
                    currentBytePos += str.substring(0, str.indexOf(keyWord)).getBytes().length;
                    isFound = true;
                } else {
                    currentBytePos += length;
                }
            } else {
                break;
            }
        } while (!isFound);

        if (isFound) {
            return currentBytePos;
        }
        return -1;
    }

    private long findNextEntry() throws IOException {
        if (positionsOfPreviousKeyWord.isEmpty()) {
            positionsOfPreviousKeyWord.addFirst(currentBytePos);
        } else if (positionsOfPreviousKeyWord.peek() != currentBytePos - 1) {
            positionsOfPreviousKeyWord.addFirst(currentBytePos);
        }
        return findEntry(currentBytePos + 1);
    }

    private long findPreviousEntry() {
        currentBytePos = positionsOfPreviousKeyWord.peek() != null ? positionsOfPreviousKeyWord.poll() : -1;
        return currentBytePos;
    }

    private String[] readFrom(long from) throws IOException {
        if (from < 0) from = 0;
        reader.seek(from);
        long index = (currentBytePos - from) % SIZE_FOR_READING;
        byte[] bytes = new byte[SIZE_FOR_READING];
        int length = reader.read(bytes, 0, SIZE_FOR_READING);
        if (length != -1) {
            String[] strings = new String[3];
            strings[0] = new String(bytes, 0, (int) index, StandardCharsets.UTF_8);
            strings[1] = new String(bytes, (int) index, keyWord.length(), StandardCharsets.UTF_8);
            strings[2] = new String(bytes, (int) index + keyWord.length(), length - ((int) index + keyWord.length()), StandardCharsets.UTF_8);
            return strings;
        }
        return null;
    }
}