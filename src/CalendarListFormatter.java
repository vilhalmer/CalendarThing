public class CalendarListFormatter extends CalendarFormatter
{
    private boolean showToday = true;
    private boolean showTomorrow = true;
    
    @Override
    public String format(CalendarData aCalendar)
    {
        
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
