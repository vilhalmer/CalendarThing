/** Calendar input and output functions **/

import java.util.GregorianCalendar;
import java.util.Scanner;

public class CalendarIO {

    /**
     * Prompt user for input and and display events within the given date range
     *
     * @param args passed in from main
     */
    public void display(String[] args) {

        CalendarData data = new CalendarData("cal-data.cal");
        CalendarListFormatter listFormatter = new CalendarListFormatter();
        CalendarGridFormatter gridFormatter = new CalendarGridFormatter();
        String[] dateComponents;
        Scanner read = new Scanner(System.in);
        GregorianCalendar beginDate = null;
        GregorianCalendar endDate = null;
        String startString;
        String endString;

        // assign input/use default input based on args
        if (args.length == 0) {
            beginDate = new GregorianCalendar();
            startString = beginDate.get(GregorianCalendar.YEAR) + "-" + (beginDate.get(GregorianCalendar.MONTH) + 1)
                    + "-" + beginDate.get(GregorianCalendar.DAY_OF_MONTH);
            endDate = new GregorianCalendar(beginDate.get(GregorianCalendar.YEAR),
                    beginDate.get(GregorianCalendar.MONTH), beginDate.get(GregorianCalendar.DAY_OF_MONTH) + 1);
            endString = endDate.get(GregorianCalendar.YEAR) + "-" + (endDate.get(GregorianCalendar.MONTH) + 1)
                    + "-" + endDate.get(GregorianCalendar.DAY_OF_MONTH);
        }
        else if (args.length == 3) {
            startString = args[1];
            endString = args[2];
        }
        else {
            System.out.println("Enter start date (yyyy-mm-dd): ");
            startString = read.nextLine();
            System.out.println("Enter end date (yyyy-mm-dd): ");
            endString = read.nextLine();
        }

        // attempt to create Calendars based on the input date for month grid
        try {
            dateComponents = startString.split("-");
            beginDate = new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                    Integer.parseInt(dateComponents[1]) - 1,
                    Integer.parseInt(dateComponents[2]));
            beginDate.set(GregorianCalendar.DAY_OF_MONTH, 1);

            endDate = new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                    Integer.parseInt(dateComponents[1]) - 1,
                    Integer.parseInt(dateComponents[2]));
            endDate.set(GregorianCalendar.DAY_OF_MONTH, endDate.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
        }
        catch (Exception e) { System.out.println("Malformed date, incorrect user input."); System.exit(0); }

        // format and output calendar grid
        gridFormatter.setDateRange(beginDate, endDate);
        String output = gridFormatter.format(data);
        System.out.println("\n" + output);

