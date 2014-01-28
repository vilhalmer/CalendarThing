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

    public void setDateRange(GregorianCalendar aBeginDate, GregorianCalendar anEndDate)
    {
        if (anEndDate.before(aBeginDate)) {
            throw new IllegalArgumentException("Invalid date range");
        }

        beginDate = boringDate(aBeginDate);
        endDate = boringDate(anEndDate);
    }

    public String format(CalendarData aCalendar)
    {
        return null;
    }

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

    protected String color(int foreground, int background, int mode)
    {
        return "\033[" + mode + ";3" + foreground + ";4" + background + "m";
    }
}
