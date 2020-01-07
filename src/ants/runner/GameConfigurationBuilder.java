package ants.runner;

import ants.board.dimension.BoardDimension;

public class GameConfigurationBuilder {

	private GameConfigurationReader gameConfigurationReader;

	public GameConfigurationBuilder(GameConfigurationReader gameConfigurationReader) {
		this.gameConfigurationReader = gameConfigurationReader;
	}

	public GameConfiguration promptForGameConfiguration() {
		BoardDimension dimension = gameConfigurationReader.promptForBoardDimension();
		int foodCellCount = gameConfigurationReader.promptForIntegerGreaterThanZero("Enter the count of food cells on the board. It has to be at least 1.");
		int maxFoodPerFoodCell = gameConfigurationReader.promptForIntegerGreaterThanZero("Enter the maximum food count per food cell. It has to be at least 1.");
		int antCount = gameConfigurationReader.promptForIntegerGreaterThanZero("Enter the count of the ants which will be present in the ant hill. The count has to be greater or equal than 1.");
		return new GameConfiguration(dimension, maxFoodPerFoodCell, foodCellCount, antCount);
	}

}
