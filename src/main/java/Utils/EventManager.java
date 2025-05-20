package Utils;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private static EventManager instance;
    private final List<TableRefreshListener> listeners = new ArrayList<>();

    private EventManager() {}

    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    public void registerListener(TableRefreshListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(TableRefreshListener listener) {
        listeners.remove(listener);
    }

    public void notifyListeners() {
        for (TableRefreshListener listener : listeners) {
            listener.refreshTable();
        }
    }
}