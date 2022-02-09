package edu.wpi.teame.db;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class CSVLineData {
  private List<String> headers = null;
  private CSVReader csvReader = null;
  private String[] parsedData = null;

  public CSVLineData(CSVReader reader) throws IOException, CsvValidationException {
    headers = Arrays.asList(reader.readNext());
    this.csvReader = reader;
  }

  public String getColumnString(String columnName) {
    return parsedData[headers.indexOf(columnName)];
  }

  public Date getColumnDate(String columnName) throws ParseException {
    long time =
        new SimpleDateFormat("yyyy-MM-dd").parse(parsedData[headers.indexOf(columnName)]).getTime();
    return new Date(time);
  }

  public boolean getColumnBoolean(String columnName) {
    return Boolean.parseBoolean(parsedData[headers.indexOf(columnName)]);
  }

  public int getColumnInt(String columnName) {
    return Integer.parseInt(parsedData[headers.indexOf(columnName)]);
  }

  public List<String> getHeaders() {
    return headers;
  }

  public String[] getParsedData() {
    return parsedData;
  }

  public void setParsedData(String[] parsedData) {
    this.parsedData = parsedData;
  }
}
