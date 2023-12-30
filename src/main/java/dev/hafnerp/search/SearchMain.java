package dev.hafnerp.search;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
public class SearchMain {

    private static final Logger logger = LogManager.getLogger(SearchMain.class);

    public static void searchFor(boolean first, String word, Path path) throws InterruptedException, IOException {

        if (word != null) {
            Thread thread = new Thread(new SearchA(first, word, path));
            thread.start();
            thread.join();
        }
        else {
            logger.debug("No word was given! Adding directory entries instead...");
            DirectoryStream<Path> files = Files.newDirectoryStream(path);
            ListWrapper<Path> listWrapper = ListWrapper.getPathInstance();
            for (Path path2 : files) {
                listWrapper.add(path2);
                if (first) break;
            }
        }
    }
}
