package ants.runner;
import java.util.Scanner;

public class ConsoleReader {
	private Scanner reader = new Scanner(System.in);
	
	public void prompt(String userPrompt) {
		System.out.println(userPrompt);
	}
	
	public String readNextLine() {
		return String.valueOf(reader.nextLine());
	}
}
