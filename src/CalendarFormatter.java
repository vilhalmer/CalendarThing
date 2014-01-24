<<<<<<< HEAD
import java.util.GregorianCalendar;
>>>>>>> d292ef723ec1c2cd3e4a61d03097ac295de40e3f

public abstract class CalendarFormatter
{
    protected GregorianCalendar beginDate;
    protected GregorianCalendar endDate;

    public void setDateRange(GregorianCalendar aBeginDate, GregorianCalendar anEndDate)
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
