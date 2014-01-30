/** The internal representation of calendar data. **/

import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import java.io.*;
import java.util.GregorianCalendar;

public class CalendarData
{
    HashMap<String, SortedSet<Event>> eventsByDay;

    public CalendarData(String dataFilePath)
    {
        eventsByDay = new HashMap<String, SortedSet<Event>>();

        try {
            BufferedReader in = new BufferedReader(new FileReader(dataFilePath));

            String currentEvent = "";
            String currentLine;
            while ((currentLine = in.readLine()) != null) {
                currentEvent += currentLine;
                if (currentLine.endsWith("\t\t")) {
                    this.addEvent(new Event(currentLine));
                    currentEvent = "";
                }
            }
        }
        catch (FileNotFoundException e) { // What even is error handling
            System.out.println("derp, invalid data file");
            System.exit(0);
        }
        catch (IOException e) {
            System.out.println("welp");
            System.exit(0);
        }
    }

    public int eventCountOnDate(GregorianCalendar date)
    {
        SortedSet<Event> events = eventsByDay.get(dateString(date));
        if (events == null) return 0;

        return events.size();
    }

    public SortedSet<Event> eventsOnDate(GregorianCalendar date)
    {
        SortedSet<Event> events = eventsByDay.get(dateString(date));
        if (events == null) {
            return new TreeSet<Event>(); // Return an empty collection instead of null.
        }

        return events;
    }

    public void addEvent(Event newEvent)
    {
        SortedSet<Event> events = eventsByDay.get(dateString(newEvent.getDate()));
        if (events == null) { // Make a set if there isn't one for this date already.
            events = new TreeSet<Event>();
            eventsByDay.put(dateString(newEvent.getDate()), events);
        }
        
        events.add(newEvent);
    }

    public void removeEvent(Event anEvent)
    {
        eventsByDay.get(dateString(anEvent.getDate())).remove(anEvent);
    }

    public void writeDataToFile(String dataFilePath)
    {
        
    }

    private String dateString(GregorianCalendar aDate)
    {
        return String.format("%d-%2d-%2d", aDate.get(GregorianCalendar.YEAR), aDate.get(GregorianCalendar.MONTH), aDate.get(GregorianCalendar.DAY_OF_MONTH));
    }
}
