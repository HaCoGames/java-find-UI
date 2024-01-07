package dev.hafnerp.search;

import dev.hafnerp.logger.EventLogger;
import dev.hafnerp.logger.PathLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
public class SearchMain {

    private static final Logger logger = LogManager.getLogger(SearchMain.class);

    public static void searchFor(boolean first, String word, Path path, int delay) throws InterruptedException, IOException {
        ListWrapper listWrapper = ListWrapper.getPathInstance();
        listWrapper.reset();

        PathLogger pathLogger = PathLogger.getInstance();
        pathLogger.reset();
        pathLogger.setFirst(first);

        SearchA.setInstanceCounterAll(0);

        if (word != null) {
            Thread thread = new Thread(new SearchA(first, word, path, delay));
            thread.start();
            logger.debug("Started main Thread for searching in: "+ path);
        }
        else {
            EventLogger eventLogger = EventLogger.getInstance();
            logger.debug("No word was given! Adding directory entries instead...");
            eventLogger.logEvent(SearchMain.class.getName(), "No word was given! Printing directory instead...");
            DirectoryStream<Path> files = Files.newDirectoryStream(path);
            for (Path path2 : files) {
                listWrapper.add(path2);
                if (first) break;
            }
        }
    }
}
