package edu.wpi.teame.db.objectManagers;

import com.opencsv.exceptions.CsvValidationException;
import edu.wpi.teame.model.enums.DataBaseObjectType;
import edu.wpi.teame.model.serviceRequests.MedicalEquipmentServiceRequest;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class ExternalPatientTransportationSRManager extends ObjectManager<MedicalEquipmentServiceRequest>{
    public ExternalPatientTransportationSRManager() {
        super(DataBaseObjectType.ExternalPatientTransportation);
    }

    @Override
    public void readCSV(String inputFileName) throws IOException, SQLException, CsvValidationException, ParseException {

    }

    @Override
    public void writeToCSV(String outputFileName) throws IOException, SQLException {

    }
}
