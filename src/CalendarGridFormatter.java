import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarGridFormatter extends CalendarFormatter
{
    private boolean colorize = true;
    private boolean highlightToday = true;

    /**
     * Format the given Calendar into a month view.
     *
     * @param aCalendar calendar date to be formatted
     *
     * @return a formatted String containing view of the month
     */
    public String format(CalendarData aCalendar)
    {
        String output = "";
        String header = " Su  Mo  Tu  We  Th  Fr  Sa \n";
        // Need an object to find today, without time:
        GregorianCalendar today = boringDate(new GregorianCalendar());

        // So, this formatter is always going to output at *least* a full month view. If the date range is smaller than that, it will grey out the hidden days.
        // First on the agenda is figuring out which day the specified month starts on:
        GregorianCalendar currentDate = (GregorianCalendar)this.beginDate.clone();
        currentDate.set(GregorianCalendar.DAY_OF_MONTH, 1);

        int firstDayColumn = currentDate.get(GregorianCalendar.DAY_OF_WEEK) - 1; // Minus one to put us at the zeroth column for Sunday.

        GregorianCalendar endOfMonth = (GregorianCalendar)currentDate.clone();
        endOfMonth.set(GregorianCalendar.DAY_OF_MONTH, endOfMonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));

        String monthName = currentDate.getDisplayName(GregorianCalendar.MONTH, GregorianCalendar.LONG, Locale.getDefault());
        monthName += " " + currentDate.get(GregorianCalendar.YEAR);
        int monthNamePadding = (header.length() / 2) + (monthName.length() / 2);
        output += color(DEFAULT, DEFAULT, BOLD) + String.format("%" + monthNamePadding + "s\n", monthName) + color(DEFAULT, DEFAULT, BOLD);

        output += color(DEFAULT, DEFAULT, REVERSE) + header + color(DEFAULT, DEFAULT, NORMAL);

        // Pad to the first column with empty space:
        for (int i = 0; i < firstDayColumn; ++i) {
            output += "    ";
        }

        // Now fill in all of the day numbers, colorizing as we go:
        while (currentDate.compareTo(endOfMonth) <= 0) {
            int col = currentDate.get(GregorianCalendar.DAY_OF_MONTH) - 1 + firstDayColumn;

            if (col % 7 == 0) {
                output += "\n"; // Line break before each Sunday.
            }
            
            int fgColor = DEFAULT;
            int bgColor = DEFAULT;
            int mode = NORMAL;
            String color = "";
            if (this.colorize) {
                if (currentDate.compareTo(beginDate) < 0 || currentDate.compareTo(endDate) > 0) {
                    fgColor = BLACK;
                }
                else if (aCalendar.eventCountOnDate(currentDate) > 6) {
                    fgColor = RED;
                    mode = BOLD;
                }
                else if (aCalendar.eventCountOnDate(currentDate) > 3) {
                    fgColor = YELLOW;
                    mode = BOLD;
                }
                else if (aCalendar.eventCountOnDate(currentDate) > 0) {
                    fgColor = GREEN;
                    mode = BOLD;
                }

                if (currentDate.compareTo(today) == 0 && this.highlightToday) {
                    //output += String.format("%s[%2s]%s", color(fgColor), String.valueOf(currentDate.get(GregorianCalendar.DAY_OF_MONTH)), color(DEFAULT));
                    color = color(fgColor, bgColor, mode, REVERSE);
                }
                else {
                    color = color(fgColor, bgColor, mode);
                }
            }

            output += String.format("%s %2s %s", color, String.valueOf(currentDate.get(GregorianCalendar.DAY_OF_MONTH)), color(DEFAULT, DEFAULT, NORMAL));
            currentDate.add(GregorianCalendar.DAY_OF_MONTH, 1);
        }

        return output;
    }

    /**
     * Set whether days should be colored.
     *
     * @param shouldColorize value to set the colorize setting
     */
    public void setColorize(boolean shouldColorize)
    {
        colorize = shouldColorize;
    }

    /**
     * Set whether the current day should be highlighted
     *
     * @param shouldHighlight value to set the highlight setting
     */
    public void setHighlightToday(boolean shouldHighlight)
    {
        highlightToday = shouldHighlight;
    }

    public static void main(String[] args)
    {
        CalendarGridFormatter formatter = new CalendarGridFormatter();

        GregorianCalendar beginDate = new GregorianCalendar();
        beginDate.set(GregorianCalendar.DAY_OF_MONTH, 1);
        GregorianCalendar endDate = new GregorianCalendar();
        endDate.set(GregorianCalendar.DAY_OF_MONTH, endDate.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));

        formatter.setDateRange(beginDate, endDate);

        CalendarData testData = new CalendarData("cal-data.cal");

        String testOutput = formatter.format(testData);

        System.out.println(testOutput);
    }
}
