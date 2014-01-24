import java.util.GregorianCalendar;

public class CalendarListFormatter extends CalendarFormatter
{
    private boolean showToday = true;
    private boolean showTomorrow = true;
    
    @Override
    public String format(CalendarData aCalendar)
    {
        return null;
    }

    public void setShowToday(boolean shouldShow)
    {
        showToday = shouldShow;
    }

    public void setShowTomorrow(boolean shouldShow)
    {
        showTomorrow = shouldShow;
    }
}
