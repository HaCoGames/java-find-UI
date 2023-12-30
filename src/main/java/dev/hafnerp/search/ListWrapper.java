package dev.hafnerp.search;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListWrapper<Type> {
    private final List<Type> list;

    private static boolean found;

    private static ListWrapper<?> listWrapper;

    public ListWrapper() {
        this.list = new LinkedList<>();
    }

    public static synchronized ListWrapper<Path> getPathInstance() {
        if (listWrapper == null) listWrapper = new ListWrapper<Path>();
        return (ListWrapper<Path>) listWrapper;
    }

    public synchronized void add(Type element) {
        list.add(element);
    }

    public synchronized void setFound(boolean found) {
        ListWrapper.found = found;
    }

    public synchronized boolean isFound() {
        return found;
    }

    public synchronized List<Type> getList() {
        return new ArrayList<>(list);
    }

    public synchronized void resetVariables() {
        list.clear();
        found = false;
    }

    public String toString() {
        StringBuilder ret = new StringBuilder();
        int elementCounter = 0;
        for (Type element : list) ret.append(elementCounter++ < 1 ? "" : "\n").append(element.toString());
        return ret.toString();
    }
}
