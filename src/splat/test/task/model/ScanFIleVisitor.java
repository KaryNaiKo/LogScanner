package splat.test.task.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.BlockingQueue;

public class ScanFIleVisitor extends SimpleFileVisitor<Path> {
    private String keyWord;
    private String fileExtension;
    private BlockingQueue<Path> queue;

    public ScanFIleVisitor(String keyWord, String fileExtension, BlockingQueue<Path> queue) {
        this.keyWord = keyWord;
        this.fileExtension = fileExtension;
        this.queue = queue;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (file.toString().endsWith(fileExtension)) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file.toFile())))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    if(line.contains(keyWord)){
                        queue.add(file);
                    }
                }
            }
        }
        return FileVisitResult.CONTINUE;
    }
}
