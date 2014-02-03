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

        CalendarData data = new CalendarData("test-data.cal");
        CalendarListFormatter listFormatter = new CalendarListFormatter();
        CalendarGridFormatter gridFormatter = new CalendarGridFormatter();
        Scanner read = new Scanner(System.in);
        String startString;
        String endString;

        if (args.length != 3) {
            System.out.println("Enter start date (yyyy-mm-dd): ");
            startString = read.nextLine();
            System.out.println("Enter end date (yyyy-mm-dd): ");
            endString = read.nextLine();
        }
        else {
            startString = args[1];
            endString = args[2];
        }

        String[] dateComponents = startString.split("-");
        GregorianCalendar beginDate = new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                Integer.parseInt(dateComponents[1]) - 1,
                Integer.parseInt(dateComponents[2]));
        beginDate.set(GregorianCalendar.DAY_OF_MONTH, 1);

        GregorianCalendar endDate = new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                Integer.parseInt(dateComponents[1]) - 1,
                Integer.parseInt(dateComponents[2]));
        endDate.set(GregorianCalendar.DAY_OF_MONTH, endDate.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));

        gridFormatter.setDateRange(beginDate, endDate);

        String output = gridFormatter.format(data);

        System.out.println("\n" + output);

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
    }

    /**
     * Prompt user for input and add an event to the calendar data file
     *
     * @param args passed in from main
     */
    public void add(String[] args) {
        CalendarData data = new CalendarData("test-data.cal");
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
        data.addEvent(new Event(eventString));
        data.writeDataToFile("test-data.cal");
    }

    /**
     * Prompt user for input and remove an event from the calendar data file
     *
     * @param args passed in from main
     */
    public void remove(String[] args) {

        CalendarData data = new CalendarData("test-data.cal");
        Scanner read = new Scanner(System.in);
        String date;
        if (args.length != 2) {
            System.out.println("Enter date (yyyy-mm-dd): ");
            date = read.nextLine();
        }
        else
            date = args[1];

        CalendarListFormatter formatter = new CalendarListFormatter();

        String[] dateComponents = date.split("-");
        GregorianCalendar beginDate = new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                Integer.parseInt(dateComponents[1]) - 1,
                Integer.parseInt(dateComponents[2]));
        GregorianCalendar endDate = new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                Integer.parseInt(dateComponents[1]) - 1,
                Integer.parseInt(dateComponents[2]));

        formatter.setDateRange(beginDate, endDate);
        formatter.setNumberEvents(true);
        formatter.setShowDescription(true);

        String output = formatter.format(data);
        System.out.println(output);

        System.out.println("Event to remove: ");
        int eventNum = read.nextInt() - 1;

        data.removeEvent((Event) data.eventsOnDate(new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                Integer.parseInt(dateComponents[1]) - 1,
                Integer.parseInt(dateComponents[2]))).toArray()[eventNum]);
        data.writeDataToFile("test-data.cal");

    }

    /**
     * Prompt user for input and edit an event in the calendar data file
     *
     * @param args passed in from main
     */
    public void edit(String[] args) {

        CalendarData data = new CalendarData("test-data.cal");
        Scanner read = new Scanner(System.in);
        String date;
        String eventString = "";

        if (args.length != 2) {
            System.out.println("Enter date (yyyy-mm-dd): ");
            date = read.nextLine();
        }
        else
            date = args[1];

        CalendarListFormatter formatter = new CalendarListFormatter();

        String[] dateComponents = date.split("-");
        GregorianCalendar beginDate = new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                Integer.parseInt(dateComponents[1]) - 1,
                Integer.parseInt(dateComponents[2]));
        GregorianCalendar endDate = new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                Integer.parseInt(dateComponents[1]) - 1,
                Integer.parseInt(dateComponents[2]));

        formatter.setDateRange(beginDate, endDate);
        formatter.setNumberEvents(true);
        formatter.setShowDescription(true);

        String output = formatter.format(data);
        System.out.println(output);

        System.out.println("Event to edit: ");
        int eventNum = Integer.parseInt(read.nextLine()) - 1;

        String tempString;


        Event event = (Event) data.eventsOnDate(new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                Integer.parseInt(dateComponents[1]) - 1,
                Integer.parseInt(dateComponents[2]))).toArray()[eventNum];

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

        data.removeEvent((Event) data.eventsOnDate(new GregorianCalendar(Integer.parseInt(dateComponents[0]),
                Integer.parseInt(dateComponents[1]) - 1,
                Integer.parseInt(dateComponents[2]))).toArray()[eventNum]);
        data.addEvent(new Event(eventString));
        data.writeDataToFile("test-data.cal");

    }
}
