/** A single event on a Calendar **/

import java.util.Calendar;

public class Event
{
    // ANSI color codes are 30 + N, 40 + N for background:
    public static final int COLOR_RED     = 1;
    public static final int COLOR_GREEN   = 2;
    public static final int COLOR_YELLOW  = 3;
    public static final int COLOR_BLUE    = 4;
    public static final int COLOR_MAGENTA = 5;
    public static final int COLOR_CYAN    = 6;

    private Calendar date;
    private String title;
    private String description;

    public Event(Calendar aDate, String aTitle, String aDescription, int aColor)
    {
        date = aDate;
        title = aTitle;
        description = aDescription;
    }

    public Event(String storedString)
    {
        
    }

    public Calendar getDate()
    {
        return date;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public String displayString()
    {
        //return time + ": " + title + ": " + description;
        return null;
    }
    
    public String storedString()
    {
        //return date + "\t" + time + "\t" + title + "\t" + description + "\t" + color;
        return null;
    }
}
