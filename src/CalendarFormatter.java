import java.util.GregorianCalendar;

public abstract class CalendarFormatter
{
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
}
