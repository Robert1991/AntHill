package ants.runner;

public class AntAlgorithm {
	public static void main(String[] args) {
		new AntColonyIterator(GameConfiguration.promptFromStdIn()).letAntsCrawl();
	}
}
