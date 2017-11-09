package com.nc.kpi.utils.excel;

import com.nc.kpi.analyzer.Analyzer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class ExcelUtilTest {
    private final int MAX_COUNT_OF_ELEMENTS = (int) 10E4;
    private ExcelUtil excelUtil;
    private Analyzer analyzer;
    private File file;

    @Before
    public void init() {
        excelUtil = ExcelUtil.getInstance();
        analyzer = Analyzer.getInstance();
        file = new File(excelUtil.getFileName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void writeStatToXls() {
        excelUtil.writeStatToXls(analyzer.getFullStat(MAX_COUNT_OF_ELEMENTS));
        Assert.assertTrue(file.exists());
        excelUtil.writeStatToXls(null);
    }
}