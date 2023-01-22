package com.bebetto.financemanager.utility;

import java.util.Objects;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileUtility {

	public static class ExcelFileUtilityBuilder {

		private String fileName;
		private ExcelType excelType;

		public ExcelFileUtilityBuilder() {
			super();
		}

		public ExcelFileUtility build() {
			final ExcelFileUtility excelFileUtility = new ExcelFileUtility();
			excelFileUtility.setExcelType(getExcelType());
			excelFileUtility.setFileName(getFileName());
			return excelFileUtility;
		}

		public ExcelType getExcelType() {
			if (this.excelType == null) {
				this.excelType = ExcelType.XLSX;
			}
			return this.excelType;
		}

		public String getFileName() {
			if (this.fileName == null) {
				this.fileName = CommonUtility.getCurrentDateTimeString();
			}
			return this.fileName + "." + getExcelType().getExtension();
		}

		public ExcelFileUtilityBuilder setExcelType(final ExcelType excelType) {
			this.excelType = excelType;
			return this;
		}

		public ExcelFileUtilityBuilder setFileName(final String fileName) {
			this.fileName = fileName;
			return this;
		}

	}

	private String fileName;
	private ExcelType excelType;
	private Workbook workbook;

	public ExcelFileUtility() {
		super();
	}

	public ExcelType getExcelType() {
		return this.excelType;
	}

	public String getFileName() {
		return this.fileName;
	}

	public Workbook getWorkbook() {
		if (Objects.nonNull(this.workbook)) {
			return this.workbook;
		}
		final ExcelType excelTypeTmp = getExcelType();
		if (excelTypeTmp == ExcelType.XLS) {
			this.workbook = new HSSFWorkbook();
			return this.workbook;
		}
		this.workbook = new XSSFWorkbook();
		return this.workbook;
	}

	public void setExcelType(final ExcelType excelType) {
		this.excelType = excelType;
	}

	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

}

enum ExcelType {

	XLSX("xlsx"), XLS("xls");

	private final String extension;

	private ExcelType(final String extension) {
		this.extension = extension;
	}

	public String getExtension() {
		return this.extension;
	}

}
