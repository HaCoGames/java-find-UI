package dev.hafnerp.search;

import dev.hafnerp.logger.PathLogger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListMonitor {
    private final List<Path> list;

    private static boolean found;

    private static ListMonitor listWrapper;

    private final PathLogger pathLogger;

    public ListMonitor() {
        pathLogger = PathLogger.getInstance();
        this.list = new LinkedList<>();
    }

    public static synchronized ListMonitor getPathInstance() {
        if (listWrapper == null) listWrapper = new ListMonitor();
        return listWrapper;
    }

    public synchronized void add(Path element) {
        pathLogger.logPath(element);
        list.add(element);
    }

    public synchronized void setFound(boolean found) {
        ListMonitor.found = found;
    }

    public synchronized boolean isFound() {
        return found;
    }

    public synchronized List<Path> getList() {
        return new ArrayList<>(list);
    }

    public synchronized void reset() {
        list.clear();
        found = false;
    }

    public String toString() {
        StringBuilder ret = new StringBuilder();
        int elementCounter = 0;
        for (Path element : list) ret.append(elementCounter++ < 1 ? "" : "\n").append(element.toString());
        return ret.toString();
    }
}
