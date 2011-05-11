package net.digitaltsunami.word.util.event;

import java.util.EventObject;

import net.digitaltsunami.word.util.CharTrieNode;

public class NodeAddedEvent extends EventObject {

	private static final long serialVersionUID = 1091049728144325287L;
	
	
	public NodeAddedEvent(CharTrieNode source) {
		super(source);
	}
	
	public CharTrieNode getNode() {
		return (CharTrieNode) getSource();
	}
}
