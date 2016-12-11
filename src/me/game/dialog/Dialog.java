package me.game.dialog;

import me.game.main.Engine;

public class Dialog {
	String[] text;
	int line = 0;
	Runnable runnable;
	String owner;
	public Dialog(String owner,String[] te,Runnable run) {
		text = te;
		line = 0;
		runnable = run;
		this.owner=owner;
	}
	public String getOwner() {
		return owner;
	}
	public String[] getText() {
		return text;
	}
	public int getLine() {
		return line;
	}
	public Runnable getRunnable() {
		return runnable;
	}
	public void nextLine(Engine e) {
		e.getSound().playClip("hit");
		line=line+3;
		if(line>=text.length) {
			e.setDialog(null);
			if(runnable!=null)
				runnable.run();
		}
	}
	public void up(Engine e) {
	}
	public void down(Engine e) {
	}
}
