package com.example.result;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xddf.usermodel.chart.BarDirection;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.LegendPosition;
import org.apache.poi.xddf.usermodel.chart.XDDFBarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartLegend;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ResultExporter {

    public void exportToExcel(List<Result> results, String filePath) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {

            // group results by generatorName
            Map<String, List<Result>> groupedResults = results.stream()
                    .collect(Collectors.groupingBy(Result::getGeneratorName));

            for (String generator : groupedResults.keySet()) {
                List<Result> groupList = groupedResults.get(generator);

                // create a new sheet named with the generator name
                Sheet sheet = workbook.createSheet(generator);
                int rowNum = 0;
                
                // collect all framesizes
                List<Integer> frameSizes = new ArrayList<>();
                frameSizes.add(0); 
                frameSizes.addAll(groupList.stream()
                        .map(r -> r.getData().getFrameSize())
                        .distinct()
                        .sorted()
                        .toList());
                frameSizes.add(180); 
            
                // collect all algorithm names
                List<String> algorithms = groupList.stream()
                        .map(Result::getAlgorithmName)
                        .distinct()
                        .toList();

                Map<String, Map<String, Map<Integer, Integer>>> metricMap = new HashMap<>();
                metricMap.put("Page Faults", new HashMap<>());
                metricMap.put("Disk Writes", new HashMap<>());
                metricMap.put("Interrupts", new HashMap<>());

                // write into the metric map with values
                for (Result r : groupList) {
                    String algo = r.getAlgorithmName();
                    int frameSize = r.getData().getFrameSize();

                    metricMap.get("Page Faults")
                            .computeIfAbsent(algo, k -> new HashMap<>())
                            .put(frameSize, r.getData().getPageFaults());

                    metricMap.get("Disk Writes")
                            .computeIfAbsent(algo, k -> new HashMap<>())
                            .put(frameSize, r.getData().getDiskWrites());

                    metricMap.get("Interrupts")
                            .computeIfAbsent(algo, k -> new HashMap<>())
                            .put(frameSize, r.getData().getInterrupts());
                }

                // create a table and a chart for each metric
                for (String metric : List.of("Page Faults", "Disk Writes", "Interrupts")) {

                    Row titleRow = sheet.createRow(rowNum++);
                    titleRow.createCell(0).setCellValue(metric);

                    Row header = sheet.createRow(rowNum++);
                    header.createCell(0).setCellValue("FrameSize");
                    for (int i = 0; i < frameSizes.size(); i++) {
                        header.createCell(i + 1).setCellValue(frameSizes.get(i));
                    }

                    int algoStartRow = rowNum;
                    for (String algo : algorithms) {
                        Row row = sheet.createRow(rowNum++);
                        row.createCell(0).setCellValue(algo);
                        for (int i = 0; i < frameSizes.size(); i++) {
                            int frameSize = frameSizes.get(i);
                            int value = metricMap.get(metric)
                                    .getOrDefault(algo, new HashMap<>())
                                    .getOrDefault(frameSize, 0);
                            row.createCell(i + 1).setCellValue(value);
                        }
                    }

                    // create chart beside statistics
                    int chartRow = rowNum + 1;
                    XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
                    XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, chartRow, 8, chartRow + 20);
                    XSSFChart chart = drawing.createChart(anchor);
                    chart.setTitleText(metric);
                    chart.setTitleOverlay(false);

                    XDDFChartLegend legend = chart.getOrAddLegend();
                    legend.setPosition(LegendPosition.TOP_RIGHT);

                    // x-axis and y axis
                    XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
                    bottomAxis.setTitle("FrameSize");
                    XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
                    leftAxis.setTitle(metric);
                    // create chart
                    XDDFChartData data = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
                    ((XDDFBarChartData) data).setBarDirection(BarDirection.COL);
                    // x-axis framesize
                    XDDFDataSource<Double> categories = XDDFDataSourcesFactory.fromNumericCellRange(
                            (XSSFSheet) sheet,
                            new CellRangeAddress(header.getRowNum(), header.getRowNum(), 1, frameSizes.size())
                    );

                    // y-axis = algorithm name
                    for (int i = 0; i < algorithms.size(); i++) {
                        XDDFNumericalDataSource<Double> values = XDDFDataSourcesFactory.fromNumericCellRange(
                                (XSSFSheet) sheet,
                                new CellRangeAddress(algoStartRow + i, algoStartRow + i, 1, frameSizes.size())
                        );
                        XDDFChartData.Series series = data.addSeries(categories, values);
                        series.setTitle(algorithms.get(i), null); // Algorithm name
                    }

                    chart.plot(data);

                    // padding some space
                    rowNum = chartRow + 22;
                }
            }

            // write into excel file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }
        }
    }
}
