package splat.test.task.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.ArrayDeque;

public class TabHandler {
    private String keyWord;
    private RandomAccessFile reader;
    private ArrayDeque<Long> positionsOfPreviousKeyWord;
    private long currentBytePos;
    private int currentCharPos;
    private final int SIZE_FOR_READING = 1024;

    public TabHandler(String keyWord, Path path) throws FileNotFoundException {
        this.keyWord = keyWord;
        this.reader = new RandomAccessFile(path.toFile(), "r");
        this.positionsOfPreviousKeyWord = new ArrayDeque<>();
    }


    public String[] loadFirst() {
        try {
            long pos = findFistEntry();
            return readFrom(pos - SIZE_FOR_READING/2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] loadNext() {
        try {
            long pos = findNextEntry();
            return readFrom(pos - SIZE_FOR_READING/2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] loadPrevious() {
        try {
            long pos = findPreviousEntry();
            return readFrom(pos - SIZE_FOR_READING/2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private long findFistEntry() throws IOException {
        return findEntry(0);
    }

    private long findEntry(long start)throws IOException{
        reader.seek(start);
        currentBytePos = start;
        currentCharPos = 0;
        for (String line; (line = reader.readLine()) != null; ) {
            if(line.contains(keyWord)) {
                currentCharPos += line.indexOf(keyWord);
                currentBytePos += line.substring(0, line.indexOf(keyWord)).getBytes().length;
                break;
            } else {
                currentCharPos += line.length();
                currentBytePos += line.getBytes().length;
            }
        }
        return currentBytePos;
    }

    private long findNextEntry() throws IOException{
        positionsOfPreviousKeyWord.addFirst(currentBytePos);
        return findEntry(currentBytePos+1);
    }

    private long findPreviousEntry() {
        currentBytePos = positionsOfPreviousKeyWord.peek() != null ? positionsOfPreviousKeyWord.poll() : 0;
        return currentBytePos;
    }

    private String[] readFrom(long from) throws IOException{
        //TODO некорректно определяет index
        if(from < 0) from = 0;
        reader.seek(from);
        long index = currentBytePos - from;
        byte[] bytes = new byte[SIZE_FOR_READING];
        int length = reader.read(bytes, 0, SIZE_FOR_READING);
        if (length != -1) {
            String[] strings = new String[3];
            strings[0] = new String(bytes, 0, (int)index, "UTF-8");
            strings[1] = new String(bytes, (int)index, keyWord.length(), "UTF-8");
            strings[2] = new String(bytes, (int)index + keyWord.length(), length - ((int)index + keyWord.length()), "UTF-8");

            String str = new String(bytes, 0, bytes.length, "UTF-8");
            String sub = str.substring(currentCharPos, currentCharPos + keyWord.length());
            return strings;
        }
        return null;
    }
}