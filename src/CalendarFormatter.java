/** Super class for Calendar formatting. **/

import java.util.GregorianCalendar;

public abstract class CalendarFormatter
{
    // ANSI color codes are 30 + N, 40 + N for background:
    protected static final int BLACK   = 0;
    protected static final int RED     = 1;
    protected static final int GREEN   = 2;
    protected static final int YELLOW  = 3;
    protected static final int BLUE    = 4;
    protected static final int MAGENTA = 5;
    protected static final int CYAN    = 6;
    protected static final int DEFAULT = 9;

    protected static final int NORMAL  = 0;
    protected static final int BOLD    = 1;
    protected static final int REVERSE = 7;

    protected GregorianCalendar beginDate;
    protected GregorianCalendar endDate;

    /**
     * Set the range to format
     *
     * @param aBeginDate the beginning of the range
     * @param anEndDate the end of the range
     */
    public void setDateRange(GregorianCalendar aBeginDate, GregorianCalendar anEndDate)
    {
        if (anEndDate.before(aBeginDate)) {
            throw new IllegalArgumentException("Invalid date range");
        }

        beginDate = boringDate(aBeginDate);
        endDate = boringDate(anEndDate);
    }

    /**
     * Placeholder for subclasses
     *
     * @param aCalendar calendar to be formatted
     *
     * @return formatted String representation
     */
    public String format(CalendarData aCalendar)
    {
        return null;
    }

    /**
     * Clear unnecessary field portions in GregorianCalendar
     *
     * @param aDate date to be cleared
     *
     * @return the new cleared date
     */
    protected GregorianCalendar boringDate(GregorianCalendar aDate)
    {
        GregorianCalendar newDate = (GregorianCalendar)aDate.clone();
        newDate.set(GregorianCalendar.HOUR_OF_DAY, 0);
        newDate.clear(GregorianCalendar.HOUR);
        newDate.clear(GregorianCalendar.MINUTE);
        newDate.clear(GregorianCalendar.SECOND);
        newDate.clear(GregorianCalendar.MILLISECOND);
        
        return newDate;
    }

    /**
     * Creates a linux code for color.
     *
     * @param foreground foreground color
     * @param  background background color
     * @param modes other parameters
     */
    protected String color(int foreground, int background, int ... modes)
    {
        String output = "\033[";
        for (int mode : modes) {
            output += mode + ";";
        }
        output += "3" + foreground + ";4" + background + "m";

        return output;
    }
}
