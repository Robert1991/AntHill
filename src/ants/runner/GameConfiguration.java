package ants.runner;
import java.util.List;
import java.util.function.Supplier;

import ants.board.GameBoard;
import ants.board.GameBoardFactory;
import ants.board.cells.Coordinates;
import ants.board.cells.GameCell;
import ants.board.dimension.BoardDimension;
import ants.board.dimension.GameObjectPositions;
import ants.random.RandomWrapper;

public class GameConfiguration {
	private RandomWrapper randomWrapper = new RandomWrapper();
	private int maxFoodPerFoodField;
	private int foodCellCount;
	private int antCount;
	private BoardDimension boardDimension;
	private GameBoard gameBoardMock;
	
	public static GameConfiguration promptFromStdIn() {
		return new GameConfigurationBuilder(new GameConfigurationReader(new ConsoleReader())).promptForGameConfiguration();
	}
	
	public GameConfiguration(BoardDimension boardDimension, int maxFoodPerFoodField, int foodCellCount, int antCount) {
		if (maxFoodPerFoodField < 1)
			throw new IllegalArgumentException("the maximum food count per cell has to be at least 1!");
		if (foodCellCount < 1)
			throw new IllegalArgumentException("the food cell count has to be greater or equal than 1!");
		if (antCount < 1)
			throw new IllegalArgumentException("there has to be at least one ant in that hill!");
		if (boardDimension.symmetrical())
			throw new IllegalArgumentException("board dimension can not be symmetrical for ant simulation");
		this.maxFoodPerFoodField = maxFoodPerFoodField;
		this.foodCellCount = foodCellCount;
		this.antCount = antCount;
		this.boardDimension = boardDimension;
	}
	
	public GameConfiguration with(GameBoard gameBoardMock) {
		this.gameBoardMock = gameBoardMock;
		return this;
	}
		
	public GameConfiguration with(RandomWrapper wrapper) {
		this.randomWrapper = wrapper;
		return this;
	}
	
	public GameBoard createBoard() {
		if (gameBoardMock != null)
			return gameBoardMock;
		return GameBoardFactory.createFor(this);
	}
	
	public GameObjectPositions createRandomGameBoardPositions() {
		return boardDimension.randomPositionsFor(this);
	}
	
	public List<Coordinates> createBoardCoordinates() {
		return boardDimension.createCoordinates();
	}
	
	public void forFoodCellCount(Supplier<?> action) {
		for (int i = 0; i < foodCellCount; i++) {
			action.get();
		}
	}
	
	public GameCell nextFoodCell() {
		return GameCell.foodCellWith(nextFoodCount());
	}

	public GameCell createAntHillAt(Coordinates coordinates) {
		return GameCell.antHill(antCount, coordinates);
	}
	
	public BoardDimension dimension() {
		return this.boardDimension;
	}
	
	private int nextFoodCount() {
		return randomWrapper.randIntIn(1, maxFoodPerFoodField);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + antCount;
		result = prime * result + ((boardDimension == null) ? 0 : boardDimension.hashCode());
		result = prime * result + foodCellCount;
		result = prime * result + maxFoodPerFoodField;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameConfiguration other = (GameConfiguration) obj;
		if (antCount != other.antCount)
			return false;
		if (boardDimension == null) {
			if (other.boardDimension != null)
				return false;
		} else if (!boardDimension.equals(other.boardDimension))
			return false;
		if (foodCellCount != other.foodCellCount)
			return false;
		if (maxFoodPerFoodField != other.maxFoodPerFoodField)
			return false;
		return true;
	}
}
