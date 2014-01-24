public abstract class CalendarFormatter
{
    protected Calendar beginDate;
    protected Calendar endDate;

    public void setDateRange(Calendar aBeginDate, Calendar anEndDate)
    {
        if (anEndDate.before(aBeginDate)) {
            throw new InvalidArgumentException("Invalid date range");
        }

        beginDate = aBeginDate;
        endDate = anEndDate;
    }

    public String format(CalendarData aCalendar)
    {
        return null;
    }
}
