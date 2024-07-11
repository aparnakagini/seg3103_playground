import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] argv) {
        SwingUtilities.invokeLater(() -> {
            CalCFrame frame = new CalCFrame("Calculator");
            frame.setSize(360, 200);
            frame.setVisible(true);
            frame.initUI(); // Call UI initialization method after construction
        });
    }
}
