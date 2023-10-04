package battleship.tir;



import javax.swing.JButton;

public class MyButton extends JButton {
    private static final long serialVersionUID = 1L;

    public int ligne;
    public int colonne;

    public MyButton(String name) {
        super(name);
    }
}