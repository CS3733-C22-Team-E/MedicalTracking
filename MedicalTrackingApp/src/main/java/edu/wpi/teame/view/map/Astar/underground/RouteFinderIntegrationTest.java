package edu.wpi.teame.view.map.Astar.underground;

// import static org.assertj.core.api.Assertions.assertThat;

import edu.wpi.teame.model.Location;
import edu.wpi.teame.view.map.Astar.Graph;
import edu.wpi.teame.view.map.Astar.RouteFinder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

// import org.junit.Before;
// import org.junit.Test;

@Slf4j
public class RouteFinderIntegrationTest {

  private Graph<Location> locationGraph;

  private RouteFinder<Location> routeFinder;

  // @Before
  public void setUp() throws Exception {
    Set<Location> locations = new HashSet<>();
    Map<Integer, Set<Integer>> connections = new HashMap<>();

    //    locations.add(new Location("1", "Acton Town", 51.5028, -0.2801));
    //    locations.add(new Location("2", "Aldgate", 51.5143, -0.0755));
    //    locations.add(new Location("3", "Aldgate East", 51.5154, -0.0726));
    //    locations.add(new Location("4", "All Saints", 51.5107, -0.013));
    //    locations.add(new Location("5", "Alperton", 51.5407, -0.2997));
    //    locations.add(new Location("6", "Amersham", 51.6736, -0.607));
    //    locations.add(new Location("7", "Angel", 51.5322, -0.1058));
    //    locations.add(new Location("8", "Archway", 51.5653, -0.1353));
    //    locations.add(new Location("9", "Arnos Grove", 51.6164, -0.1331));
    //    locations.add(new Location("10", "Arsenal", 51.5586, -0.1059));
    //    locations.add(new Location("11", "Baker Street", 51.5226, -0.1571));
    //    locations.add(new Location("12", "Balham", 51.4431, -0.1525));
    //    locations.add(new Location("13", "Bank", 51.5133, -0.0886));
    //    locations.add(new Location("14", "Barbican", 51.5204, -0.0979));
    //    locations.add(new Location("15", "Barking", 51.5396, 0.081));
    //    locations.add(new Location("16", "Barkingside", 51.5856, 0.0887));
    //    locations.add(new Location("17", "Barons Court", 51.4905, -0.2139));
    //    locations.add(new Location("18", "Bayswater", 51.5121, -0.1879));
    //    locations.add(new Location("19", "Beckton", 51.5148, 0.0613));
    //    locations.add(new Location("20", "Beckton Park", 51.5087, 0.055));
    //    locations.add(new Location("21", "Becontree", 51.5403, 0.127));
    //    locations.add(new Location("22", "Belsize Park", 51.5504, -0.1642));
    //    locations.add(new Location("23", "Bermondsey", 51.4979, -0.0637));
    //    locations.add(new Location("24", "Bethnal Green", 51.527, -0.0549));
    //    locations.add(new Location("25", "Blackfriars", 51.512, -0.1031));
    //    locations.add(new Location("26", "Blackhorse Road", 51.5867, -0.0417));
    //    locations.add(new Location("27", "Blackwall", 51.5079, -0.0066));
    //    locations.add(new Location("28", "Bond Street", 51.5142, -0.1494));
    //    locations.add(new Location("29", "Borough", 51.5011, -0.0943));
    //    locations.add(new Location("30", "Boston Manor", 51.4956, -0.325));
    //    locations.add(new Location("31", "Bounds Green", 51.6071, -0.1243));
    //    locations.add(new Location("32", "Bow Church", 51.5273, -0.0208));
    //    locations.add(new Location("33", "Bow Road", 51.5269, -0.0247));
    //    locations.add(new Location("34", "Brent Cross", 51.5766, -0.2136));
    //    locations.add(new Location("35", "Brixton", 51.4627, -0.1145));
    //    locations.add(new Location("36", "Bromley-By-Bow", 51.5248, -0.0119));
    //    locations.add(new Location("37", "Buckhurst Hill", 51.6266, 0.0471));
    //    locations.add(new Location("38", "Burnt Oak", 51.6028, -0.2641));
    //    locations.add(new Location("39", "Caledonian Road", 51.5481, -0.1188));
    //    locations.add(new Location("40", "Camden Town", 51.5392, -0.1426));
    //    locations.add(new Location("41", "Canada Water", 51.4982, -0.0502));
    //    locations.add(new Location("42", "Canary Wharf", 51.5051, -0.0209));
    //    locations.add(new Location("43", "Canning Town", 51.5147, 0.0082));
    //    locations.add(new Location("44", "Cannon Street", 51.5113, -0.0904));
    //    locations.add(new Location("45", "Canons Park", 51.6078, -0.2947));
    //    locations.add(new Location("46", "Chalfont & Latimer", 51.6679, -0.561));
    //    locations.add(new Location("47", "Chalk Farm", 51.5441, -0.1538));
    //    locations.add(new Location("48", "Chancery Lane", 51.5185, -0.1111));
    //    locations.add(new Location("49", "Charing Cross", 51.508, -0.1247));
    //    locations.add(new Location("50", "Chesham", 51.7052, -0.611));
    //    locations.add(new Location("51", "Chigwell", 51.6177, 0.0755));
    //    locations.add(new Location("52", "Chiswick Park", 51.4946, -0.2678));
    //    locations.add(new Location("53", "Chorleywood", 51.6543, -0.5183));
    //    locations.add(new Location("54", "Clapham Common", 51.4618, -0.1384));
    //    locations.add(new Location("55", "Clapham North", 51.4649, -0.1299));
    //    locations.add(new Location("56", "Clapham South", 51.4527, -0.148));
    //    locations.add(new Location("57", "Cockfosters", 51.6517, -0.1496));
    //    locations.add(new Location("58", "Colindale", 51.5955, -0.2502));
    //    locations.add(new Location("59", "Colliers Wood", 51.418, -0.1778));
    //    locations.add(new Location("60", "Covent Garden", 51.5129, -0.1243));
    //    locations.add(new Location("61", "Crossharbour & London Arena", 51.4957, -0.0144));
    //    locations.add(new Location("62", "Croxley", 51.647, -0.4412));
    //    locations.add(new Location("63", "Custom House", 51.5095, 0.0276));
    //    locations.add(new Location("64", "Cutty Sark", 51.4827, -0.0096));
    //    locations.add(new Location("65", "Cyprus", 51.5085, 0.064));
    //    locations.add(new Location("66", "Dagenham East", 51.5443, 0.1655));
    //    locations.add(new Location("67", "Dagenham Heathway", 51.5417, 0.1469));
    //    locations.add(new Location("68", "Debden", 51.6455, 0.0838));
    //    locations.add(new Location("69", "Deptford Bridge", 51.474, -0.0216));
    //    locations.add(new Location("70", "Devons Road", 51.5223, -0.0173));
    //    locations.add(new Location("71", "Dollis Hill", 51.552, -0.2387));
    //    locations.add(new Location("72", "Ealing Broadway", 51.5152, -0.3017));
    //    locations.add(new Location("73", "Ealing Common", 51.5101, -0.2882));
    //    locations.add(new Location("74", "Earl's Court", 51.492, -0.1973));
    //    locations.add(new Location("75", "Eastcote", 51.5765, -0.397));
    //    locations.add(new Location("76", "East Acton", 51.5168, -0.2474));
    //    locations.add(new Location("77", "East Finchley", 51.5874, -0.165));
    //    locations.add(new Location("78", "East Ham", 51.5394, 0.0518));
    //    locations.add(new Location("79", "East India", 51.5093, -0.0021));
    //    locations.add(new Location("80", "East Putney", 51.4586, -0.2112));
    //    locations.add(new Location("81", "Edgware", 51.6137, -0.275));
    //    locations.add(new Location("82", "Edgware Road (B)", 51.5199, -0.1679));
    //    locations.add(new Location("83", "Edgware Road (C)", 51.5203, -0.17));
    //    locations.add(new Location("84", "Elephant & Castle", 51.4943, -0.1001));
    //    locations.add(new Location("85", "Elm Park", 51.5496, 0.1977));
    //    locations.add(new Location("86", "Elverson Road", 51.4693, -0.0174));
    //    locations.add(new Location("87", "Embankment", 51.5074, -0.1223));
    //    locations.add(new Location("88", "Epping", 51.6937, 0.1139));
    //    locations.add(new Location("89", "Euston", 51.5282, -0.1337));
    //    locations.add(new Location("90", "Euston Square", 51.526, -0.1359));
    //    locations.add(new Location("91", "Fairlop", 51.596, 0.0912));
    //    locations.add(new Location("92", "Farringdon", 51.5203, -0.1053));
    //    locations.add(new Location("93", "Finchley Central", 51.6012, -0.1932));
    //    locations.add(new Location("94", "Finchley Road", 51.5472, -0.1803));
    //    locations.add(new Location("95", "Finsbury Park", 51.5642, -0.1065));
    //    locations.add(new Location("96", "Fulham Broadway", 51.4804, -0.195));
    //    locations.add(new Location("97", "Gallions Reach", 51.5096, 0.0716));
    //    locations.add(new Location("98", "Gants Hill", 51.5765, 0.0663));
    //    locations.add(new Location("99", "Gloucester Road", 51.4945, -0.1829));
    //    locations.add(new Location("100", "Golders Green", 51.5724, -0.1941));
    //    locations.add(new Location("101", "Goldhawk Road", 51.5018, -0.2267));
    //    locations.add(new Location("102", "Goodge Street", 51.5205, -0.1347));
    //    locations.add(new Location("103", "Grange Hill", 51.6132, 0.0923));
    //    locations.add(new Location("104", "Great Portland Street", 51.5238, -0.1439));
    //    locations.add(new Location("105", "Greenford", 51.5423, -0.3456));
    //    locations.add(new Location("106", "Greenwich", 51.4781, -0.0149));
    //    locations.add(new Location("107", "Green Park", 51.5067, -0.1428));
    //    locations.add(new Location("108", "Gunnersbury", 51.4915, -0.2754));
    //    locations.add(new Location("109", "Hainault", 51.603, 0.0933));
    //    locations.add(new Location("110", "Hammersmith", 51.4936, -0.2251));
    //    locations.add(new Location("111", "Hampstead", 51.5568, -0.178));
    //    locations.add(new Location("112", "Hanger Lane", 51.5302, -0.2933));
    //    locations.add(new Location("113", "Harlesden", 51.5362, -0.2575));
    //    locations.add(new Location("114", "Harrow & Wealdston", 51.5925, -0.3351));
    //    locations.add(new Location("115", "Harrow-on-the-Hill", 51.5793, -0.3366));
    //    locations.add(new Location("116", "Hatton Cross", 51.4669, -0.4227));
    //    locations.add(new Location("117", "Heathrow Terminals 1, 2 & 3", 51.4713, -0.4524));
    //    locations.add(new Location("118", "Heathrow Terminal 4", 51.4598, -0.4476));
    //    locations.add(new Location("119", "Hendon Central", 51.5829, -0.2259));
    //    locations.add(new Location("120", "Heron Quays", 51.5033, -0.0215));
    //    locations.add(new Location("121", "High Barnet", 51.6503, -0.1943));
    //    locations.add(new Location("122", "High Street Kensington", 51.5009, -0.1925));
    //    locations.add(new Location("123", "Highbury & Islington", 51.546, -0.104));
    //    locations.add(new Location("124", "Highgate", 51.5777, -0.1458));
    //    locations.add(new Location("125", "Hillingdon", 51.5538, -0.4499));
    //    locations.add(new Location("126", "Holborn", 51.5174, -0.12));
    //    locations.add(new Location("127", "Holland Park", 51.5075, -0.206));
    //    locations.add(new Location("128", "Holloway Road", 51.5526, -0.1132));
    //    locations.add(new Location("129", "Hornchurch", 51.5539, 0.2184));
    //    locations.add(new Location("130", "Hounslow Central", 51.4713, -0.3665));
    //    locations.add(new Location("131", "Hounslow East", 51.4733, -0.3564));
    //    locations.add(new Location("132", "Hounslow West", 51.4734, -0.3855));
    //    locations.add(new Location("133", "Hyde Park Corner", 51.5027, -0.1527));
    //    locations.add(new Location("134", "Ickenham", 51.5619, -0.4421));
    //    locations.add(new Location("135", "Island Gardens", 51.4871, -0.0101));
    //    locations.add(new Location("136", "Kennington", 51.4884, -0.1053));
    //    locations.add(new Location("137", "Kensal Green", 51.5304, -0.225));
    //    locations.add(new Location("138", "Kensington (Olympia)", 51.4983, -0.2106));
    //    locations.add(new Location("139", "Kentish Town", 51.5507, -0.1402));
    //    locations.add(new Location("140", "Kenton", 51.5816, -0.3162));
    //    locations.add(new Location("141", "Kew Gardens", 51.477, -0.285));
    //    locations.add(new Location("142", "Kilburn", 51.5471, -0.2047));
    //    locations.add(new Location("143", "Kilburn Park", 51.5351, -0.1939));
    //    locations.add(new Location("144", "Kingsbury", 51.5846, -0.2786));
    //    locations.add(new Location("145", "King's Cross St. Pancras", 51.5308, -0.1238));
    //    locations.add(new Location("146", "Knightsbridge", 51.5015, -0.1607));
    //    locations.add(new Location("147", "Ladbroke Grove", 51.5172, -0.2107));
    //    locations.add(new Location("148", "Lambeth North", 51.4991, -0.1115));
    //    locations.add(new Location("149", "Lancaster Gate", 51.5119, -0.1756));
    //    locations.add(new Location("150", "Latimer Road", 51.5139, -0.2172));
    //    locations.add(new Location("151", "Leicester Square", 51.5113, -0.1281));
    //    locations.add(new Location("152", "Lewisham", 51.4657, -0.0142));
    //    locations.add(new Location("153", "Leyton", 51.5566, -0.0053));
    //    locations.add(new Location("154", "Leytonstone", 51.5683, 0.0083));
    //    locations.add(new Location("155", "Limehouse", 51.5123, -0.0396));
    //    locations.add(new Location("156", "Liverpool Street", 51.5178, -0.0823));
    //    locations.add(new Location("157", "London Bridge", 51.5052, -0.0864));
    //    locations.add(new Location("158", "Loughton", 51.6412, 0.0558));
    //    locations.add(new Location("159", "Maida Vale", 51.53, -0.1854));
    //    locations.add(new Location("160", "Manor House", 51.5712, -0.0958));
    //    locations.add(new Location("161", "Mansion House", 51.5122, -0.094));
    //    locations.add(new Location("162", "Marble Arch", 51.5136, -0.1586));
    //    locations.add(new Location("163", "Marylebone", 51.5225, -0.1631));
    //    locations.add(new Location("164", "Mile End", 51.5249, -0.0332));
    //    locations.add(new Location("165", "Mill Hill East", 51.6082, -0.2103));
    //    locations.add(new Location("166", "Monument", 51.5108, -0.0863));
    //    locations.add(new Location("167", "Moorgate", 51.5186, -0.0886));
    //    locations.add(new Location("168", "Moor Park", 51.6294, -0.432));
    //    locations.add(new Location("169", "Morden", 51.4022, -0.1948));
    //    locations.add(new Location("170", "Mornington Crescent", 51.5342, -0.1387));
    //    locations.add(new Location("171", "Mudchute", 51.4902, -0.0145));
    //    locations.add(new Location("172", "Neasden", 51.5542, -0.2503));
    //    locations.add(new Location("173", "Newbury Park", 51.5756, 0.0899));
    //    locations.add(new Location("174", "New Cross", 51.4767, -0.0327));
    //    locations.add(new Location("175", "New Cross Gate", 51.4757, -0.0402));
    //    locations.add(new Location("176", "Northfields", 51.4995, -0.3142));
    //    locations.add(new Location("177", "Northolt", 51.5483, -0.3687));
    //    locations.add(new Location("178", "Northwick Park", 51.5784, -0.3184));
    //    locations.add(new Location("179", "Northwood", 51.6111, -0.424));
    //    locations.add(new Location("180", "Northwood Hills", 51.6004, -0.4092));
    //    locations.add(new Location("181", "North Acton", 51.5237, -0.2597));
    //    locations.add(new Location("182", "North Ealing", 51.5175, -0.2887));
    //    locations.add(new Location("183", "North Greenwich", 51.5005, 0.0039));
    //    locations.add(new Location("184", "North Harrow", 51.5846, -0.3626));
    //    locations.add(new Location("185", "North Wembley", 51.5621, -0.3034));
    //    locations.add(new Location("186", "Notting Hill Gate", 51.5094, -0.1967));
    //    locations.add(new Location("187", "Oakwood", 51.6476, -0.1318));
    //    locations.add(new Location("188", "Old Street", 51.5263, -0.0873));
    //    locations.add(new Location("189", "Osterley", 51.4813, -0.3522));
    //    locations.add(new Location("190", "Oval", 51.4819, -0.113));
    //    locations.add(new Location("191", "Oxford Circus", 51.515, -0.1415));
    //    locations.add(new Location("192", "Paddington", 51.5154, -0.1755));
    //    locations.add(new Location("193", "Park Royal", 51.527, -0.2841));
    //    locations.add(new Location("194", "Parsons Green", 51.4753, -0.2011));
    //    locations.add(new Location("195", "Perivale", 51.5366, -0.3232));
    //    locations.add(new Location("196", "Picadilly Circus", 51.5098, -0.1342));
    //    locations.add(new Location("197", "Pimlico", 51.4893, -0.1334));
    //    locations.add(new Location("198", "Pinner", 51.5926, -0.3805));
    //    locations.add(new Location("199", "Plaistow", 51.5313, 0.0172));
    //    locations.add(new Location("200", "Poplar", 51.5077, -0.0173));
    //    locations.add(new Location("201", "Preston Road", 51.572, -0.2954));
    //    locations.add(new Location("202", "Prince Regent", 51.5093, 0.0336));
    //    locations.add(new Location("203", "Pudding Mill Lane", 51.5343, -0.0139));
    //    locations.add(new Location("204", "Putney Bridge", 51.4682, -0.2089));
    //    locations.add(new Location("205", "Queen's Park", 51.5341, -0.2047));
    //    locations.add(new Location("206", "Queensbury", 51.5942, -0.2861));
    //    locations.add(new Location("207", "Queensway", 51.5107, -0.1877));
    //    locations.add(new Location("208", "Ravenscourt Park", 51.4942, -0.2359));
    //    locations.add(new Location("209", "Rayners Lane", 51.5753, -0.3714));
    //    locations.add(new Location("210", "Redbridge", 51.5763, 0.0454));
    //    locations.add(new Location("211", "Regent's Park", 51.5234, -0.1466));
    //    locations.add(new Location("212", "Richmond", 51.4633, -0.3013));
    //    locations.add(new Location("213", "Rickmansworth", 51.6404, -0.4733));
    //    locations.add(new Location("214", "Roding Valley", 51.6171, 0.0439));
    //    locations.add(new Location("215", "Rotherhithe", 51.501, -0.0525));
    //    locations.add(new Location("216", "Royal Albert", 51.5084, 0.0465));
    //    locations.add(new Location("217", "Royal Oak", 51.519, -0.188));
    //    locations.add(new Location("218", "Royal Victoria", 51.5091, 0.0181));
    //    locations.add(new Location("219", "Ruislip", 51.5715, -0.4213));
    //    locations.add(new Location("220", "Ruislip Gardens", 51.5606, -0.4103));
    //    locations.add(new Location("221", "Ruislip Manor", 51.5732, -0.4125));
    //    locations.add(new Location("222", "Russell Square", 51.523, -0.1244));
    //    locations.add(new Location("223", "Seven Sisters", 51.5822, -0.0749));
    //    locations.add(new Location("224", "Shadwell", 51.5117, -0.056));
    //    locations.add(new Location("225", "Shepherd's Bush (C)", 51.5046, -0.2187));
    //    locations.add(new Location("226", "Shepherd's Bush (H)", 51.5058, -0.2265));
    //    locations.add(new Location("227", "Shoreditch", 51.5227, -0.0708));
    //    locations.add(new Location("228", "Sloane Square", 51.4924, -0.1565));
    //    locations.add(new Location("229", "Snaresbrook", 51.5808, 0.0216));
    //    locations.add(new Location("230", "Southfields", 51.4454, -0.2066));
    //    locations.add(new Location("231", "Southgate", 51.6322, -0.128));
    //    locations.add(new Location("232", "Southwark", 51.501, -0.1052));
    //    locations.add(new Location("233", "South Ealing", 51.5011, -0.3072));
    //    locations.add(new Location("234", "South Harrow", 51.5646, -0.3521));
    //    locations.add(new Location("235", "South Kensington", 51.4941, -0.1738));
    //    locations.add(new Location("236", "South Kenton", 51.5701, -0.3081));
    //    locations.add(new Location("237", "South Quay", 51.5007, -0.0191));
    //    locations.add(new Location("238", "South Ruislip", 51.5569, -0.3988));
    //    locations.add(new Location("239", "South Wimbledon", 51.4154, -0.1919));
    //    locations.add(new Location("240", "South Woodford", 51.5917, 0.0275));
    //    locations.add(new Location("241", "Stamford Brook", 51.495, -0.2459));
    //    locations.add(new Location("242", "Stanmore", 51.6194, -0.3028));
    //    locations.add(new Location("243", "Stepney Green", 51.5221, -0.047));
    //    locations.add(new Location("244", "Stockwell", 51.4723, -0.123));
    //    locations.add(new Location("245", "Stonebridge Park", 51.5439, -0.2759));
    //    locations.add(new Location("246", "Stratford", 51.5416, -0.0042));
    //    locations.add(new Location("247", "St. James's Park", 51.4994, -0.1335));
    //    locations.add(new Location("248", "St. John's Wood", 51.5347, -0.174));
    //    locations.add(new Location("249", "St. Paul's", 51.5146, -0.0973));
    //    locations.add(new Location("250", "Sudbury Hill", 51.5569, -0.3366));
    //    locations.add(new Location("251", "Sudbury Town", 51.5507, -0.3156));
    //    locations.add(new Location("252", "Surrey Quays", 51.4933, -0.0478));
    //    locations.add(new Location("253", "Swiss Cottage", 51.5432, -0.1738));
    //    locations.add(new Location("254", "Temple", 51.5111, -0.1141));
    //    locations.add(new Location("255", "Theydon Bois", 51.6717, 0.1033));
    //    locations.add(new Location("256", "Tooting Bec", 51.4361, -0.1598));
    //    locations.add(new Location("257", "Tooting Broadway", 51.4275, -0.168));
    //    locations.add(new Location("258", "Tottenham Court Road", 51.5165, -0.131));
    //    locations.add(new Location("259", "Tottenham Hale", 51.5882, -0.0594));
    //    locations.add(new Location("260", "Totteridge & Whetstone", 51.6302, -0.1791));
    //    locations.add(new Location("261", "Tower Gateway", 51.5106, -0.0743));
    //    locations.add(new Location("262", "Tower Hill", 51.5098, -0.0766));
    //    locations.add(new Location("263", "Tufnell Park", 51.5567, -0.1374));
    //    locations.add(new Location("264", "Turnham Green", 51.4951, -0.2547));
    //    locations.add(new Location("265", "Turnpike Lane", 51.5904, -0.1028));
    //    locations.add(new Location("266", "Upminster", 51.559, 0.251));
    //    locations.add(new Location("267", "Upminster Bridge", 51.5582, 0.2343));
    //    locations.add(new Location("268", "Upney", 51.5385, 0.1014));
    //    locations.add(new Location("269", "Upton Park", 51.5352, 0.0343));
    //    locations.add(new Location("270", "Uxbridge", 51.5463, -0.4786));
    //    locations.add(new Location("271", "Vauxhall", 51.4861, -0.1253));
    //    locations.add(new Location("272", "Victoria", 51.4965, -0.1447));
    //    locations.add(new Location("273", "Walthamstow Central", 51.583, -0.0195));
    //    locations.add(new Location("274", "Wanstead", 51.5775, 0.0288));
    //    locations.add(new Location("275", "Wapping", 51.5043, -0.0558));
    //    locations.add(new Location("276", "Warren Street", 51.5247, -0.1384));
    //    locations.add(new Location("277", "Warwick Avenue", 51.5235, -0.1835));
    //    locations.add(new Location("278", "Waterloo", 51.5036, -0.1143));
    //    locations.add(new Location("279", "Watford", 51.6573, -0.4177));
    //    locations.add(new Location("280", "Wembley Central", 51.5519, -0.2963));
    //    locations.add(new Location("281", "Wembley Park", 51.5635, -0.2795));
    //    locations.add(new Location("282", "Westbourne Park", 51.521, -0.2011));
    //    locations.add(new Location("283", "Westferry", 51.5097, -0.0265));
    //    locations.add(new Location("284", "Westminster", 51.501, -0.1254));
    //    locations.add(new Location("285", "West Acton", 51.518, -0.2809));
    //    locations.add(new Location("286", "West Brompton", 51.4872, -0.1953));
    //    locations.add(new Location("287", "West Finchley", 51.6095, -0.1883));
    //    locations.add(new Location("288", "West Ham", 51.5287, 0.0056));
    //    locations.add(new Location("289", "West Hampstead", 51.5469, -0.1906));
    //    locations.add(new Location("290", "West Harrow", 51.5795, -0.3533));
    //    locations.add(new Location("291", "West India Quay", 51.507, -0.0203));
    //    locations.add(new Location("292", "West Kensington", 51.4907, -0.2065));
    //    locations.add(new Location("293", "West Ruislip", 51.5696, -0.4376));
    //    locations.add(new Location("294", "Whitechapel", 51.5194, -0.0612));
    //    locations.add(new Location("295", "White City", 51.512, -0.2239));
    //    locations.add(new Location("296", "Willesden Green", 51.5492, -0.2215));
    //    locations.add(new Location("297", "Willesden Junction", 51.5326, -0.2478));
    //    locations.add(new Location("298", "Wimbledon", 51.4214, -0.2064));
    //    locations.add(new Location("299", "Wimbledon Park", 51.4343, -0.1992));
    //    locations.add(new Location("300", "Woodford", 51.607, 0.0341));
    //    locations.add(new Location("301", "Woodside Park", 51.6179, -0.1856));
    //    locations.add(new Location("302", "Wood Green", 51.5975, -0.1097));
    //
    //    connections.put("1", Stream.of("52", "73", "73", "233",
    // "264").collect(Collectors.toSet()));
    //    connections.put("2", Stream.of("156", "262", "156").collect(Collectors.toSet()));
    //    connections.put("3", Stream.of("262", "294", "156", "294").collect(Collectors.toSet()));
    //    connections.put("4", Stream.of("70", "200").collect(Collectors.toSet()));
    //    connections.put("5", Stream.of("193", "251").collect(Collectors.toSet()));
    //    connections.put("6", Stream.of("46").collect(Collectors.toSet()));
    //    connections.put("7", Stream.of("145", "188").collect(Collectors.toSet()));
    //    connections.put("8", Stream.of("124", "263").collect(Collectors.toSet()));
    //    connections.put("9", Stream.of("31", "231").collect(Collectors.toSet()));
    //    connections.put("10", Stream.of("95", "128").collect(Collectors.toSet()));
    //    connections.put(
    //        "11",
    //        Stream.of("163", "211", "83", "104", "83", "104", "28", "248", "94", "104")
    //            .collect(Collectors.toSet()));
    //    connections.put("12", Stream.of("56", "256").collect(Collectors.toSet()));
    //    connections.put(
    //        "13", Stream.of("156", "249", "224", "157", "167",
    // "278").collect(Collectors.toSet()));
    //    connections.put(
    //        "14", Stream.of("92", "167", "92", "167", "92", "167").collect(Collectors.toSet()));
    //    connections.put("15", Stream.of("78", "268", "78").collect(Collectors.toSet()));
    //    connections.put("16", Stream.of("91", "173").collect(Collectors.toSet()));
    //    connections.put("17", Stream.of("110", "292", "74", "110").collect(Collectors.toSet()));
    //    connections.put("18", Stream.of("186", "192", "186", "192").collect(Collectors.toSet()));
    //    connections.put("19", Stream.of("97").collect(Collectors.toSet()));
    //    connections.put("20", Stream.of("65", "216").collect(Collectors.toSet()));
    //    connections.put("21", Stream.of("67", "268").collect(Collectors.toSet()));
    //    connections.put("22", Stream.of("47", "111").collect(Collectors.toSet()));
    //    connections.put("23", Stream.of("41", "157").collect(Collectors.toSet()));
    //    connections.put("24", Stream.of("156", "164").collect(Collectors.toSet()));
    //    connections.put("25", Stream.of("161", "254", "161", "254").collect(Collectors.toSet()));
    //    connections.put("26", Stream.of("259", "273").collect(Collectors.toSet()));
    //    connections.put("27", Stream.of("79", "200").collect(Collectors.toSet()));
    //    connections.put("28", Stream.of("162", "191", "11", "107").collect(Collectors.toSet()));
    //    connections.put("29", Stream.of("84", "157").collect(Collectors.toSet()));
    //    connections.put("30", Stream.of("176", "189").collect(Collectors.toSet()));
    //    connections.put("31", Stream.of("9", "302").collect(Collectors.toSet()));
    //    connections.put("32", Stream.of("70", "203").collect(Collectors.toSet()));
    //    connections.put("33", Stream.of("36", "164", "36", "164").collect(Collectors.toSet()));
    //    connections.put("34", Stream.of("100", "119").collect(Collectors.toSet()));
    //    connections.put("35", Stream.of("244").collect(Collectors.toSet()));
    //    connections.put("36", Stream.of("33", "288", "33", "288").collect(Collectors.toSet()));
    //    connections.put("37", Stream.of("158", "300").collect(Collectors.toSet()));
    //    connections.put("38", Stream.of("58", "81").collect(Collectors.toSet()));
    //    connections.put("39", Stream.of("128", "145").collect(Collectors.toSet()));
    //    connections.put("40", Stream.of("47", "89", "139", "170").collect(Collectors.toSet()));
    //    connections.put("41", Stream.of("215", "252", "23", "42").collect(Collectors.toSet()));
    //    connections.put("42", Stream.of("120", "291", "41", "183").collect(Collectors.toSet()));
    //    connections.put("43", Stream.of("79", "218", "183", "288").collect(Collectors.toSet()));
    //    connections.put("44", Stream.of("161", "166", "161", "166").collect(Collectors.toSet()));
    //    connections.put("45", Stream.of("206", "242").collect(Collectors.toSet()));
    //    connections.put("46", Stream.of("6", "50", "53").collect(Collectors.toSet()));
    //    connections.put("47", Stream.of("22", "40").collect(Collectors.toSet()));
    //    connections.put("48", Stream.of("126", "249").collect(Collectors.toSet()));
    //    connections.put("49", Stream.of("87", "196", "87", "151").collect(Collectors.toSet()));
    //    connections.put("50", Stream.of("46").collect(Collectors.toSet()));
    //    connections.put("51", Stream.of("103", "214").collect(Collectors.toSet()));
    //    connections.put("52", Stream.of("1", "264").collect(Collectors.toSet()));
    //    connections.put("53", Stream.of("46", "213").collect(Collectors.toSet()));
    //    connections.put("54", Stream.of("55", "56").collect(Collectors.toSet()));
    //    connections.put("55", Stream.of("54", "244").collect(Collectors.toSet()));
    //    connections.put("56", Stream.of("12", "54").collect(Collectors.toSet()));
    //    connections.put("57", Stream.of("187").collect(Collectors.toSet()));
    //    connections.put("58", Stream.of("38", "119").collect(Collectors.toSet()));
    //    connections.put("59", Stream.of("239", "257").collect(Collectors.toSet()));
    //    connections.put("60", Stream.of("126", "151").collect(Collectors.toSet()));
    //    connections.put("61", Stream.of("171", "237").collect(Collectors.toSet()));
    //    connections.put("62", Stream.of("168", "279").collect(Collectors.toSet()));
    //    connections.put("63", Stream.of("202", "218").collect(Collectors.toSet()));
    //    connections.put("64", Stream.of("106", "135").collect(Collectors.toSet()));
    //    connections.put("65", Stream.of("20", "97").collect(Collectors.toSet()));
    //    connections.put("66", Stream.of("67", "85").collect(Collectors.toSet()));
    //    connections.put("67", Stream.of("21", "66").collect(Collectors.toSet()));
    //    connections.put("68", Stream.of("158", "255").collect(Collectors.toSet()));
    //    connections.put("69", Stream.of("86", "106").collect(Collectors.toSet()));
    //    connections.put("70", Stream.of("4", "32").collect(Collectors.toSet()));
    //    connections.put("71", Stream.of("172", "296").collect(Collectors.toSet()));
    //    connections.put("72", Stream.of("285", "73").collect(Collectors.toSet()));
    //    connections.put("73", Stream.of("72", "1", "1", "182").collect(Collectors.toSet()));
    //    connections.put(
    //        "74", Stream.of("99", "122", "138", "286", "292", "17",
    // "99").collect(Collectors.toSet()));
    //    connections.put("75", Stream.of("209", "221", "209", "221").collect(Collectors.toSet()));
    //    connections.put("76", Stream.of("181", "295").collect(Collectors.toSet()));
    //    connections.put("77", Stream.of("93", "124").collect(Collectors.toSet()));
    //    connections.put("78", Stream.of("15", "269", "15", "269").collect(Collectors.toSet()));
    //    connections.put("79", Stream.of("27", "43").collect(Collectors.toSet()));
    //    connections.put("80", Stream.of("204", "230").collect(Collectors.toSet()));
    //    connections.put("81", Stream.of("38").collect(Collectors.toSet()));
    //    connections.put("82", Stream.of("163", "192").collect(Collectors.toSet()));
    //    connections.put("83", Stream.of("11", "192", "192", "11",
    // "192").collect(Collectors.toSet()));
    //    connections.put("84", Stream.of("148", "29", "136").collect(Collectors.toSet()));
    //    connections.put("85", Stream.of("66", "129").collect(Collectors.toSet()));
    //    connections.put("86", Stream.of("69", "152").collect(Collectors.toSet()));
    //    connections.put(
    //        "87",
    //        Stream.of("49", "278", "254", "284", "254", "284", "49", "278")
    //            .collect(Collectors.toSet()));
    //    connections.put("88", Stream.of("255").collect(Collectors.toSet()));
    //    connections.put(
    //        "89", Stream.of("40", "145", "170", "276", "145", "276").collect(Collectors.toSet()));
    //    connections.put(
    //        "90", Stream.of("104", "145", "104", "145", "104",
    // "145").collect(Collectors.toSet()));
    //    connections.put("91", Stream.of("16", "109").collect(Collectors.toSet()));
    //    connections.put(
    //        "92", Stream.of("14", "145", "14", "145", "14", "145").collect(Collectors.toSet()));
    //    connections.put("93", Stream.of("77", "165", "287").collect(Collectors.toSet()));
    //    connections.put("94", Stream.of("253", "289", "11", "281").collect(Collectors.toSet()));
    //    connections.put("95", Stream.of("10", "160", "123", "223").collect(Collectors.toSet()));
    //    connections.put("96", Stream.of("194", "286").collect(Collectors.toSet()));
    //    connections.put("97", Stream.of("19", "65").collect(Collectors.toSet()));
    //    connections.put("98", Stream.of("173", "210").collect(Collectors.toSet()));
    //    connections.put(
    //        "99", Stream.of("122", "235", "74", "235", "74", "235").collect(Collectors.toSet()));
    //    connections.put("100", Stream.of("34", "111").collect(Collectors.toSet()));
    //    connections.put("101", Stream.of("110", "226").collect(Collectors.toSet()));
    //    connections.put("102", Stream.of("258", "276").collect(Collectors.toSet()));
    //    connections.put("103", Stream.of("51", "109").collect(Collectors.toSet()));
    //    connections.put(
    //        "104", Stream.of("11", "90", "11", "90", "11", "90").collect(Collectors.toSet()));
    //    connections.put("105", Stream.of("177", "195").collect(Collectors.toSet()));
    //    connections.put("106", Stream.of("64", "69").collect(Collectors.toSet()));
    //    connections.put(
    //        "107", Stream.of("28", "284", "133", "196", "191",
    // "272").collect(Collectors.toSet()));
    //    connections.put("108", Stream.of("141", "264").collect(Collectors.toSet()));
    //    connections.put("109", Stream.of("91", "103").collect(Collectors.toSet()));
    //    connections.put("110", Stream.of("17", "208", "101", "17",
    // "264").collect(Collectors.toSet()));
    //    connections.put("111", Stream.of("22", "100").collect(Collectors.toSet()));
    //    connections.put("112", Stream.of("181", "195").collect(Collectors.toSet()));
    //    connections.put("113", Stream.of("245", "297").collect(Collectors.toSet()));
    //    connections.put("114", Stream.of("140").collect(Collectors.toSet()));
    //    connections.put("115", Stream.of("178", "184", "290").collect(Collectors.toSet()));
    //    connections.put("116", Stream.of("117", "118", "132").collect(Collectors.toSet()));
    //    connections.put("117", Stream.of("116", "118").collect(Collectors.toSet()));
    //    connections.put("118", Stream.of("116", "117").collect(Collectors.toSet()));
    //    connections.put("119", Stream.of("34", "58").collect(Collectors.toSet()));
    //    connections.put("120", Stream.of("42", "237").collect(Collectors.toSet()));
    //    connections.put("121", Stream.of("260").collect(Collectors.toSet()));
    //    connections.put("122", Stream.of("99", "186", "74", "186").collect(Collectors.toSet()));
    //    connections.put("123", Stream.of("95", "145").collect(Collectors.toSet()));
    //    connections.put("124", Stream.of("8", "77").collect(Collectors.toSet()));
    //    connections.put("125", Stream.of("134", "270", "134", "270").collect(Collectors.toSet()));
    //    connections.put("126", Stream.of("48", "258", "60", "222").collect(Collectors.toSet()));
    //    connections.put("127", Stream.of("186", "225").collect(Collectors.toSet()));
    //    connections.put("128", Stream.of("10", "39").collect(Collectors.toSet()));
    //    connections.put("129", Stream.of("85", "267").collect(Collectors.toSet()));
    //    connections.put("130", Stream.of("131", "132").collect(Collectors.toSet()));
    //    connections.put("131", Stream.of("130", "189").collect(Collectors.toSet()));
    //    connections.put("132", Stream.of("116", "130").collect(Collectors.toSet()));
    //    connections.put("133", Stream.of("107", "146").collect(Collectors.toSet()));
    //    connections.put("134", Stream.of("125", "219", "125", "219").collect(Collectors.toSet()));
    //    connections.put("135", Stream.of("64", "171").collect(Collectors.toSet()));
    //    connections.put("136", Stream.of("84", "190", "278").collect(Collectors.toSet()));
    //    connections.put("137", Stream.of("205", "297").collect(Collectors.toSet()));
    //    connections.put("138", Stream.of("74").collect(Collectors.toSet()));
    //    connections.put("139", Stream.of("40", "263").collect(Collectors.toSet()));
    //    connections.put("140", Stream.of("114", "236").collect(Collectors.toSet()));
    //    connections.put("141", Stream.of("108", "212").collect(Collectors.toSet()));
    //    connections.put("142", Stream.of("289", "296").collect(Collectors.toSet()));
    //    connections.put("143", Stream.of("159", "205").collect(Collectors.toSet()));
    //    connections.put("144", Stream.of("206", "281").collect(Collectors.toSet()));
    //    connections.put(
    //        "145",
    //        Stream.of("90", "92", "90", "92", "90", "92", "7", "89", "39", "222", "89", "123")
    //            .collect(Collectors.toSet()));
    //    connections.put("146", Stream.of("133", "235").collect(Collectors.toSet()));
    //    connections.put("147", Stream.of("150", "282").collect(Collectors.toSet()));
    //    connections.put("148", Stream.of("84", "278").collect(Collectors.toSet()));
    //    connections.put("149", Stream.of("162", "207").collect(Collectors.toSet()));
    //    connections.put("150", Stream.of("147", "226").collect(Collectors.toSet()));
    //    connections.put("151", Stream.of("49", "258", "60", "196").collect(Collectors.toSet()));
    //    connections.put("152", Stream.of("86").collect(Collectors.toSet()));
    //    connections.put("153", Stream.of("154", "246").collect(Collectors.toSet()));
    //    connections.put("154", Stream.of("153", "229", "274").collect(Collectors.toSet()));
    //    connections.put("155", Stream.of("224", "283").collect(Collectors.toSet()));
    //    connections.put(
    //        "156",
    //        Stream.of("13", "24", "2", "167", "3", "167", "2",
    // "167").collect(Collectors.toSet()));
    //    connections.put("157", Stream.of("23", "232", "13", "29").collect(Collectors.toSet()));
    //    connections.put("158", Stream.of("37", "68").collect(Collectors.toSet()));
    //    connections.put("159", Stream.of("143", "277").collect(Collectors.toSet()));
    //    connections.put("160", Stream.of("95", "265").collect(Collectors.toSet()));
    //    connections.put("161", Stream.of("25", "44", "25", "44").collect(Collectors.toSet()));
    //    connections.put("162", Stream.of("28", "149").collect(Collectors.toSet()));
    //    connections.put("163", Stream.of("11", "82").collect(Collectors.toSet()));
    //    connections.put(
    //        "164", Stream.of("24", "246", "33", "243", "33", "243").collect(Collectors.toSet()));
    //    connections.put("165", Stream.of("93").collect(Collectors.toSet()));
    //    connections.put("166", Stream.of("44", "262", "44", "262").collect(Collectors.toSet()));
    //    connections.put(
    //        "167",
    //        Stream.of("14", "156", "14", "156", "14", "156", "13",
    // "188").collect(Collectors.toSet()));
    //    connections.put("168", Stream.of("62", "179", "213").collect(Collectors.toSet()));
    //    connections.put("169", Stream.of("239").collect(Collectors.toSet()));
    //    connections.put("170", Stream.of("40", "89").collect(Collectors.toSet()));
    //    connections.put("171", Stream.of("61", "135").collect(Collectors.toSet()));
    //    connections.put("172", Stream.of("71", "281").collect(Collectors.toSet()));
    //    connections.put("173", Stream.of("16", "98").collect(Collectors.toSet()));
    //    connections.put("174", Stream.of("252").collect(Collectors.toSet()));
    //    connections.put("175", Stream.of("252").collect(Collectors.toSet()));
    //    connections.put("176", Stream.of("30", "233").collect(Collectors.toSet()));
    //    connections.put("177", Stream.of("105", "238").collect(Collectors.toSet()));
    //    connections.put("178", Stream.of("115", "201").collect(Collectors.toSet()));
    //    connections.put("179", Stream.of("168", "180").collect(Collectors.toSet()));
    //    connections.put("180", Stream.of("179", "198").collect(Collectors.toSet()));
    //    connections.put("181", Stream.of("76", "112", "285").collect(Collectors.toSet()));
    //    connections.put("182", Stream.of("73", "193").collect(Collectors.toSet()));
    //    connections.put("183", Stream.of("42", "43").collect(Collectors.toSet()));
    //    connections.put("184", Stream.of("115", "198").collect(Collectors.toSet()));
    //    connections.put("185", Stream.of("236", "280").collect(Collectors.toSet()));
    //    connections.put(
    //        "186", Stream.of("127", "207", "18", "122", "18", "122").collect(Collectors.toSet()));
    //    connections.put("187", Stream.of("57", "231").collect(Collectors.toSet()));
    //    connections.put("188", Stream.of("7", "167").collect(Collectors.toSet()));
    //    connections.put("189", Stream.of("30", "131").collect(Collectors.toSet()));
    //    connections.put("190", Stream.of("136", "244").collect(Collectors.toSet()));
    //    connections.put(
    //        "191", Stream.of("196", "211", "28", "258", "107",
    // "276").collect(Collectors.toSet()));
    //    connections.put(
    //        "192",
    //        Stream.of("82", "277", "18", "83", "18", "83", "83",
    // "217").collect(Collectors.toSet()));
    //    connections.put("193", Stream.of("5", "182").collect(Collectors.toSet()));
    //    connections.put("194", Stream.of("96", "204").collect(Collectors.toSet()));
    //    connections.put("195", Stream.of("105", "112").collect(Collectors.toSet()));
    //    connections.put("196", Stream.of("49", "191", "107", "151").collect(Collectors.toSet()));
    //    connections.put("197", Stream.of("271", "272").collect(Collectors.toSet()));
    //    connections.put("198", Stream.of("180", "184").collect(Collectors.toSet()));
    //    connections.put("199", Stream.of("269", "288", "269", "288").collect(Collectors.toSet()));
    //    connections.put("200", Stream.of("4", "27", "283", "291").collect(Collectors.toSet()));
    //    connections.put("201", Stream.of("178", "281").collect(Collectors.toSet()));
    //    connections.put("202", Stream.of("63", "216").collect(Collectors.toSet()));
    //    connections.put("203", Stream.of("32", "246").collect(Collectors.toSet()));
    //    connections.put("204", Stream.of("80", "194").collect(Collectors.toSet()));
    //    connections.put("205", Stream.of("137", "143").collect(Collectors.toSet()));
    //    connections.put("206", Stream.of("45", "144").collect(Collectors.toSet()));
    //    connections.put("207", Stream.of("149", "186").collect(Collectors.toSet()));
    //    connections.put("208", Stream.of("110", "241").collect(Collectors.toSet()));
    //    connections.put("209", Stream.of("75", "290", "75", "234").collect(Collectors.toSet()));
    //    connections.put("210", Stream.of("98", "274").collect(Collectors.toSet()));
    //    connections.put("211", Stream.of("11", "191").collect(Collectors.toSet()));
    //    connections.put("212", Stream.of("141").collect(Collectors.toSet()));
    //    connections.put("213", Stream.of("53", "168").collect(Collectors.toSet()));
    //    connections.put("214", Stream.of("51", "300").collect(Collectors.toSet()));
    //    connections.put("215", Stream.of("41", "275").collect(Collectors.toSet()));
    //    connections.put("216", Stream.of("20", "202").collect(Collectors.toSet()));
    //    connections.put("217", Stream.of("192", "282").collect(Collectors.toSet()));
    //    connections.put("218", Stream.of("43", "63").collect(Collectors.toSet()));
    //    connections.put("219", Stream.of("134", "221", "134", "221").collect(Collectors.toSet()));
    //    connections.put("220", Stream.of("238", "293").collect(Collectors.toSet()));
    //    connections.put("221", Stream.of("75", "219", "75", "219").collect(Collectors.toSet()));
    //    connections.put("222", Stream.of("126", "145").collect(Collectors.toSet()));
    //    connections.put("223", Stream.of("95", "259").collect(Collectors.toSet()));
    //    connections.put("224", Stream.of("13", "155", "261", "275",
    // "294").collect(Collectors.toSet()));
    //    connections.put("225", Stream.of("127", "295").collect(Collectors.toSet()));
    //    connections.put("226", Stream.of("101", "150").collect(Collectors.toSet()));
    //    connections.put("227", Stream.of("294").collect(Collectors.toSet()));
    //    connections.put("228", Stream.of("235", "272", "235", "272").collect(Collectors.toSet()));
    //    connections.put("229", Stream.of("154", "240").collect(Collectors.toSet()));
    //    connections.put("230", Stream.of("80", "299").collect(Collectors.toSet()));
    //    connections.put("231", Stream.of("9", "187").collect(Collectors.toSet()));
    //    connections.put("232", Stream.of("157", "278").collect(Collectors.toSet()));
    //    connections.put("233", Stream.of("1", "176").collect(Collectors.toSet()));
    //    connections.put("234", Stream.of("209", "250").collect(Collectors.toSet()));
    //    connections.put(
    //        "235", Stream.of("99", "228", "99", "228", "99", "146").collect(Collectors.toSet()));
    //    connections.put("236", Stream.of("140", "185").collect(Collectors.toSet()));
    //    connections.put("237", Stream.of("61", "120").collect(Collectors.toSet()));
    //    connections.put("238", Stream.of("177", "220").collect(Collectors.toSet()));
    //    connections.put("239", Stream.of("59", "169").collect(Collectors.toSet()));
    //    connections.put("240", Stream.of("229", "300").collect(Collectors.toSet()));
    //    connections.put("241", Stream.of("208", "264").collect(Collectors.toSet()));
    //    connections.put("242", Stream.of("45").collect(Collectors.toSet()));
    //    connections.put("243", Stream.of("164", "294", "164", "294").collect(Collectors.toSet()));
    //    connections.put("244", Stream.of("55", "190", "35", "271").collect(Collectors.toSet()));
    //    connections.put("245", Stream.of("113", "280").collect(Collectors.toSet()));
    //    connections.put("246", Stream.of("153", "164", "203", "288").collect(Collectors.toSet()));
    //    connections.put("247", Stream.of("272", "284", "272", "284").collect(Collectors.toSet()));
    //    connections.put("248", Stream.of("11", "253").collect(Collectors.toSet()));
    //    connections.put("249", Stream.of("13", "48").collect(Collectors.toSet()));
    //    connections.put("250", Stream.of("234", "251").collect(Collectors.toSet()));
    //    connections.put("251", Stream.of("5", "250").collect(Collectors.toSet()));
    //    connections.put("252", Stream.of("41", "174", "175").collect(Collectors.toSet()));
    //    connections.put("253", Stream.of("94", "248").collect(Collectors.toSet()));
    //    connections.put("254", Stream.of("25", "87", "25", "87").collect(Collectors.toSet()));
    //    connections.put("255", Stream.of("68", "88").collect(Collectors.toSet()));
    //    connections.put("256", Stream.of("12", "257").collect(Collectors.toSet()));
    //    connections.put("257", Stream.of("59", "256").collect(Collectors.toSet()));
    //    connections.put("258", Stream.of("126", "191", "102", "151").collect(Collectors.toSet()));
    //    connections.put("259", Stream.of("26", "223").collect(Collectors.toSet()));
    //    connections.put("260", Stream.of("121", "301").collect(Collectors.toSet()));
    //    connections.put("261", Stream.of("224").collect(Collectors.toSet()));
    //    connections.put("262", Stream.of("2", "166", "3", "166").collect(Collectors.toSet()));
    //    connections.put("263", Stream.of("8", "139").collect(Collectors.toSet()));
    //    connections.put("264", Stream.of("52", "108", "241", "1",
    // "110").collect(Collectors.toSet()));
    //    connections.put("265", Stream.of("160", "302").collect(Collectors.toSet()));
    //    connections.put("266", Stream.of("267").collect(Collectors.toSet()));
    //    connections.put("267", Stream.of("129", "266").collect(Collectors.toSet()));
    //    connections.put("268", Stream.of("15", "21").collect(Collectors.toSet()));
    //    connections.put("269", Stream.of("78", "199", "78", "199").collect(Collectors.toSet()));
    //    connections.put("270", Stream.of("125", "125").collect(Collectors.toSet()));
    //    connections.put("271", Stream.of("197", "244").collect(Collectors.toSet()));
    //    connections.put(
    //        "272", Stream.of("228", "247", "228", "247", "107",
    // "197").collect(Collectors.toSet()));
    //    connections.put("273", Stream.of("26").collect(Collectors.toSet()));
    //    connections.put("274", Stream.of("154", "210").collect(Collectors.toSet()));
    //    connections.put("275", Stream.of("215", "224").collect(Collectors.toSet()));
    //    connections.put("276", Stream.of("89", "102", "89", "191").collect(Collectors.toSet()));
    //    connections.put("277", Stream.of("159", "192").collect(Collectors.toSet()));
    //    connections.put(
    //        "278", Stream.of("87", "148", "232", "284", "87", "136",
    // "13").collect(Collectors.toSet()));
    //    connections.put("279", Stream.of("62").collect(Collectors.toSet()));
    //    connections.put("280", Stream.of("185", "245").collect(Collectors.toSet()));
    //    connections.put("281", Stream.of("144", "172", "94", "201").collect(Collectors.toSet()));
    //    connections.put("282", Stream.of("147", "217").collect(Collectors.toSet()));
    //    connections.put("283", Stream.of("155", "200", "291").collect(Collectors.toSet()));
    //    connections.put(
    //        "284", Stream.of("87", "247", "87", "247", "107", "278").collect(Collectors.toSet()));
    //    connections.put("285", Stream.of("72", "181").collect(Collectors.toSet()));
    //    connections.put("286", Stream.of("74", "96").collect(Collectors.toSet()));
    //    connections.put("287", Stream.of("93", "301").collect(Collectors.toSet()));
    //    connections.put(
    //        "288", Stream.of("36", "199", "36", "199", "43", "246").collect(Collectors.toSet()));
    //    connections.put("289", Stream.of("94", "142").collect(Collectors.toSet()));
    //    connections.put("290", Stream.of("115", "209").collect(Collectors.toSet()));
    //    connections.put("291", Stream.of("42", "200", "283").collect(Collectors.toSet()));
    //    connections.put("292", Stream.of("17", "74").collect(Collectors.toSet()));
    //    connections.put("293", Stream.of("220").collect(Collectors.toSet()));
    //    connections.put(
    //        "294", Stream.of("3", "243", "224", "227", "3", "243").collect(Collectors.toSet()));
    //    connections.put("295", Stream.of("76", "225").collect(Collectors.toSet()));
    //    connections.put("296", Stream.of("71", "142").collect(Collectors.toSet()));
    //    connections.put("297", Stream.of("113", "137").collect(Collectors.toSet()));
    //    connections.put("298", Stream.of("299").collect(Collectors.toSet()));
    //    connections.put("299", Stream.of("230", "298").collect(Collectors.toSet()));
    //    connections.put("300", Stream.of("37", "214", "240").collect(Collectors.toSet()));
    //    connections.put("301", Stream.of("260", "287").collect(Collectors.toSet()));
    //    connections.put("302", Stream.of("31", "265").collect(Collectors.toSet()));
    locationGraph = new Graph<>(locations, connections);
    routeFinder =
        new RouteFinder<>(
            locationGraph, new EuclideanDistanceScorer(), new EuclideanDistanceScorer());
  }

  //  @Test
  //  public void findRoute() {
  //    List<Location> route =
  //        routeFinder.findRoute(underground.getNode("74"), underground.getNode("7"));
  //    // assertThat(route).size().isPositive();
  //
  //    route.stream()
  //        .map(Location::getName)
  //        .collect(Collectors.toList())
  //        .forEach(station -> log.debug(station));
  //  }
}
