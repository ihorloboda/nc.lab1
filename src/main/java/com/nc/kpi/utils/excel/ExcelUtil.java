package com.nc.kpi.utils.excel;

import com.nc.kpi.analyzer.ArraySortStatistics;
import com.nc.kpi.sorters.Sorter;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.charts.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.charts.XSSFChartLegend;

import java.io.*;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Singleton util that imports statistics to xlsx-file.
 *
 * @author Ihor Loboda
 */
public class ExcelUtil {
    private static ExcelUtil instance;
    @Getter
    @Setter
    private String fileName = "stat.xlsx";

    private ExcelUtil() {
    }

    public static ExcelUtil getInstance() {
        if (instance == null) {
            instance = new ExcelUtil();
        }
        return instance;
    }

    /**
     * Writes statistics to xlsx-file.
     *
     * @param stat statistics that will be written to file.
     */
    public void writeStatToXls(ArraySortStatistics stat) {
        if (stat == null) {
            throw new IllegalArgumentException("Statistics is null");
        }
        Workbook workbook = new XSSFWorkbook();
        for (Method filler : stat.getFillers()) {
            createSheet(workbook, filler, stat.getFillerStatistics(filler), stat.getCountOfElementsValues());
        }
        writeWorkBook(workbook);
    }

    private void createSheet(Workbook workbook, Method filler, Map<Class<? extends Sorter>, List<Long>> stat,
                             List<Long> countOfElementsValues) {
        Sheet sheet = workbook.createSheet(filler.getName());
        XSSFDrawing xssfDrawing = (XSSFDrawing) sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = xssfDrawing.createAnchor(0, 0, 0, 0,
                countOfElementsValues.size() + 1, 0, 15, 20);
        XSSFChart xssfChart = xssfDrawing.createChart(anchor);
        xssfChart.setTitleText(filler.getName().replace("generate", ""));
        XSSFChartLegend legend = xssfChart.getOrCreateLegend();
        legend.setPosition(LegendPosition.BOTTOM);
        LineChartData data = xssfChart.getChartDataFactory().createLineChartData();
        ChartAxis bottomAxis = xssfChart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
        ValueAxis leftAxis = xssfChart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
        leftAxis.setLogBase(1000);
        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
        leftAxis.setMinimum(1000);
        createElementsRow(sheet, countOfElementsValues);
        ChartDataSource<Number> xs = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(0, 0,
                1, countOfElementsValues.size()));
        int rowIndex = 1;
        for (Class<? extends Sorter> sorter : stat.keySet()) {
            createTimeRow(sheet, rowIndex, sorter.getSimpleName(), stat.get(sorter));
            ChartDataSource<Number> ys = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(rowIndex, rowIndex,
                    1, stat.get(sorter).size()));
            LineChartSeries chartSeries = data.addSeries(xs, ys);
            chartSeries.setTitle(sorter.getSimpleName());
            rowIndex++;
        }
        xssfChart.plot(data, new ChartAxis[]{bottomAxis, leftAxis});
    }

    private void createTimeRow(Sheet sheet, int rowIndex, String sorterName, List<Long> sortTime) {
        Row timeRow = sheet.createRow(rowIndex);
        timeRow.createCell(0).setCellValue(sorterName);
        for (int i = 0; i < sortTime.size(); i++) {
            timeRow.createCell(i + 1).setCellValue(sortTime.get(i));
        }
    }

    private void createElementsRow(Sheet sheet, List<Long> countOfElements) {
        Row elementsRow = sheet.createRow(0);
        elementsRow.createCell(0).setCellValue("Elements:");
        for (int i = 0; i < countOfElements.size(); i++) {
            Cell cell = elementsRow.createCell(i + 1);
            cell.setCellValue(countOfElements.get(i));
        }
    }

    private void writeWorkBook(Workbook workbook) {
        try (OutputStream out = new FileOutputStream(new File(fileName))) {
            workbook.write(out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
