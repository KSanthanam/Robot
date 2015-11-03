package org.bgl.robot;

/**
 * Robot Application!
 *
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import org.bgl.robot.Commands.Command;

import jline.console.ConsoleReader;
//import jline.console.completer.AnsiStringsCompleter;
//import jline.console.completer.CandidateListCompletionHandler;
import jline.console.completer.Completer;
import jline.console.completer.StringsCompleter;
public class Robot 
{
	public static void usage() {
        System.out.println("Usage: java " + Robot.class.getName()
            + " [none/suggest]");
        System.out.println("  none - no completors");
        System.out
            .println("  suggest - a completor that comples " + "possible commands");
        System.out.println("  color - colored prompt and feedback");
        System.out.println("\n  E.g - java Robot suggest \n"
            + "will use the suggest compleator \n");
    }
	private static RobotState robotState = new RobotState();
    public static void main( String[] args ) throws IOException
    {
    	try {
//    		boolean color = false;
    		ConsoleReader reader = new ConsoleReader();
    		reader.setPrompt("Robot > ");
    		if ((args == null) || (args.length == 0)) {
    			usage();
    			return;
    		}
    		List<Completer> completors = new LinkedList<Completer>();
    		
    		if(args.length > 0) {
    			if (args[0].equals("none")) {    				
    			}
    			else if (args[0].equals("suggest")) {
    				completors.add(new StringsCompleter("PLACE", "REPORT"));
    			}
    			else {
        			usage();
        			return;    				
    			}
    		}
    		for (Completer c : completors) {
                reader.addCompleter(c);
            }
    		String line;
    		String[] tokens;
    		String command, parameter;
    		
            PrintWriter out = new PrintWriter(reader.getOutput());
            while ((line = reader.readLine()) != null) {
                 out.flush();
                 line = line.trim();
            	 tokens = line.split(" ");
            	 if (!Commands.isLegitimateCommand(tokens[0])) {
            		 out.println("Illegitimate Command");
            		 continue;
            	 }            	 
            	 command = tokens[0].toUpperCase();
            	 if (line.length() > command.length()) parameter = line.substring(command.length()); else parameter = "";
            	 if (robotState.validCommand(command)) {
                     switch (command) {
                     	case "PLACE":
                     		if (parameter.length() == 0) {                     			
                     			out.println("PLACE command needs parameters");
                     		} else {
                     			try {
		                     		if (!Commands.validParameter(Command.PLACE, parameter)) {
		                     			out.println("Invalid PLACE parameter. Usage PLACE x,y,Direction");
		                     		} else {
		                     			robotState.place(parameter);
		                         		out.println("Placed Robot with Parameter " + parameter + "; And " + robotState.reportCurrentState());
		                     		}
                     			} catch (PositionOutOfBoundException oob) {
	                     			out.println("Invalid PLACE parameter." + oob.getMessage());                     				
                     			} catch (InvalidDirectionException id) {
	                     			out.println("Invalid PLACE parameter." + id.getMessage());                     				                     				
                     			}
                     		}
                     		break;
                     	case "MOVE":
                     		if (parameter.length() > 0) {
                     			out.println("No parameter allowed for MOVE command");
                     		} else {
                     			robotState.move();
                         		out.println("Robot moved; And " + robotState.reportCurrentState());
                     		}
                     		break;
                     	case "LEFT":
                     		if (parameter.length() > 0) {
                     			out.println("No parameter allowed for LEFT command");
                     		} else {
                     			robotState.left();;
                         		out.println("Robot turned; And " + robotState.reportCurrentState());
                     		}
                     		break;
                     	case "RIGHT":
                     		if (parameter.length() > 0) {
                     			out.println("No parameter allowed for RIGHT command");
                     		} else {
                     			robotState.right();
                         		out.println("Robot turned; And " + robotState.reportCurrentState());
                     		}
                     		break;
                     	case "REPORT":
                     		if (parameter.length() > 0) {
                     			out.println("No parameter allowed for REPORT command");
                     		} else {
                         		out.println( robotState.reportCurrentState());
                     		}
                     		break;
                     }
            	 } else {
            		 out.println("This Command is not possible in the current state : " + robotState.reportCurrentState());
            	 }
                if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                    break;
                }
                if (line.equalsIgnoreCase("cls")) {
                    reader.clearScreen();
                }
            }
    	} catch (Throwable t) {
    		t.printStackTrace();
    	}
    }
}
