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
                                     Integer.parseInt(dateComponents[1]) - 1, // YES, LET'S START NUMBERING THE DAYS AT ONE, BUT THE MONTHS AT ZERO. *BRILLIANT*.
                                     Integer.parseInt(dateComponents[2]),
                                     Integer.parseInt(timeComponents[0]),
                                     Integer.parseInt(timeComponents[1]));
        title = components[2];
        color = Integer.parseInt(components[3]);
        description = components[4];
    }

    /**
     * @return the event date
     */
    public GregorianCalendar getDate()
    {
        return date;
    }

    /**
     * @return the event tile
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @return the event description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @return the event color
     */
    public int getColor()
    {
        return color;
    }

    /**
     * Create and return a String representation of the event.
     *
     * @return String representation of the event
     */
    public String storedString()
    {
        GregorianCalendar currentDate = this.getDate();

        String out = currentDate.get(GregorianCalendar.YEAR) + "-" + (currentDate.get(GregorianCalendar.MONTH) + 1) + "-" + currentDate.get(GregorianCalendar.DAY_OF_MONTH) + "\t";
        out += currentDate.get(GregorianCalendar.HOUR_OF_DAY) + ":" + currentDate.get(GregorianCalendar.MINUTE) + "\t";
        out += this.getTitle() + "\t";
        out += this.getColor() + "\t";
        out += this.getDescription();
        out += "\t\t";

        return out;
    }

    /**
     * Compare this instance of Event with another.
     *
     * @param anObject another object to compare to
     *
     * @return int comparison of both objects
     */
    public int compareTo(Event anObject)
    {
        int maybeSame = this.getDate().compareTo(anObject.getDate());

        if (maybeSame == 0) {
            return this.getTitle().compareTo(anObject.getTitle());
        }
        else {
            return maybeSame;
        }
    }
}
