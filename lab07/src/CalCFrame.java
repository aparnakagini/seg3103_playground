import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class CalCFrame extends JFrame implements ActionListener {

    private JButton[] buttons = new JButton[19];
    private String[] buttonText = { " 1 ", " 2 ", " 3 ", " + ", " - ",
            " 4 ", " 5 ", " 6 ", " x ", " / ",
            " 7 ", " 8 ", " 9 ", "^ ", "sqrt",
            " C ", " 0 ", " . ", "    =    " };

    private JTextField result; // Calculator display screen
    private String input = ""; // stores user input
    private JLabel label;
    private Color forecolor, // Calculator foreground color
            backcolor, // Calculator background color
            fieldcolor; // the display screen's color
    private Font font,
            buttonfont;
    private int oper = 0, // stores the integer constants representing
            oldoper = 0, // the operators
            newoper = 0;
    private double answer,
            num1 = 0.0,
            num2 = 0.0,
            num3 = 0.0;
    private static final int ADD = 1, // integer constants representing operators
            SUB = 2,
            MULT = 3,
            DIVI = 4,
            POW = 5,
            SQRT = 6;
    private boolean firstpress = true, // determines first button press
            morenums = false, // "" if more numbers are being pressed
            equals = false, // "" if equal button has been pressed
            clearscreen = false, // clears screen
            decnumber = false, // "" if a user entered a float
            doubleclick = false; // "" if mouse was doubleclicked

    public CalCFrame(String title) {
        super(title);
        addWindowListener(new MyWindowAdapter());
        buttonfont = new Font("Courier", Font.PLAIN, 12);
        setBackground(Color.lightGray);
    }

    public void initUI() {
        result = new JTextField(25);
        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(result);

        for (int i = 0; i < 19; i++) {
            buttons[i] = new JButton(buttonText[i]);
            buttons[i].setFont(buttonfont);
            getContentPane().add(buttons[i]);
            if (i == 2 || i == 7 || i == 12 || i == 17) {
                getContentPane().add(new JLabel("  "));
            }
        }
        buttons[15].setForeground(Color.red);
        result.setBackground(Color.white);

        // Add action listeners outside of constructor
        for (JButton button : buttons) {
            button.addActionListener(this);
        }
    }

    // Named static inner class
    private static class MyWindowAdapter extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() != buttons[3] && e.getSource() != buttons[4]
                && e.getSource() != buttons[8] && e.getSource() != buttons[9]
                && e.getSource() != buttons[13] && e.getSource() != buttons[14]
                && e.getSource() != buttons[15] && e.getSource() != buttons[18]) {

            if (clearscreen) {
                clearScreen();
                clearscreen = false;
            }

            for (int i = 0; i < buttons.length; i++) {
                if (e.getSource() == buttons[i]) {
                    input += buttonText[i].trim();
                    showAnswer(input);
                    break;
                }
            }

            if (e.getSource() == buttons[17]) {
                if (!decnumber) {
                    decnumber = true;
                    input += ".";
                    showAnswer(input);
                }
            }
        }

        if (e.getSource() == buttons[3]) {
            processOperation(ADD);
        } else if (e.getSource() == buttons[4]) {
            processOperation(SUB);
        } else if (e.getSource() == buttons[8]) {
            processOperation(MULT);
        } else if (e.getSource() == buttons[9]) {
            processOperation(DIVI);
        } else if (e.getSource() == buttons[13]) {
            processOperation(POW);
        } else if (e.getSource() == buttons[14]) {
            processOperation(SQRT);
        }

        if (e.getSource() == buttons[15]) {
            clearScreen();
        }

        if (e.getSource() == buttons[18]) {
            equals = true;
            clearscreen = true;
            clickCheck(input);
            if (!doubleclick) {
                processNumbers();
                input = Double.toString(answer);
            }
        }
    }

    private void processOperation(int operation) {
        clearscreen = false;
        decnumber = false;
        oper = operation;
        clickCheck(input);
        if (!doubleclick) {
            processNumbers();
        }
        input = "";
    }

    public void processNumbers() {
        if (firstpress) {
            if (equals) {
                num1 = answer;
                equals = false;
            } else {
                num1 = Double.parseDouble(input);
            }
            oldoper = oper;

            if (oper == SQRT) {
                answer = calculate(oldoper, num1, 0.0);
                showAnswer(Double.toString(answer));
                morenums = true;
            }
            firstpress = false;
        } else if (!morenums) {
            num2 = Double.parseDouble(input);
            answer = calculate(oldoper, num1, num2);
            showAnswer(Double.toString(answer));
            newoper = oper;
            if (!equals) {
                morenums = true;
            } else {
                morenums = false;
                firstpress = true;
            }
        } else {
            if (equals) {
                newoper = oper;
                morenums = false;
                firstpress = true;
            }
            num3 = Double.parseDouble(input);
            answer = calculate(newoper, answer, num3);
            showAnswer(Double.toString(answer));
            newoper = oper;
        }
    }

    public double calculate(int oper, double number1, double number2) {
        double answer = 0.0;

        switch (oper) {
            case ADD:
                answer = number1 + number2;
                break;
            case SUB:
                answer = number1 - number2;
                break;
            case MULT:
                answer = number1 * number2;
                break;
            case DIVI:
                answer = number1 / number2;
                break;
            case POW:
                answer = Math.pow(number1, number2);
                break;
            case SQRT:
                answer = Math.sqrt(number1);
                break;
            default:
                throw new IllegalArgumentException("Unexpected operation: " + oper);
        }

        return answer;
    }

    public void showAnswer(String s) {
        double answer = Double.parseDouble(s);
        if (decnumber) {
            result.setText(Double.toString(answer));
        } else {
            result.setText(s);
        }
    }

    public boolean clickCheck(String s) {
        doubleclick = s.equals("");
        return doubleclick;
    }

    public void clearScreen() {
        oper = 0;
        input = "";
        answer = 0;
        decnumber = false;
        morenums = false;
        firstpress = true;
        equals = false;
        showAnswer(Integer.toString((int) answer));
    }
}
