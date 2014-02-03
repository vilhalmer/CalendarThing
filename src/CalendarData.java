/** The internal representation of calendar data. **/

import java.util.*;
import java.io.*;

public class CalendarData
{
    HashMap<String, SortedSet<Event>> eventsByDay;

    /**
	 * Read all event data from a specified file.
	 * 
	 * @param dataFilePath data file to be read in
	 */
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
                        this.addEvent(new Event(currentEvent));
                        currentEvent = "";
                }
                else
                    currentEvent += "\n";
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

    /**
	 * Retrieves event listing from the eventsByDay map and outputs its size.
	 * 
	 * @param date the day to have its events counted
	 * 
	 * @return the number of events for that day
	 */
    public int eventCountOnDate(GregorianCalendar date)
    {
        SortedSet<Event> events = eventsByDay.get(dateString(date));
        if (events == null) return 0;

        return events.size();
    }

    /**
	 * Retrieves event listing from the eventsByDay map and returns it.
	 * 
	 * @param date the day events are being given for
	 * 
	 * @return the events for the input day
	 */
    public SortedSet<Event> eventsOnDate(GregorianCalendar date)
    {
        SortedSet<Event> events = eventsByDay.get(dateString(date));
        if (events == null) {
            return new TreeSet<Event>(); // Return an empty collection instead of null.
        }

        return events;
    }

    /**
	 * Add a new event to the event listing for the day given within it.
	 * 
	 * @param newEvent the new event to be added
	 */
    public void addEvent(Event newEvent)
    {
        SortedSet<Event> events = eventsByDay.get(dateString(newEvent.getDate()));
        if (events == null) { // Make a set if there isn't one for this date already.
            events = new TreeSet<Event>();
            eventsByDay.put(dateString(newEvent.getDate()), events);
        }
        
        events.add(newEvent);
    }

    /**
	 * Remove an event from the day it's listed in.
	 * 
	 * @param anEvent the event to be removed
	 */
    public void removeEvent(Event anEvent)
    {
        eventsByDay.get(dateString(anEvent.getDate())).remove(anEvent);
    }

    /**
     * Write current running data into the given file.
     *
     * @param dataFilePath the file to write to
     */
    public void writeDataToFile(String dataFilePath)
    {
        try {
        	PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(dataFilePath)));
            for(String s : eventsByDay.keySet()) {
                for (Event e : eventsByDay.get(s)) {
                    out.println(e.getDate().get(GregorianCalendar.YEAR) + "-" + (e.getDate().get(GregorianCalendar.MONTH)+ 1) + "-" +
                            e.getDate().get(GregorianCalendar.DAY_OF_MONTH) + "\t" + e.getDate().get(GregorianCalendar.HOUR_OF_DAY) + ":" +
                            e.getDate().get(GregorianCalendar.MINUTE) + "\t" + e.getTitle() + "\t" + e.getColor() + "\t" + e.getDescription() + "\t\t");
                }
            }
            out.flush();
            out.close();
        }
        catch (FileNotFoundException e) { // What even is error handling
            System.err.println("output file cannot be created/modified");
            System.exit(0);
        }
        catch (IOException e) {
            System.err.println("cannot output to file");
            System.exit(0);
        }
    }

    /**
	 * Converts a GregorianCalendar into a date string for use.
	 * 
	 * @param aDate the Calendar to be converted
	 * 
	 * @return a string representation of the input Calendar
	 */
    private String dateString(GregorianCalendar aDate)
    {
        return String.format("%d-%2d-%2d", aDate.get(GregorianCalendar.YEAR), aDate.get(GregorianCalendar.MONTH), aDate.get(GregorianCalendar.DAY_OF_MONTH));
    }

    public static void main(String[] args)
    {
        CalendarData data = new CalendarData("cal-data.cal");

        data.writeDataToFile("cal-data.cal");
    }
}
