import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarListFormatter extends CalendarFormatter
{
    private boolean hilightToday = true;
    private boolean hilightTomorrow = true;
    
    @Override
    public String format(CalendarData aCalendar)
    {
        GregorianCalendar currentDate = (GregorianCalendar)beginDate.clone();
        GregorianCalendar today = boringDate(new GregorianCalendar());
        GregorianCalendar tomorrow = boringDate(new GregorianCalendar());
        tomorrow.add(GregorianCalendar.DAY_OF_MONTH, 1);

        String out = "";
        while (currentDate.compareTo(endDate) <= 0) {
            if (aCalendar.eventCountOnDate(currentDate) > 0) {
                out += String.format("%s%s %d", color(DEFAULT, DEFAULT, BOLD),
                                                currentDate.getDisplayName(GregorianCalendar.MONTH, GregorianCalendar.LONG, Locale.getDefault()),
                                                currentDate.get(GregorianCalendar.DAY_OF_MONTH));
                if (currentDate.compareTo(today) == 0) {
                    out += " - Today";
                }
                else if (currentDate.compareTo(tomorrow) == 0) {
                    out += " - Tomorrow";
                }
                out += String.format("%s\n", color(DEFAULT, DEFAULT, NORMAL));
            }

            for (Event event : aCalendar.eventsOnDate(currentDate)) {
                out += event.displayString();
            }

            currentDate.add(GregorianCalendar.DAY_OF_MONTH, 1);
        }

        return out;
    }

    public void setHilightToday(boolean shouldHilight)
    {
        hilightToday = shouldHilight;
    }

    public void setHilightTomorrow(boolean shouldHilight)
    {
        hilightTomorrow = shouldHilight;
    }

    public static void main(String[] args)
    {
        CalendarListFormatter formatter = new CalendarListFormatter();

        GregorianCalendar beginDate = new GregorianCalendar();
        GregorianCalendar endDate = new GregorianCalendar();
        endDate.add(GregorianCalendar.DAY_OF_MONTH, 1);

        formatter.setDateRange(beginDate, endDate);

        CalendarData testData = new CalendarData("../test-data.cal");

        String testOutput = formatter.format(testData);

        System.out.println(testOutput);
    }
}
