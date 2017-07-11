package eu.veldsoft.gamus;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Parser for the input data from Excel to 3D array of objects.
 * 
 * @author Kristian Radkov
 */
public class DataParser {
	/**
	 * Path to the file we need to open.
	 */
	private String name;

	/**
	 * Constructor with all parameters.
	 * 
	 * @param name
	 *            Input file name.
	 */
	public DataParser(String name) {
		this.name = name;
	}

	/**
	 * Parses data from a given source.
	 * 
	 * @return Array of objects with problem information.
	 */
	public Object[][][] parse() {
		Object data[][][] = null;

		POIFSFileSystem system = null;
		HSSFWorkbook book = null;
		try {
			system = new POIFSFileSystem(new FileInputStream(this.name));
			book = new HSSFWorkbook(system);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		/*
		 * Calculate the number of sheets in the given workbook.
		 */
		int size = 0;
		Iterator<Sheet> s = book.iterator();
		while (s.hasNext() == true) {
			s.next();
			size++;
		}
		data = new Object[size][][];

		/*
		 * Iterate over the sheets and extract problems information.
		 */
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

		/*
		 * Close the book.
		 */
		try {
			book.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return data;
	}
}
