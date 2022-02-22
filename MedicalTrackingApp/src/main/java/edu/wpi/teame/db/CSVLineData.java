package edu.wpi.teame.db;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CSVLineData {
  private List<String> headers = null;
  private CSVReader csvReader = null;
  private String[] parsedData = null;

  public CSVLineData(CSVReader reader) throws IOException, CsvValidationException {
    String[] headerStrings = reader.readNext();
    if (headerStrings == null || headerStrings.length == 0) {
      return;
    }

    this.headers = new ArrayList<String>();
    this.headers.addAll(List.of(headerStrings));
    this.csvReader = reader;
  }

  public boolean readNext() throws CsvValidationException, IOException {
    parsedData = csvReader.readNext();
    if (parsedData == null) {
      return false;
    }
    return parsedData.length != 0;
  }

  public boolean readHeaders() {
    return headers != null && headers.size() > 0;
  }

  public Date getColumnDate(String columnName) throws ParseException {
    String columnString = getSanitizedString(columnName);
    if (columnString == null) {
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
    String columnString = getSanitizedString(columnName);
    if (columnString == null) {
      return false;
    }
    return Boolean.parseBoolean(columnString);
  }

  public int getColumnInt(String columnName) {
    String columnString = getSanitizedString(columnName);
    if (columnString == null) {
      return 0;
    }
    return Integer.parseInt(columnString);
  }

  public String getColumnString(String columnName) {
    return getSanitizedString(columnName);
  }

  private String getSanitizedString(String columnName) {
    String columnString = parsedData[headers.indexOf(columnName)].trim();
    if (columnString.isEmpty() || columnString.isBlank()) {
      return null;
    }
    return columnString;
  }
}
