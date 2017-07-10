package eu.veldsoft.gamus;

import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class DataParser {

	/**
	 * Path to the file we need to open.
	 */
	private String name;

	public DataParser(String name) {
		this.name = name;
	}
	
	/**
	 * Parses data from a given path.
	 * 
	 * @return Object array of data that was parsed.
	 */
	public Object[][][] parse() {
		Object data[][][] = null;

		try {
			POIFSFileSystem system = new POIFSFileSystem(new FileInputStream(this.name));
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
			book.close();
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
		
		return data;
	}
}
