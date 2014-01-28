import java.util.GregorianCalendar;
import java.util.Locale;

public class CalendarGridFormatter extends CalendarFormatter
{
    private boolean colorize = true;
    private boolean hilightToday = true;

    public String format(CalendarData aCalendar)
    {
        String output = " Su  Mo  Tu  We  Th  Fr  Sa \n";
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
        int monthNamePadding = (output.length() / 2) + (monthName.length() / 2);
        System.out.printf("%" + monthNamePadding + "s\n", monthName);

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
            if (this.colorize) {
                if (currentDate.compareTo(beginDate) < 0 || currentDate.compareTo(endDate) > 0) {
                    fgColor = BLACK;
                }
                else if (aCalendar.eventCountOnDate(currentDate) > 6) {
                    fgColor = RED;
                }
                else if (aCalendar.eventCountOnDate(currentDate) > 3) {
                    fgColor = YELLOW;
                }
                else if (aCalendar.eventCountOnDate(currentDate) > 0) {
                    fgColor = GREEN;
                }
            }

            if (currentDate.compareTo(today) == 0 && this.hilightToday) {
                //output += String.format("%s[%2s]%s", color(fgColor), String.valueOf(currentDate.get(GregorianCalendar.DAY_OF_MONTH)), color(DEFAULT));
                mode = REVERSE;
            }

            output += String.format("%s %2s %s", color(fgColor, bgColor, mode), String.valueOf(currentDate.get(GregorianCalendar.DAY_OF_MONTH)), color(DEFAULT, DEFAULT, NORMAL));
            currentDate.add(GregorianCalendar.DAY_OF_MONTH, 1);
        }

        return output;
    }

    public void setColorize(boolean shouldColorize)
    {
        colorize = shouldColorize;
    }

    public void setHilightToday(boolean shouldHilight)
    {
        hilightToday = shouldHilight;
    }

    public static void main(String[] args)
    {
        CalendarGridFormatter formatter = new CalendarGridFormatter();

        GregorianCalendar beginDate = new GregorianCalendar();
        beginDate.set(GregorianCalendar.DAY_OF_MONTH, 1);
        GregorianCalendar endDate = new GregorianCalendar();
        endDate.set(GregorianCalendar.DAY_OF_MONTH, endDate.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));

        formatter.setDateRange(beginDate, endDate);

        CalendarData testData = new CalendarData("../test-data.cal");

        String testOutput = formatter.format(testData);

        System.out.println(testOutput);
    }
}
