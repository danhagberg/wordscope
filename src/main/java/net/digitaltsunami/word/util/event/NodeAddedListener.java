/**
 * 
 */
package net.digitaltsunami.word.util.event;

import java.util.EventListener;

/**
 * @author dhagberg
 *
 */
public interface NodeAddedListener extends EventListener {
	public void characterAdded(NodeAddedEvent event);
	public void terminusCharacterAdded(TerminusNodeAddedEvent event);
	public void nodeAdded(NodeAddedEvent event);
	public void terminusNodeAdded(TerminusNodeAddedEvent event);
}
