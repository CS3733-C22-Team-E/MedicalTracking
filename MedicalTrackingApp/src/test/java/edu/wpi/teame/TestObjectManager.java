package edu.wpi.teame;

import edu.wpi.teame.db.ISQLSerializable;
import edu.wpi.teame.db.objectManagers.EmployeeManager;
import edu.wpi.teame.db.objectManagers.LocationManager;
import edu.wpi.teame.db.objectManagers.ObjectManager;
import edu.wpi.teame.db.objectManagers.PatientManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TestObjectManager {
  public ObjectManager objectManager;

  public TestObjectManager(ObjectManager manager) {
    objectManager = manager;
  }

  @BeforeAll
  public void setUp() {
    
  }

  @Test
  public void testGetAll() {

  }

  @Test
  public void testInsert() {

  }

  @Test
  public void testRemove() {

  }

  @AfterAll
  public void cleanDBTables() {

  }

  @Parameterized.Parameters
  public static List<ObjectManager> objectManagerInstances() throws SQLException {
    return Arrays.asList(new ObjectManager[] {
            new LocationManager(),
            new EmployeeManager(),
            new PatientManager()
    });
  }
}
