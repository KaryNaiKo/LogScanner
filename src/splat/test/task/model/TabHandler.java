package splat.test.task.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.ArrayDeque;

public class TabHandler {
    private String keyWord;
    private RandomAccessFile reader;
    private ArrayDeque<Long> positionsOfKeyWord;
    private final int SIZE_FOR_READING = 1024;

    public TabHandler(String keyWord, Path path) throws FileNotFoundException {
        this.keyWord = keyWord;
        this.reader = new RandomAccessFile(path.toFile(), "r");
        this.positionsOfKeyWord = new ArrayDeque<>();
    }


    public String loadFirst() {
        try {
            long pos = findFistEntry();
            return readFrom(pos - SIZE_FOR_READING/2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String loadNext() {
        try {
            long pos = findNextEntry();
            return readFrom(pos /*- SIZE_FOR_READING/2*/);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String loadPrevious() {
        try {
            long pos = findPreviousEntry();
            return readFrom(pos /*- SIZE_FOR_READING/2*/);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private long findFistEntry() throws IOException {
        return findEntry(0);
    }

    private long findEntry(long start)throws IOException{
        reader.seek(start);
        long positionOfFistEntry = start;
        for (String line; (line = reader.readLine()) != null; ) {
            if(line.contains(keyWord)) {
                positionOfFistEntry += line.indexOf(keyWord);
                break;
            } else {
                positionOfFistEntry += line.length();
            }
        }
        positionsOfKeyWord.addFirst(positionOfFistEntry);
        return positionOfFistEntry;
    }

    private long findNextEntry() throws IOException{
        long pos = positionsOfKeyWord.peek() != null ? positionsOfKeyWord.peek() : 0;
        return findEntry(pos+1);
    }

    private long findPreviousEntry() {
        return positionsOfKeyWord.poll() != null ? positionsOfKeyWord.poll() : 0;
    }

    private String readFrom(long from) throws IOException{
        if(from < 0) from = 0;
        reader.seek(from);
        byte[] bytes = new byte[SIZE_FOR_READING];
        int length = reader.read(bytes, 0, SIZE_FOR_READING);
        return new String(bytes, 0, length, "UTF-8");
    }
}
