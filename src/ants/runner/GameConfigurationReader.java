package ants.runner;

import ants.board.dimension.BoardDimension;

public class GameConfigurationReader {
	private static final String BOARD_DIMENSION_INPUT_FORMAT = "([0-9]+)X([0-9]+)";
	private ConsoleReader consoleReader;

	public GameConfigurationReader(ConsoleReader consoleReader) {
		this.consoleReader = consoleReader;
	}
	
	public BoardDimension promptForBoardDimension() {
		String boardDimensionString = "";
		BoardDimension dimension = BoardDimension.of(3, 5);
		do {
			consoleReader.prompt("Enter the board configuration in the following format: 4X5. "
					+ "The board configuration can not be symmetrical and has to be greater than 3X4 or 4X3.");
			boardDimensionString = consoleReader.readNextLine();
			if (boardDimensionString.matches(BOARD_DIMENSION_INPUT_FORMAT)){
				dimension = createBoardDimensionFrom(boardDimensionString);				
			}
		} while(enteredDimensionIsNotValid(boardDimensionString, dimension));
		return dimension;
	}
	
	public int promptForIntegerGreaterThanZero(String promptText) {
		boolean enteredIntIsValid = false;
		int entered = -1;
		do {
			consoleReader.prompt(promptText);
			try {
				entered = toInt(consoleReader.readNextLine());
				if (entered > 0)
					enteredIntIsValid = true;
			} catch(GameConfigurationFactoryException exc) {
				enteredIntIsValid = false;
			}
			
		} while(!enteredIntIsValid);
		
		return entered;
	}

	private BoardDimension createBoardDimensionFrom(String boardDimensionString) {
		String[] boardDimensionArray = boardDimensionString.split("X");
		return BoardDimension.of(toInt(boardDimensionArray[0]),
								 toInt(boardDimensionArray[1]));
	}
	
	private boolean enteredDimensionIsNotValid(String boardDimensionString, BoardDimension dimension) {
		return !boardDimensionString.matches(BOARD_DIMENSION_INPUT_FORMAT) || dimension.symmetrical() || dimension.smallerThan(BoardDimension.of(3, 4));
	}
	
	private int toInt(String input) {
		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException exc) {
			throw new GameConfigurationFactoryException(String.format("not an integer: %s", input));
		}
	}
}
