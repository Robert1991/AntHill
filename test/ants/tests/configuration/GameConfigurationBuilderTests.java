package ants.tests.configuration;
import org.junit.Before;
import org.junit.Test;

import ants.board.dimension.BoardDimension;
import ants.runner.GameConfiguration;
import ants.runner.GameConfigurationBuilder;
import ants.runner.GameConfigurationReader;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class GameConfigurationBuilderTests {
	private GameConfigurationReader gameConfigurationReaderMock;
	private GameConfigurationBuilder gameConfigurationBuilder;

	@Before
	public void setupTestedInstanceAndMocks() {
		gameConfigurationReaderMock = mock(GameConfigurationReader.class);
		gameConfigurationBuilder = new GameConfigurationBuilder(gameConfigurationReaderMock);
	}
	
	@Test
	public void testThatTheGameConfigurationIsAssemledByTheReturnsTheUserEnters() {
		when(gameConfigurationReaderMock.promptForBoardDimension()).thenReturn(
				BoardDimension.of(3, 5));
		when(gameConfigurationReaderMock.promptForIntegerGreaterThanZero(
				eq("Enter the count of food cells on the board. It has to be at least 1.")))
					.thenReturn(12);
		when(gameConfigurationReaderMock.promptForIntegerGreaterThanZero(
				eq("Enter the maximum food count per food cell. It has to be at least 1.")))
					.thenReturn(10);
		when(gameConfigurationReaderMock.promptForIntegerGreaterThanZero(
				eq("Enter the count of the ants which will be present in the ant hill. The count has to be greater or equal than 1.")))
		.thenReturn(23);
		assertThat(
				gameConfigurationBuilder.promptForGameConfiguration(), 
				equalTo(new GameConfiguration(BoardDimension.of(3, 5), 10, 12, 23)));
	}
}
