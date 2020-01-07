package ants.tests.configuration;
import org.junit.Before;
import org.junit.Test;

import ants.board.dimension.BoardDimension;
import ants.runner.ConsoleReader;
import ants.runner.GameConfigurationReader;
import static org.mockito.Mockito.*;

import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;

public class GameConfigurationReaderTests {
	private ConsoleReader consoleReaderMock;
	private GameConfigurationReader gameConfigurationReader;

	@Before
	public void createMocksAndTestedInstance() {
		consoleReaderMock = mock(ConsoleReader.class);
		gameConfigurationReader = new GameConfigurationReader(consoleReaderMock);
	}
	
	@Test
	public void testPromptingAnIntegerGreaterThanZeroFromTheConsoleAndVerifyThatTheMessageTextIsPromtedUntilTheUserEnteredAValidNumber() {
		// returns valid dimension first
		when(consoleReaderMock.readNextLine())// literal
											  .thenReturn("abc")
											  // to small
											  .thenReturn("-1")
											  // valid minimum food count per cell
											  .thenReturn("10");
		assertThat(gameConfigurationReader.promptForIntegerGreaterThanZero("Enter the minimum food count per food cell. It has to be at least 1."),
				equalTo(10));
		verify(consoleReaderMock, times(3))
			.prompt("Enter the minimum food count per food cell. It has to be at least 1.");
	}
	
	@Test
	public void testThatTheBoardDimensionIsRedFirstAndAlsoCheckThatTheUserGetsPromptedAgainForInputUntilHeEnteredADimensionWhichIsBigEnoughToLetTheAntsSearch() {
		when(consoleReaderMock.readNextLine()).thenReturn("3X2")
											  .thenReturn("3X5");
		assertThat(gameConfigurationReader.promptForBoardDimension(), equalTo(BoardDimension.of(3, 5)));
		verify(consoleReaderMock, times(2))
			.prompt(eq("Enter the board configuration in the following format: 4X5. The board configuration can not be symmetrical and has to be greater than 3X4 or 4X3."));
		
	}
	
	
	@Test
	public void testThatTheBoardDimensionIsRedFirstAndAlsoCheckThatTheUserGetsPromptedAgainForInputUntilHeEnteredADimensionWhichIsNotSymmetrical() {
		when(consoleReaderMock.readNextLine()).thenReturn("4X4")
											  .thenReturn("3X5");
		assertThat(gameConfigurationReader.promptForBoardDimension(), equalTo(BoardDimension.of(3, 5)));
		verify(consoleReaderMock, times(2))
			.prompt(eq("Enter the board configuration in the following format: 4X5. The board configuration can not be symmetrical and has to be greater than 3X4 or 4X3."));
	}
	
	@Test
	public void testThatTheBoardDimensionIsRedFirstAndAlsoCheckThatTheUserGetsPromptedAgainForInputUntilHeEnteredTheDimensionInTheRightFormat() {
		when(consoleReaderMock.readNextLine()).thenReturn("aX5")
											  .thenReturn("3XF")
											  .thenReturn("4X5");
		assertThat(gameConfigurationReader.promptForBoardDimension(), equalTo(BoardDimension.of(4, 5)));
		verify(consoleReaderMock, times(3))
			.prompt(eq("Enter the board configuration in the following format: 4X5. The board configuration can not be symmetrical and has to be greater than 3X4 or 4X3."));
	}
	
	
}
