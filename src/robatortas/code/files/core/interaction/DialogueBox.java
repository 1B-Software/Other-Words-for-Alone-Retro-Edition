package robatortas.code.files.core.interaction;

import robatortas.code.files.core.console.Console;

public class DialogueBox {

	private int width, height;
	private String from;
	private DialogueSystem dialogueSystem;
	
	public DialogueBox(String text, String from, DialogueSystem dialogueSystem) {
		this.from = from;
		this.dialogueSystem = dialogueSystem;
		dialogueSystem.inDialogue = true;
		Console.log(from + " says: " + text);
	}
	
	// From whom is the message ?
	public String from() {
		return this.from;
	}
	
	public void setWidth() {
		
	}
	
	public void setHeight() {
		
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
}