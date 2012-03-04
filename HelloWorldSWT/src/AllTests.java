import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ 
	SudokuCrackerSWTTest.class, 
	SudokuHelperTest.class, 
	SudokuModelTest.class, 
	SudokuViewTest.class, 
	SudokuFileReaderTest.class 
	})
public class AllTests {

}
