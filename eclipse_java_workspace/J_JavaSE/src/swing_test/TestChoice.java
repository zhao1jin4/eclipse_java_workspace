package swing_test;
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Frame;

public class TestChoice {
	public static void main(String[] args) {
		Choice ch = new Choice();
		ch.add("aa");

		ch.add("bb");
		ch.add("cc");
		ch.add("dd");
		ch.add("ee");

		Frame fr = new Frame();
		fr.add(ch, BorderLayout.NORTH);
		fr.setVisible(true);
		fr.setSize(300, 300);

	}
}
