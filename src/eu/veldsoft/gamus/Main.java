package eu.veldsoft.gamus;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Application single entry point class.
 * 
 * @author Todor Balabanov
 */
public class Main {
	/**
	 * Single entry point method.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		Object data[][][] = null;

		try {
			POIFSFileSystem system = new POIFSFileSystem(new FileInputStream(args[0]));
			HSSFWorkbook book = new HSSFWorkbook(system);

			int size = 0;
			Iterator<Sheet> s = book.iterator();
			while (s.hasNext() == true) {
				s.next();
				size++;
			}
			data = new Object[size][][];

			int w = 0;
			s = book.iterator();
			while (s.hasNext() == true) {
				Sheet sheet = s.next();
				data[w] = new Object[sheet.getPhysicalNumberOfRows()][];
				for (int r = 0; r < sheet.getPhysicalNumberOfRows(); r++) {
					Row row = sheet.getRow(r);
					data[w][r] = new Object[row.getLastCellNum()];
					for (int c = 0; c < row.getLastCellNum(); c++) {
						Cell cell = row.getCell(c);
						if (cell == null) {
							data[w][r][c] = "";
						} else if (cell.getCellTypeEnum() == CellType.STRING) {
							data[w][r][c] = cell.toString();
						} else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
							data[w][r][c] = (int) (Double.valueOf(cell.toString()).doubleValue());
						}
					}
				}
				w++;
			}
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}

		// System.out.println(Arrays.deepToString(data));

		WorkUnit work = new WorkUnit(data[5]);

		work.load();
		work.adjustRandomTimes(100, 1000);
		System.out.println(Arrays.toString(work.simulate(100000)));
		System.out.println(work.numberOfUndoneOperations());
		System.out.println(work.report());
		System.out.println(work.totalTimeUsed());
	}
}
