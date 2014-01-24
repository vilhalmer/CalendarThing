/** The internal representation of calendar data. **/

import java.util.HashMap;
import java.util.GregorianCalendar;

public class CalendarData
{
    HashMap<Integer, Event[]> eventsByDay;

    public CalendarData(String dataFilePath)
    {
        // import data or something
    }

    public int eventCountOnDate(GregorianCalendar date)
    /** @param date: Should be yyyymmdd **/
    {
        Event[] events = eventsByDay.get(date);
        if (events == null) return 0;

        return events.length;
    }

    public Event[] eventsOnDate(GregorianCalendar date)
    {
        return eventsByDay.get(date);
    }
}
