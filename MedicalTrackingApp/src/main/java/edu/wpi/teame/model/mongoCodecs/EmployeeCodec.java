package edu.wpi.teame.model.mongoCodecs;

import edu.wpi.teame.model.Employee;
import edu.wpi.teame.model.enums.DepartmentType;
import edu.wpi.teame.model.enums.EmployeeType;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class EmployeeCodec implements Codec<Employee> {
    @Override
    public Employee decode(BsonReader reader, DecoderContext decoderContext) {
        Employee employee = new Employee();
        reader.readStartDocument();

        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String fieldName = reader.readName();
            if (fieldName.equals("_id")) {
                employee.setId(reader.readInt32());
            } else if(fieldName.equals("department")) {
                employee.setDepartment(DepartmentType.values()[reader.readInt32()]);
            } else if(fieldName.equals("name")) {
                employee.setName(reader.readString());
            } else if(fieldName.equals("type")) {
                employee.setType(EmployeeType.values()[reader.readInt32()]);
            } else if(fieldName.equals("isDeleted")) {
                employee.setDeleted(reader.readInt32() == 1);
            }
        }

        reader.readEndDocument();

        return employee;
    }

    @Override
    public void encode(BsonWriter writer, Employee value, EncoderContext encoderContext) {
        writer.writeStartDocument();
        writer.writeInt32("_id", value.getId());
        writer.writeInt32("department", value.getDepartment().ordinal());
        writer.writeString("name", value.getName());
        writer.writeInt32("type", value.getType().ordinal());
        writer.writeInt32("isDeleted", value.getIsDeleted() ? 1 : 0);
        writer.writeEndDocument();
    }

    @Override
    public Class<Employee> getEncoderClass() {
        return Employee.class;
    }
}
