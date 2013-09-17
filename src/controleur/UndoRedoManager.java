package controleur;

import java.util.Stack;

import javax.swing.JComponent;


/**
 * Manage executed and canceled commands
 * @author daniel
 *
 */
public class UndoRedoManager {
	/**
	 * Already executed commands
	 */
	private Stack<Command> mCommandsDone;
	/**
	 * Canceled commands
	 */
	private Stack<Command> mCommandsUndone;
	/**
	 * Component linked with undo action
	 */
	private JComponent mUndo;
	/**
	 * Component linked with redo action
	 */
	private JComponent mRedo;
	
	/**
	 * Constructor 
	 * @param undo Component linked with undo action
	 * @param redo Component linked with redo action
	 */
	public UndoRedoManager(JComponent undo, JComponent redo) {
		mCommandsDone = new Stack<>();
		mCommandsUndone = new Stack<>();
		mUndo = undo;
		mRedo = redo;
		updateMenuItems();
	}
	
	/**
	 * Execute the command and remember it
	 * @param com Command to execute
	 */
	public void execute(Command com) {
		com.execute();
		mCommandsDone.push(com);
		mCommandsUndone.clear(); // clear the 'branch'
		updateMenuItems();
	}
	
	/**
	 * Cancel the latest executed command
	 */
	public void undo() {
		Command com = mCommandsDone.pop();
		com.undo();
		mCommandsUndone.push(com);
		updateMenuItems();
	}
	
	/**
	 * Re-execute the latest canceled command
	 */
	public void redo() {
		Command com = mCommandsUndone.pop();
		com.execute();
		mCommandsDone.push(com);
		updateMenuItems();
	}
	
	/**
	 * Update components' enable state
	 */
	public void updateMenuItems() {
		mUndo.setEnabled(!mCommandsDone.isEmpty());
		mRedo.setEnabled(!mCommandsUndone.isEmpty());
	}
}
