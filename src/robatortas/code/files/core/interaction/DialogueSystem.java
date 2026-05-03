package robatortas.code.files.core.interaction;

import robatortas.code.files.core.input.InputManager;
import robatortas.code.files.core.level.LevelManager;
import robatortas.code.files.project.GameManager;

public class DialogueSystem {
	
	public boolean inDialogue = false;
	
	private GameManager game;
	
	private InputManager input;
	
	public DialogueSystem(GameManager game, InputManager input) {
		this.game = game;
		this.input = input;
	}
	
	int ticks = 0;
	int dialogueTime = 0;
	boolean temp = false;
	public void update() {
		// ONLY if the player has something to interact with..
		if(LevelManager.player.isInInteractibleRange) {
			inDialogue = input.toggle(input.f, inDialogue);
			if(inDialogue) {
				GameManager.PLAYSTATE.IN_DIALOGUE.state = true;
				dialogueTime++;
				input.update();
			} else {
				GameManager.PLAYSTATE.IN_DIALOGUE.state = false;
			}
		}
	}
}