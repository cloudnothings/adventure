public class ItemButton2 extends Item implements Pushable, Visible {

	public ItemButton2(String s, String sd, String[] a) {
		super(s, sd, a);
	}
	public void push() {
		System.out.println("Beep");
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean b) {
		this.visible = b;
	}
	protected boolean visible = true;
}