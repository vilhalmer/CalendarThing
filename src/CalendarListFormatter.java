/** Class for formatting a listing of events. **/

import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarListFormatter extends CalendarFormatter
{
    private boolean highlightToday = true;
    private boolean highlightTomorrow = true;
    private boolean numberEvents = false;
    private boolean showDescription = false;

    /**
     * Format the given Calendar into an event listing.
     *
     * @param aCalendar calendar date to be formatted
     *
     * @return a formatted String containing a list of events
     */
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
                if (this.highlightToday && currentDate.compareTo(today) == 0) {
                    out += " - Today";
                }
                else if (this.highlightTomorrow && currentDate.compareTo(tomorrow) == 0) {
                    out += " - Tomorrow";
                }
                out += String.format("%s\n", color(DEFAULT, DEFAULT, NORMAL));
            }

            for (Event event : aCalendar.eventsOnDate(currentDate)) {
                if (this.numberEvents) {
                    out += String.format("%2s)", String.valueOf(n));
                    ++n;
                }

                GregorianCalendar eventDate = event.getDate();
                int hour = eventDate.get(GregorianCalendar.HOUR);
                out += String.format(" %2s:%02d %s - %s\n", String.valueOf(((hour == 0) ? 12 : hour)),
                                                            eventDate.get(GregorianCalendar.MINUTE),
                                                            eventDate.getDisplayName(GregorianCalendar.AM_PM, GregorianCalendar.SHORT, Locale.getDefault()),
                                                            event.getTitle());

                if (this.showDescription) {
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

    /**
     * Set the highlight setting for the current day.
     *
     * @param shouldHighlight the formatter's highlight setting for the current day
     */
    public void setHighlightToday(boolean shouldHighlight)
    {
        highlightToday = shouldHighlight;
    }

    /**
     * Set the highlight setting for the next day.
     *
     * @param shouldHighlight the formatter's highlight setting for the next day
     */
    public void setHighlightTomorrow(boolean shouldHighlight)
    {
        highlightTomorrow = shouldHighlight;
    }

    /**
     * Set events to be formatted with numbers.
     *
     * @param shouldNumber used to set numbered output setting
     */
    public void setNumberEvents(boolean shouldNumber)
    {
        numberEvents = shouldNumber;
    }

    /**
     * Set events to output with descriptions
     *
     * @param shouldShow used to set showDescription setting
     */
    public void setShowDescription(boolean shouldShow)
    {
        showDescription = shouldShow;
    }

    public static void main(String[] args)
    {
        CalendarListFormatter formatter = new CalendarListFormatter();

        GregorianCalendar beginDate = new GregorianCalendar();
        GregorianCalendar endDate = new GregorianCalendar();
        endDate.add(GregorianCalendar.DAY_OF_MONTH, 1);

        formatter.setDateRange(beginDate, endDate);
        formatter.setNumberEvents(true);
        formatter.setShowDescription(true);

        CalendarData testData = new CalendarData("cal-data.cal");

        String testOutput = formatter.format(testData);

        System.out.println(testOutput);
    }
}
