/** The internal representation of calendar data. **/

import java.util.HashMap;

public class CalendarData
{
    HashMap<Integer, Event[]> eventsByDay;

    public CalendarData(String dataFilePath)
    {
        // import data or something
    }

    public int eventCountOnDate(Calendar date)
    /** @param date: Should be yyyymmdd **/
    {
        Event[] events = eventsByDay.get(date);
        if (events == null) return 0;

        return events.length;
    }

    public Event[] eventsOnDate(Calendar date)
    {
        return eventsByDay.get(date);
    }
}
