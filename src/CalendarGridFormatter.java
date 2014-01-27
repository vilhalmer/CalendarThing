import java.util.GregorianCalendar;

public class CalendarGridFormatter extends CalendarFormatter
{
    // ANSI color codes are 30 + N, 40 + N for background:
    public static final int RED     = 1;
    public static final int GREEN   = 2;
    public static final int YELLOW  = 3;
    public static final int BLUE    = 4;
    public static final int MAGENTA = 5;
    public static final int CYAN    = 6;

    private boolean colorize = true;
    private boolean bracketToday = true;

    public String format(CalendarData aCalendar)
    {
        String output = " Su  Mo  Tu  We  Th  Fr  Sa \n";

        // So, this formatter is always going to output at *least* a full month view. If the date range is smaller than that, it will grey out the hidden days.
        // First on the agenda is figuring out which day the specified month starts on:
        GregorianCalendar currentDate = (GregorianCalendar)this.beginDate.clone();
        currentDate.set(GregorianCalendar.DAY_OF_MONTH, 1);

        int firstDayColumn = currentDate.get(GregorianCalendar.DAY_OF_WEEK) - 1; // Minus one to put us at the zeroth column for Sunday.

        GregorianCalendar endOfMonth = (GregorianCalendar)currentDate.clone();
        endOfMonth.set(GregorianCalendar.DAY_OF_MONTH, endOfMonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
        System.out.println(currentDate.get(GregorianCalendar.DAY_OF_MONTH));

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

            if (currentDate.compareTo(this.beginDate) == 0) {
                output += String.format("[%02d]", currentDate.get(GregorianCalendar.DAY_OF_MONTH));
            }
            else {
                output += String.format(" %02d ", currentDate.get(GregorianCalendar.DAY_OF_MONTH));
            }
            currentDate.add(GregorianCalendar.DAY_OF_MONTH, 1);
        }

        return output;
    }

    public void setColorize(boolean shouldColorize)
    {
        colorize = shouldColorize;
    }

    public void setBracketToday(boolean shouldBracket)
    {
        bracketToday = shouldBracket;
    }

    private String color(int aCode)
    {
        if (aCode == -1) {
            return "\\x1b[0m";
        }

        return "\\x1b[3" + aCode + ";1m";
    }

    public static void main(String[] args)
    {
        CalendarGridFormatter formatter = new CalendarGridFormatter();
        formatter.setDateRange(new GregorianCalendar(), new GregorianCalendar());
        String testOutput = formatter.format(null);

        System.out.println(testOutput);
    }
}