        // format and output event listing
        dateComponents = startString.split("-");
        beginDate = new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                Integer.parseInt(dateComponents[1]) - 1,
                Integer.parseInt(dateComponents[2]));

        dateComponents = endString.split("-");
        endDate = new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                Integer.parseInt(dateComponents[1]) - 1,
                Integer.parseInt(dateComponents[2]));

        listFormatter.setDateRange(beginDate, endDate);
        listFormatter.setShowDescription(true);

        output = listFormatter.format(data);
        System.out.println("\n" + output);
        read.close();
    }

    /**
     * Prompt user for input and add an event to the calendar data file
     *
     * @param args passed in from main
     */
    public void add(String[] args) {
        CalendarData data = new CalendarData("cal-data.cal");
        Scanner read = new Scanner(System.in);
        String eventString = "";

        switch (args.length) {
            case 1:
                System.out.println("Enter date (yyyy-mm-dd): ");
                eventString += read.nextLine() + "\t";
                System.out.println("Enter time (hh:mm): ");
                eventString += read.nextLine() + "\t";
                System.out.println("Enter event title: ");
                eventString += read.nextLine() + "\t" + "-1" + "\t";
                System.out.println("Enter event description: ");
                eventString += read.nextLine() + "\t\t";
                break;
            case 2:
                eventString += args[1] + "\t";
                System.out.println("Enter time (hh:mm): ");
                eventString += read.nextLine() + "\t";
                System.out.println("Enter event title: ");
                eventString += read.nextLine() + "\t" + "-1" + "\t";
                System.out.println("Enter event description: ");
                eventString += read.nextLine() + "\t\t";
                break;
            case 3:
                eventString += args[1] + "\t" + args[2] + "\t";
                System.out.println("Enter event title: ");
                eventString += read.nextLine() + "\t" + "-1" + "\t";
                System.out.println("Enter event description: ");
                eventString += read.nextLine() + "\t\t";
                break;
        }

        try { data.addEvent(new Event(eventString)); }
        catch (Exception e) { System.out.println("Malformed event, incorrect user input."); System.exit(0); }
        data.writeDataToFile("cal-data.cal");
        read.close();
    }

    /**
     * Prompt user for input and remove an event from the calendar data file
     *
     * @param args passed in from main
     */
    public void remove(String[] args) {

        CalendarData data = new CalendarData("cal-data.cal");
        CalendarListFormatter formatter = new CalendarListFormatter();
        Scanner read = new Scanner(System.in);
        GregorianCalendar beginDate = null;
        GregorianCalendar endDate = null;
        String[] dateComponents = new String[3];
        String date;

        if (args.length != 2) {
            System.out.println("Enter date (yyyy-mm-dd): ");
            date = read.nextLine();
        }
        else
            date = args[1];


        try {
            dateComponents = date.split("-");
            beginDate = new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                    Integer.parseInt(dateComponents[1]) - 1,
                    Integer.parseInt(dateComponents[2]));
            endDate = new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                    Integer.parseInt(dateComponents[1]) - 1,
                    Integer.parseInt(dateComponents[2]));
        }
        catch (Exception e) { System.out.println("Malformed date, incorrect user input."); System.exit(0); }

        formatter.setDateRange(beginDate, endDate);
        formatter.setNumberEvents(true);
        formatter.setShowDescription(true);

        String output = formatter.format(data);
        System.out.println(output);

        System.out.println("Event to remove: ");
        int eventNum = 0;
        try { eventNum = Integer.parseInt(read.nextLine()) - 1; }
        catch (Exception e) { System.out.println("Type error, incorrect user input."); System.exit(0); }

        try {
        data.removeEvent((Event) data.eventsOnDate(new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                Integer.parseInt(dateComponents[1]) - 1,
                Integer.parseInt(dateComponents[2]))).toArray()[eventNum]);
        }
        catch (Exception e) { System.out.println("Event does not exist."); System.exit(0); }

        data.writeDataToFile("cal-data.cal");
        read.close();
    }

    /**
     * Prompt user for input and edit an event in the calendar data file
     *
     * @param args passed in from main
     */
    public void edit(String[] args) {

        CalendarData data = new CalendarData("cal-data.cal");
        CalendarListFormatter formatter = new CalendarListFormatter();
        Scanner read = new Scanner(System.in);
        GregorianCalendar beginDate = null;
        GregorianCalendar endDate = null;
        String[] dateComponents = new String[3];
        String date;
        String eventString = "";

        if (args.length != 2) {
            System.out.println("Enter date (yyyy-mm-dd): ");
            date = read.nextLine();
        }
        else
            date = args[1];

        // create Calendars from input
        try {
            dateComponents = date.split("-");
            beginDate = new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                    Integer.parseInt(dateComponents[1]) - 1,
                    Integer.parseInt(dateComponents[2]));
            endDate = new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                    Integer.parseInt(dateComponents[1]) - 1,
                    Integer.parseInt(dateComponents[2]));
        }
        catch (Exception e) { System.out.println("Malformed date, incorrect user input."); System.exit(0); }

        // format and print a numbered listing of events
        formatter.setDateRange(beginDate, endDate);
        formatter.setNumberEvents(true);
        formatter.setShowDescription(true);
        String output = formatter.format(data);
        System.out.println(output);

        // prompt user for input
        System.out.println("Event to edit: ");
        int eventNum = 0;
        try { eventNum = Integer.parseInt(read.nextLine()) - 1; }
        catch (Exception e) { System.out.println("Type error, incorrect user input."); System.exit(0); }

        // retrieve the event
        String tempString;
        Event event = null;
        try {
            event = (Event) data.eventsOnDate(new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                Integer.parseInt(dateComponents[1]) - 1,
                Integer.parseInt(dateComponents[2]))).toArray()[eventNum];
        }
        catch (Exception e) { System.out.println("No such event exists."); System.exit(0); }

        // prompt user for edits add in from the original if the user gives empty input
        System.out.println("Enter date (yyyy-mm-dd): ");
        tempString = read.nextLine();
        if (tempString.isEmpty())
            eventString += event.getDate().get(GregorianCalendar.YEAR) + "-" +
                    (event.getDate().get(GregorianCalendar.MONTH)+ 1) + "-" +
                    event.getDate().get(GregorianCalendar.DAY_OF_MONTH) + "\t";
        else
            eventString += tempString + "\t";

        System.out.println("Enter time (hh:mm): ");
        tempString = read.nextLine();
        if (tempString.isEmpty())
            eventString += event.getDate().get(GregorianCalendar.HOUR_OF_DAY) + ":" +
                    event.getDate().get(GregorianCalendar.MINUTE) + "\t";
        else
            eventString += tempString + "\t";

        System.out.println("Enter event title: ");
        tempString = read.nextLine();
        if (tempString.isEmpty())
            eventString += event.getTitle() + "\t";
        else
            eventString += tempString + "\t";

        eventString += "-1\t";

        System.out.println("Enter event description: ");
        tempString = read.nextLine();
        if (tempString.isEmpty())
            eventString += event.getDescription() + "\t";
        else
            eventString += tempString + "\t";

        // remove the old event from the listing
        try {
            data.removeEvent((Event) data.eventsOnDate(new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                    Integer.parseInt(dateComponents[1]) - 1,
                    Integer.parseInt(dateComponents[2]))).toArray()[eventNum]);
        }
        catch (Exception e) { System.out.println("No such event exists."); System.exit(0); }

        // add the new event
        try { data.addEvent(new Event(eventString)); }
        catch (Exception e) { System.out.println("Malformed date, incorrect user input."); System.exit(0); }

        data.writeDataToFile("cal-data.cal");
        read.close();
    }
}
