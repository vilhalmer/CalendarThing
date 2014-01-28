/** A single event on a Calendar **/

import java.util.GregorianCalendar;

public class Event implements Comparable<Event>
{
    // ANSI color codes are 30 + N, 40 + N for background:
    public static final int COLOR_RED     = 1;
    public static final int COLOR_GREEN   = 2;
    public static final int COLOR_YELLOW  = 3;
    public static final int COLOR_BLUE    = 4;
    public static final int COLOR_MAGENTA = 5;
    public static final int COLOR_CYAN    = 6;

    private GregorianCalendar date;
    private String title;
    private String description;
    private int color;

    public Event(GregorianCalendar aDate, String aTitle, String aDescription, int aColor)
    {
        date = aDate;
        title = aTitle;
        description = aDescription;
        color = aColor;
    }

    public Event(String storedString)
    {
        String[] components = storedString.split("\t");
        String[] dateComponents = components[0].split("-");
        String[] timeComponents = components[1].split(":");
        date = new GregorianCalendar(Integer.parseInt(dateComponents[0]), 
                                     Integer.parseInt(dateComponents[1]),
                                     Integer.parseInt(dateComponents[2]),
                                     Integer.parseInt(timeComponents[0]),
                                     Integer.parseInt(timeComponents[1]));
        title = components[2];
        description = components[3];
        color = Integer.parseInt(components[4]);
    }

    public GregorianCalendar getDate()
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

    public int compareTo(Event anObject)
    {
        return this.getDate().compareTo(anObject.getDate());
    }
}
