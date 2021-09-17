package World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventHandler {
    private List<String> events;

    // Konstruktor
    public EventHandler(){
        events = new ArrayList<>();
    }

    // Dodanie eventu
    public void addEvent(String event){
        events.add(event);
    }

    // Wypisanie eventow
    public String printEvents(){
        String output = "<html>Eventy: <br>";
        for (int i = 0; i < events.size(); i++){
            output = output + events.get(i) + "<br>";
        }
        output = output + "</html>";
        events.clear();
        return output;
    }

    // Zwraca eventy
    public List<String> getEvents(){
        return events;
    }

}
