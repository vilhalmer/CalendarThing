import java.util.Calendar;

public abstract class CalendarFormatter
{
    protected Calendar beginDate;
    protected Calendar endDate;

    public void setDateRange(Calendar aBeginDate, Calendar anEndDate)
    {
        if (anEndDate.before(aBeginDate)) {
            throw new IllegalArgumentException("Invalid date range");
        }

        beginDate = aBeginDate;
        endDate = anEndDate;
    }

    public String format(CalendarData aCalendar)
    {
        return null;
    }
}
