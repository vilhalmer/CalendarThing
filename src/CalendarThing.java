/** calendar thing
 *  @author Bill Doyle, Jason Elston
 */

public class CalendarThing
{
    private static final int MODE_ERROR = 0;
    private static final int MODE_VIEW = 1;
    private static final int MODE_ADD = 2;
    private static final int MODE_EDIT = 3;

    public static void main(String[] args)
    {
        int mode = MODE_ERROR;

        if (args.length == 0) {
            mode = MODE_VIEW;
        }
        else {
            if (args[0].startsWith("-")) {
                mode = MODE_VIEW;
            }
            else {
                switch (args[0].toLowerCase()) {
                    case "view":
                        mode = MODE_VIEW;
                        break;
                    case "add":
                        mode = MODE_ADD;
                        break;
                    case "edit":
                        mode = MODE_EDIT;
                        break;
                }
            }
        }

        if (mode == MODE_ERROR) { // Error handling!
            System.out.println("derp");
            System.exit(0);
        }
        else {
            System.out.println(mode);
        }
    }

    public void display(int beginDate, int endDate)
    {
        
    }
}
