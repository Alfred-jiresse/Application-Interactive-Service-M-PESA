// Main.java
import java.util.Scanner;
import ui.InterfaceConsole;

public class Main {
    public static void main(String[] args) {
        InterfaceConsole interfaceConsole = new InterfaceConsole(new Scanner(System.in));
        interfaceConsole.demarrer();
    }
}
