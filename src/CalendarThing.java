/** calendar thing
 *  @author Bill Doyle, Jason Elston
 */

public class CalendarThing
{
    private static final int MODE_ERROR = 0;
    private static final int MODE_VIEW = 1;
    private static final int MODE_ADD = 2;
    private static final int MODE_REMOVE = 3;
    private static final int MODE_EDIT = 4;

    public static void main(String[] args)
    {
        CalendarIO IO = new CalendarIO();
        int mode = MODE_ERROR;

        if (args.length == 0) {
            mode = MODE_VIEW;
            IO.display(args);
        }
        else {
            if (args[0].startsWith("-")) {
                mode = MODE_VIEW;
            }
            else {
                switch (args[0].toLowerCase()) {
                    case "view":
                        mode = MODE_VIEW;
                        IO.display(args);
                        break;
                    case "add":
                        mode = MODE_ADD;
                        IO.add(args);
                        break;
                    case "remove":
                        mode = MODE_REMOVE;
                        IO.remove(args);
                        break;
                    case "edit":
                        mode = MODE_EDIT;
                        IO.edit(args);
                        break;
                }
            }
        }

        if (mode == MODE_ERROR) { // Error handling!
            System.out.println("Incorrect arguments passed.");
            System.exit(0);
        }
    }
}
