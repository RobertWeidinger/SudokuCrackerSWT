import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ 
	SudokuCrackerSWTTest.class, 
	SudokuHelperTest.class, 
	SudokuModelTest.class, 
	SudokuViewTest.class, 
	SudokuFileReaderTest.class,
	SudokuFieldValuesTest.class,
	SudokuSiblingCrackerTest.class,
	SudokuEntryTest.class
	})
public class AllTests {

}
