package Learn.modloader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

// Define the ModEventHandler class
public class ModEventHandler {
    // Built-in events and their listeners
    private final List<BuiltInEvent> builtInEvents = new ArrayList<>();

    // Map to store custom events and their listeners
    private final Map<String, List<Consumer<String>>> customEvents = new HashMap<>();

    public ModEventHandler() {
        // Initialize the built-in event listener for "onModLoaded"
        addBuiltInEvent("onModLoaded", this::defaultOnModLoaded);
    }

    // Method to add a built-in event and its listener
    public void addBuiltInEvent(String eventName, Consumer<String> listener) {
        builtInEvents.add(new BuiltInEvent(eventName, listener));
    }

    // Method to add a listener for a custom event
    public void addEventListener(String eventName, Consumer<String> listener) {
        customEvents.computeIfAbsent(eventName, k -> new ArrayList<>()).add(listener);
    }

    // Method to remove a listener for a custom event
    public void removeEventListener(String eventName, Consumer<String> listener) {
        List<Consumer<String>> listeners = customEvents.get(eventName);
        if (listeners != null) {
            listeners.remove(listener);
            if (listeners.isEmpty()) {
                customEvents.remove(eventName);
            }
        }
    }

    // Method to trigger an event
    public void triggerEvent(String eventName, String eventData) {
        // Trigger built-in events if they exist
        for (BuiltInEvent event : builtInEvents) {
            if (event.getName().equals(eventName)) {
                event.getListener().accept(eventData);
            }
        }

        // Trigger custom events if they exist
        List<Consumer<String>> listeners = customEvents.get(eventName);
        if (listeners != null) {
            for (Consumer<String> listener : listeners) {
                listener.accept(eventData);
            }
        }
    }

    // Default action for the built-in "onModLoaded" event
    private void defaultOnModLoaded(String modid) {
        System.out.println("Default handler for mod loaded event. Mod ID: " + modid);
    }

    // Inner class to represent a built-in event
    private static class BuiltInEvent {
        private final String name;
        private final Consumer<String> listener;

        public BuiltInEvent(String name, Consumer<String> listener) {
            this.name = name;
            this.listener = listener;
        }

        public String getName() {
            return name;
        }

        public Consumer<String> getListener() {
            return listener;
        }
    }
}
