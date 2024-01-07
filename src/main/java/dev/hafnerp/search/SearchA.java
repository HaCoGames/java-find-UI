package dev.hafnerp.search;

import dev.hafnerp.logger.EventLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

public class SearchA implements Runnable {

    private static final Logger logger = LogManager.getLogger(SearchA.class);

    private final EventLogger eventLogger;

    private final String eventLoggerPrefix;

    private final boolean first;

    private final String word;

    private final Path directory;

    private final int delay;

    private volatile boolean interrupted;

    private static final ListWrapper<Path> foundPaths = ListWrapper.getPathInstance();

    private static final ArrayList<Thread> allChildThreads = new ArrayList<>();

    private static int instanceCounter = 0;

    private static long instanceCounterAll = 0;

    public SearchA(boolean first, String word, Path directory, int delay) {
        instanceCounter++;
        instanceCounterAll++;
        this.first = first;
        this.word = word;
        this.directory = directory;
        this.delay = delay;
        eventLogger = EventLogger.getInstance();
        eventLoggerPrefix = this.getClass() + " - thread "+ instanceCounterAll;
    }

    public static void setInstanceCounterAll(long num) {
        instanceCounterAll = num;
    }

    @Override
    public void run() {
        Instant startTime = Instant.now();
        try {
            eventLogger.logEvent(eventLoggerPrefix, "Starting at: " + startTime);
            eventLogger.logEvent(eventLoggerPrefix, "Searching in path \"" + directory + "\"");
            interrupted = Thread.interrupted();
            logger.debug("Checking if the Thread is interrupted? "+ interrupted);
            File file = new File(String.valueOf(directory));
            if (file.isDirectory()) {
                DirectoryStream<Path> direct = Files.newDirectoryStream(directory);
                for (Path path : direct) {
                    interrupted = Thread.interrupted();

                    if (foundPaths.isFound() && !interrupted) break;
                    SearchA runnable;
                    Thread th;

                    runnable = new SearchA(first, word, path, delay);
                    th = new Thread(runnable);

                    allChildThreads.add(th);

                    th.start();
                    Thread.sleep(delay);
                }
            }
            else if (file.isFile()) {
                eventLogger.logEvent(eventLoggerPrefix, "Starting search algorithm at: " +Instant.now());
                Scanner scanner = new Scanner(file);
                boolean found = false;
                while (scanner.hasNextLine() && !found && !interrupted) {
                    String line = scanner.nextLine();
                    interrupted = Thread.interrupted();
                    found = (line.contains(word)) && !interrupted;
                }
                if (found) {
                    foundPaths.add(directory);
                    logger.debug("Found word in File: " + directory);
                    eventLogger.logEvent(eventLoggerPrefix, "-------------------Found word in File: " + directory);
                    if (first) {
                        foundPaths.setFound(true);
                        throw new InterruptedException("Word has been found.");
                    }
                }
                scanner.close();
                Instant endTime = Instant.now();
                eventLogger.logEvent(eventLoggerPrefix, "Finished search at: " + endTime);
                eventLogger.logEvent(eventLoggerPrefix, "Search lasted " + Duration.between(startTime, endTime).toMillis() + " milliseconds");
            }
        }
        catch (InterruptedException e) {
            new Thread(() -> allChildThreads.forEach(Thread::interrupt)).start();
            eventLogger.logEvent(eventLoggerPrefix, "Interrupted thread, word has been found.");
            logger.error(e);
            interrupted = true;
        }
        catch (Exception e) {
            logger.error(e);
        }

        Instant endTime = Instant.now();
        eventLogger.logEvent(eventLoggerPrefix, "Finishing at: " + Instant.now());
        eventLogger.logEvent(eventLoggerPrefix, "The thread lived " + Duration.between(startTime, endTime).toMillis() + " milliseconds");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (--instanceCounter <= 0 && foundPaths.getList().isEmpty()) {
            System.exit(2);
        }
        if (instanceCounter <= 0) {
            SearchA.setInstanceCounterAll(0);
        }
    }
}
