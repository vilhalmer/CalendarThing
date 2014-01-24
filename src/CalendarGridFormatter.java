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
        String output;

        // So, this formatter is always going to output at *least* a full month view. If the date range is smaller than that, it will grey out the hidden days.
        // First on the agenda is figuring out which day the specified month starts on:
        int firstDay = GregorianCalendar(aCalendar.get(Calendar.YEAR), aCalendar.get(Calendar.MONTH), 1).get(Calendar.DAY_OF_WEEK) - 1; // Minus one to put us at the zeroth column for Sunday.
        
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
        if (code = -1) {
            return "\x1b[0m";
        }

        return "\x1b[3" + code + ";1m";
    }
}
