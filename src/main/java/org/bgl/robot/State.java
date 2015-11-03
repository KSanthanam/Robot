package org.bgl.robot;

import java.util.List;

public interface State {
	boolean getIsActive();
	void setIsActive(boolean active);
	String report();
	List<?> movesGenerator();	
}
