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
        else if (args.length == 2) { // If they entered a single date, we're looking for one day.
            startString = args[1];
            endString = args[1];
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
        catch (Exception e) { System.out.println("Malformed date, incorrect user input."); System.exit(1); }

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

        GregorianCalendar newEventDate = new GregorianCalendar(); // Will be replaced. Easier than useless null checks.

        boolean dateArg = false;
        while (true) { // Loop until we get valid input.
            String[] dateBits;
            if (args.length <= 1) {
                System.out.println("Enter date (yyyy-mm-dd): ");
                dateBits = read.nextLine().split("-");
            }
            else {
                dateBits = args[1].split("-");
                dateArg = true;
            }

            if (dateBits.length != 3) {
                System.out.println("Please enter a valid date.");
                if (dateArg) {
                    System.exit(1);
                }
                continue;
            }

            try {
                newEventDate = new GregorianCalendar(Integer.parseInt(dateBits[0]),
                                                     Integer.parseInt(dateBits[1]) - 1,
                                                     Integer.parseInt(dateBits[2]));
                break;
            }
            catch (Exception e) {
                System.out.println("Please enter a valid date.");
                if (dateArg) {
                    System.exit(1);
                }
                continue;
            }
        }

        boolean timeArg = false;
        while (true) { // Again! Again!
            String[] timeBits;
            if (args.length <= 2) {
                System.out.println("Enter time (hh:mm): ");
                timeBits = read.nextLine().split(":");
            }
            else {
                timeBits = args[2].split(":");
                timeArg = true;
            }

            if (timeBits.length != 2) {
                System.out.println("Please enter a valid time.");
                if (timeArg) {
                    System.exit(1);
                }
                continue;
            }

            try {
                if (timeBits[1].length() == 2) { // 24-hr time
                    newEventDate.set(GregorianCalendar.HOUR_OF_DAY, Integer.parseInt(timeBits[0]));
                    newEventDate.set(GregorianCalendar.MINUTE, Integer.parseInt(timeBits[1]));
                }
                else { // 12-hr
                    int hour = Integer.parseInt(timeBits[0]);
                    if (hour == 12) {
                        hour = 0; // I hate Java.
                    }

                    int ampm = 0;
                    if (timeBits[1].substring(2).toLowerCase().equals("a")) {
                        ampm = GregorianCalendar.AM;
                    }
                    else if (timeBits[1].substring(2).toLowerCase().equals("p")) {
                        ampm = GregorianCalendar.PM;
                    }
                    else {
                        throw new Exception("Incorrect AM/PM designation.");
                    }

                    newEventDate.set(GregorianCalendar.HOUR, hour);
                    newEventDate.set(GregorianCalendar.MINUTE, Integer.parseInt(timeBits[1].substring(0, 1)));
                    newEventDate.set(GregorianCalendar.AM_PM, ampm);
                }

                break;
            }
            catch (Exception e) {
                System.out.println("Please enter a valid time.");
                if (timeArg) {
                    System.exit(1);
                }
                continue;
            }
        }

        System.out.println("Enter event title:");
        String eventTitle = read.nextLine();

        String description = "";
        String nextLine = "x";
        System.out.println("Enter event description (blank line to finish):");
        while (!(nextLine = read.nextLine()).equals("")) {
            if (!description.equals("")) {
                description += "\n";
            }
            description += nextLine;
        }
        System.out.println(description);

        try {
            Event newEvent = new Event(newEventDate, eventTitle, description, -1);
            data.addEvent(newEvent);
        }
        catch (Exception e) { System.out.println("Oops, something went wrong."); System.exit(1); }
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
        String[] dateBits;
        String date;

        boolean dateArg = false;
        while (true) { // Loop until we get valid input.
            if (args.length <= 1) {
                System.out.println("Enter date (yyyy-mm-dd): ");
                dateBits = read.nextLine().split("-");
            }
            else {
                dateBits = args[1].split("-");
                dateArg = true;
            }

            if (dateBits.length != 3) {
                System.out.println("Please enter a valid date.");
                if (dateArg) {
                    System.exit(1);
                }
                continue;
            }

            try {
                beginDate = new GregorianCalendar(Integer.parseInt(dateBits[0]),
                                                  Integer.parseInt(dateBits[1]) - 1,
                                                  Integer.parseInt(dateBits[2]));
                endDate = new GregorianCalendar(Integer.parseInt(dateBits[0]),
                                                Integer.parseInt(dateBits[1]) - 1,
                                                Integer.parseInt(dateBits[2]));
                break;
            }
            catch (Exception e) {
                System.out.println("Please enter a valid date.");
                if (dateArg) {
                    System.exit(1);
                }
                continue;
            }
        }

        formatter.setDateRange(beginDate, endDate);
        formatter.setNumberEvents(true);
        formatter.setShowDescription(true);

        String output = formatter.format(data);
        System.out.println(output);

        int eventNum = -1;
        while (true) {
            System.out.println("Event to remove: ");
            eventNum = 0;
            try {
                eventNum = Integer.parseInt(read.nextLine()) - 1;
                data.removeEvent((Event) data.eventsOnDate(new GregorianCalendar(Integer.parseInt(dateBits[0]),
                    Integer.parseInt(dateBits[1]) - 1,
                    Integer.parseInt(dateBits[2]))).toArray()[eventNum]);
                break;
            }
            catch (Exception e) {
                System.err.println("Please select a valid event.");
                continue;
            }
        }

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

        boolean dateArg = false;
        while (true) {
            if (args.length != 2) {
                System.out.println("Enter date (yyyy-mm-dd): ");
                date = read.nextLine();
            }
            else {
                date = args[1];
                dateArg = true;
            }

            // create Calendars from input
            try {
                dateComponents = date.split("-");
                beginDate = new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                        Integer.parseInt(dateComponents[1]) - 1,
                        Integer.parseInt(dateComponents[2]));
                endDate = new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                        Integer.parseInt(dateComponents[1]) - 1,
                        Integer.parseInt(dateComponents[2]));

                break;
            }
            catch (Exception e) {
                System.err.println("Please enter a valid date.");
                if (dateArg) {
                    System.exit(1);
                }
                continue;
            }
        }

        // format and print a numbered listing of events
        formatter.setDateRange(beginDate, endDate);
        formatter.setNumberEvents(true);
        formatter.setShowDescription(true);
        String output = formatter.format(data);
        System.out.println(output);

        // prompt user for input
        int eventNum = -1;
        while (true) {
            System.out.println("Event to edit: ");
            eventNum = 0;
            try {
                eventNum = Integer.parseInt(read.nextLine()) - 1;
                break;
            }
            catch (Exception e) {
                System.err.println("Please select a valid event.");
                continue;
            }
        }

        // retrieve the event
        String tempString;
        Event event = null;
        while (true) {
            try {
                event = (Event) data.eventsOnDate(new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                    Integer.parseInt(dateComponents[1]) - 1,
                    Integer.parseInt(dateComponents[2]))).toArray()[eventNum];
                break;
            }
            catch (Exception e) {
                System.out.println("Please select a valid event.");
                continue;
            }
        }

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

        String description = "";
        String nextLine = "x";
        System.out.println("Enter event description (blank line to finish):");
        while (!(nextLine = read.nextLine()).equals("")) {
            if (!description.equals("")) {
                description += "\n";
            }
            description += nextLine;
        }
        if (description.equals("")) {
            eventString += event.getDescription() + "\t";
        }
        else {
            eventString += description + "\t\t";
        }

        // remove the old event from the listing
        try {
            data.removeEvent((Event) data.eventsOnDate(new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                    Integer.parseInt(dateComponents[1]) - 1,
                    Integer.parseInt(dateComponents[2]))).toArray()[eventNum]);
        }
        catch (Exception e) { System.out.println("No such event exists."); System.exit(1); }

        // add the new event
        try { data.addEvent(new Event(eventString)); }
        catch (Exception e) { System.out.println("Malformed date, incorrect user input."); System.exit(1); }

        data.writeDataToFile("cal-data.cal");
        read.close();
    }
}
