package org.bgl.robot;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.bgl.robot.Commands.Command;
import org.junit.Before;
import org.junit.Rule;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
/**
 * Unit test for Robot App.
 */

public class RobotTest 
    extends TestCase
{
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	private RobotState robotState = new RobotState();
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RobotTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static junit.framework.Test suite()
    {
        return new TestSuite( RobotTest.class );
    }

    /**
     * Load RobotState 
     */
    @Before
    public void LoadRobotState()
    {
    	robotState = new RobotState();
    }
    
    /**
     * Commands Test 
     */
    @org.junit.Test
    public void testCommands()
    {
    	// Initial State    	
        assertTrue(robotState.validCommand("PLACE"));
        assertTrue(!robotState.validCommand("REPORT"));
        assertTrue(!robotState.validCommand("MOVE"));
        assertTrue(!robotState.validCommand("LEFT"));
        assertTrue(!robotState.validCommand("RIGHT"));        
    }
    /**
     * Active Commands Test 
     */
    @org.junit.Test
    public void testActiveState()
    {
    	// Place the Robot
    	robotState.place("3,3,NORTH");
        assertTrue(robotState.validCommand("PLACE"));
        assertTrue(robotState.validCommand("REPORT"));
        assertTrue(robotState.validCommand("MOVE"));
        assertTrue(robotState.validCommand("LEFT"));
        assertTrue(robotState.validCommand("RIGHT"));        
    }

    /**
     * Test Parameters Exception   
     * @throws PositionOutOfBoundException 
     * @throws InvalidDirectionException 
     *
     */
    @org.junit.Test
    public void testvalidParameter() throws PositionOutOfBoundException, InvalidDirectionException {
    	try {
        	Commands.validParameter(Command.PLACE, "33,3,NORTH");
        	assertTrue(false);
    	} catch (PositionOutOfBoundException poob) {
    		assertTrue(true);    		
    	} 
    	try {
        	Commands.validParameter(Command.PLACE, "3,3,XYZ");
        	assertTrue(false);
        }  catch (InvalidDirectionException ide) {
    		assertTrue(true);
    	}
    }

    /**
     * Test InValid Commands    
     *
     */
    @org.junit.Test
    public void testInValidCommands() throws PositionOutOfBoundException, InvalidDirectionException {
    	try {
        	Commands.validParameter(Command.PLACE, "33,3,NORTH");
        	assertTrue(false);
    	} catch (PositionOutOfBoundException poob) {
    		assertTrue(true);    		
    	} 
    	try {
        	Commands.validParameter(Command.PLACE, "3,3,XYZ");
        	assertTrue(false);
        }  catch (InvalidDirectionException ide) {
    		assertTrue(true);
    	}
    }
    /**
     * Test Valid Commands    
     *
     */
    @org.junit.Test
    public void testValidCommands() throws WrongMoveException {
        robotState.place("3,3,north");   
        assertThat(robotState.reportCurrentState(), is("Robot is  at 3 x 3 facing NORTH"));        
        robotState.move();
        assertThat(robotState.reportCurrentState(), is("Robot is  at 3 x 4 facing NORTH"));        
        robotState.move();
        assertThat(robotState.reportCurrentState(), is("Robot is  at 3 x 5 facing NORTH"));        
        try {        	
            robotState.move();
            assertTrue(false);
        } catch (WrongMoveException wm) {
        	assertTrue(true);
        }
        try {        	
            robotState.right();
            assertThat(robotState.reportCurrentState(), is("Robot is  at 3 x 5 facing EAST"));        
        } catch (WrongTurnException wt) {
        	assertTrue(false);
        }
        robotState.move();
        assertThat(robotState.reportCurrentState(), is("Robot is  at 4 x 5 facing EAST"));        
        robotState.move();
        assertThat(robotState.reportCurrentState(), is("Robot is  at 5 x 5 facing EAST"));        
        try {        	
            robotState.left();
        	assertTrue(false);
        } catch (WrongTurnException wt) {
        	assertTrue(true);
        }
        assertThat(robotState.report(), is("5,5,EAST"));        
    }
}
