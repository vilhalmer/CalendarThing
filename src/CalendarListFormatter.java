import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.List;
import java.util.ArrayList;

public class CalendarListFormatter extends CalendarFormatter
{
    private boolean hilightToday = true;
    private boolean hilightTomorrow = true;
    private boolean numberEvents = false;
    private boolean showDescripton = false;
    private List<Event> eventListing = new ArrayList();
    
    @Override
    public String format(CalendarData aCalendar)
    {
        GregorianCalendar currentDate = (GregorianCalendar)beginDate.clone();
        GregorianCalendar today = boringDate(new GregorianCalendar());
        GregorianCalendar tomorrow = boringDate(new GregorianCalendar());
        tomorrow.add(GregorianCalendar.DAY_OF_MONTH, 1);

        String out = "";
        int n = 1;
        while (currentDate.compareTo(endDate) <= 0) {
            if (aCalendar.eventCountOnDate(currentDate) > 0) {
                out += String.format("%s%s %d", color(DEFAULT, DEFAULT, BOLD),
                                                currentDate.getDisplayName(GregorianCalendar.MONTH, GregorianCalendar.LONG, Locale.getDefault()),
                                                currentDate.get(GregorianCalendar.DAY_OF_MONTH));
                if (this.hilightToday && currentDate.compareTo(today) == 0) {
                    out += " - Today";
                }
                else if (this.hilightTomorrow && currentDate.compareTo(tomorrow) == 0) {
                    out += " - Tomorrow";
                }
                out += String.format("%s\n", color(DEFAULT, DEFAULT, NORMAL));
            }

            for (Event event : aCalendar.eventsOnDate(currentDate)) {
                if (this.numberEvents) {
                    eventListing.add(event);
                    out += String.format("%2s)", String.valueOf(n));
                    ++n;
                }

                GregorianCalendar eventDate = event.getDate();
                int hour = eventDate.get(GregorianCalendar.HOUR);
                out += String.format(" %2s:%02d %s - %s\n", String.valueOf(((hour == 0) ? 12 : hour)),
                                                            eventDate.get(GregorianCalendar.MINUTE),
                                                            eventDate.getDisplayName(GregorianCalendar.AM_PM, GregorianCalendar.SHORT, Locale.getDefault()),
                                                            event.getTitle());

                if (this.showDescripton) {
                    for (String line : event.getDescription().split("\n")) {
                        if (this.numberEvents) {
                            out += "   "; // Extra indentation to make up for the number.
                        }
                        out += "    " + line + "\n";
                    }
                }
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

    public void setNumberEvents(boolean shouldNumber)
    {
        numberEvents = shouldNumber;
    }

    public void setShowDescription(boolean shouldShow)
    {
        showDescripton = shouldShow;
    }

    public List<Event> getEventListing() { return eventListing; }

    public static void main(String[] args)
    {
        CalendarListFormatter formatter = new CalendarListFormatter();

        GregorianCalendar beginDate = new GregorianCalendar();
        GregorianCalendar endDate = new GregorianCalendar();
        endDate.add(GregorianCalendar.DAY_OF_MONTH, 1);

        formatter.setDateRange(beginDate, endDate);
        formatter.setNumberEvents(true);
        formatter.setShowDescription(true);

        CalendarData testData = new CalendarData("test-data.cal");

        String testOutput = formatter.format(testData);

        System.out.println(testOutput);
    }
}
