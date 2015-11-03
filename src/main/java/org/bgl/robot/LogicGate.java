package org.bgl.robot;
import java.util.HashMap;

public class LogicGate {
	private HashMap<Integer, Integer> truthTable = new HashMap<Integer, Integer>();
	
	public LogicGate() {
		// Truth Table
		//-------------------------------------------------------------//
		//        |        |        |     |        |        |          //
		// FRONT  | LEFT   | RIGHT  | =>  | MOVE   | LEFT   | RIGHT    //
		//        |        |        |     |        |        |          //
		//-------------------------------------------------------------//
		//   0    |   0    |   0    |     |   0    |   0    |    0     //
		//-------------------------------------------------------------//
		//   0    |   0    |   1    |     |   0    |   0    |    1     //
		//-------------------------------------------------------------//
		//   0    |   1    |   0    |     |   0    |   1    |    0     //
		//-------------------------------------------------------------//
		//   0    |   1    |   1    |     |   0    |   1    |    1     //
		//-------------------------------------------------------------//
		//   1    |   0    |   0    |     |   1    |   0    |    0     //
		//-------------------------------------------------------------//
		//   1    |   0    |   1    |     |   1    |   0    |    1     //
		//-------------------------------------------------------------//
		//   1    |   1    |   0    |     |   1    |   1    |    0     //
		//-------------------------------------------------------------//
		//   1    |   1    |   1    |     |   1    |   1    |    1     //
		//-------------------------------------------------------------//		
		truthTable.put((0 | 0 | 0), (0 | 0 | 0)); // 0 0 0 => 0 0 0
		truthTable.put((0 | 0 | Sensors.RIGHT),( 0 | 0 | Commands.RIGHT)); // 0 0 1 => 0 0 1
		truthTable.put((0 | Sensors.LEFT | 0),( 0 | Commands.LEFT | 0)); // 0 1 0 => 0 1 0
		truthTable.put((0 | Sensors.LEFT | Sensors.RIGHT),( 0 | Commands.LEFT | Commands.RIGHT)); // 0 1 1 => 0 1 1
		truthTable.put((Sensors.FRONT | 0 | 0), (Commands.MOVE | 0 | 0)); // 1 0 0 => 1 0 0
		truthTable.put((Sensors.FRONT | 0 | Sensors.RIGHT),( Commands.MOVE | 0 | Commands.RIGHT)); // 1 0 1 => 1 0 1
		truthTable.put((Sensors.FRONT | Sensors.LEFT | 0),( Commands.MOVE | Commands.LEFT | 0)); // 1 1 0 => 1 1 0
		truthTable.put((Sensors.FRONT | Sensors.LEFT | Sensors.RIGHT),( Commands.MOVE | Commands.LEFT | Commands.RIGHT)); // 1 1 1 => 1 1 1
	}
	
	public int getCommands(int sensors) {
		Integer commands = this.truthTable.get(new Integer(sensors));
		return commands.intValue();
	}
	

}
