package edu.wpi.teame.db;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
    String dateString = parsedData[headers.indexOf(columnName)];
    if (dateString.equals("")) {
      return null;
    }
    long time;
    if (dateString.length() > 11) {
      time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString).getTime();
    } else {
      time = new SimpleDateFormat("yyyy-MM-dd").parse(dateString).getTime();
    }
    return new Date(time);
  }

  public boolean getColumnBoolean(String columnName) {
    return Boolean.parseBoolean(parsedData[headers.indexOf(columnName)]);
  }

  public int getColumnInt(String columnName) {
    int colIndex = headers.indexOf(columnName);
    if (Objects.equals(parsedData[colIndex], "")) {
      return 0;
    }
    return Integer.parseInt(parsedData[colIndex].replace(" ", ""));
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
