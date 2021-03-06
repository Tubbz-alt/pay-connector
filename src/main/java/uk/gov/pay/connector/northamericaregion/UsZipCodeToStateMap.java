package uk.gov.pay.connector.northamericaregion;

import java.util.HashMap;
import java.util.Map;

import static uk.gov.pay.connector.northamericaregion.UsState.ALABAMA;
import static uk.gov.pay.connector.northamericaregion.UsState.ALASKA;
import static uk.gov.pay.connector.northamericaregion.UsState.ARIZONA;
import static uk.gov.pay.connector.northamericaregion.UsState.ARKANSAS;
import static uk.gov.pay.connector.northamericaregion.UsState.ARMED_FORCES_AMERICAS;
import static uk.gov.pay.connector.northamericaregion.UsState.ARMED_FORCES_EUROPE;
import static uk.gov.pay.connector.northamericaregion.UsState.ARMED_FORCES_PACIFIC;
import static uk.gov.pay.connector.northamericaregion.UsState.CALIFORNIA;
import static uk.gov.pay.connector.northamericaregion.UsState.COLORADO;
import static uk.gov.pay.connector.northamericaregion.UsState.CONNECTICUT;
import static uk.gov.pay.connector.northamericaregion.UsState.DELAWARE;
import static uk.gov.pay.connector.northamericaregion.UsState.FLORIDA;
import static uk.gov.pay.connector.northamericaregion.UsState.GEORGIA;
import static uk.gov.pay.connector.northamericaregion.UsState.GUAM;
import static uk.gov.pay.connector.northamericaregion.UsState.HAWAII;
import static uk.gov.pay.connector.northamericaregion.UsState.IDAHO;
import static uk.gov.pay.connector.northamericaregion.UsState.ILLINOIS;
import static uk.gov.pay.connector.northamericaregion.UsState.INDIANA;
import static uk.gov.pay.connector.northamericaregion.UsState.IOWA;
import static uk.gov.pay.connector.northamericaregion.UsState.KANSAS;
import static uk.gov.pay.connector.northamericaregion.UsState.KENTUCKY;
import static uk.gov.pay.connector.northamericaregion.UsState.LOUISIANA;
import static uk.gov.pay.connector.northamericaregion.UsState.MAINE;
import static uk.gov.pay.connector.northamericaregion.UsState.MARYLAND;
import static uk.gov.pay.connector.northamericaregion.UsState.MASSACHUSETTS;
import static uk.gov.pay.connector.northamericaregion.UsState.MICHIGAN;
import static uk.gov.pay.connector.northamericaregion.UsState.MINNESOTA;
import static uk.gov.pay.connector.northamericaregion.UsState.MISSISSIPPI;
import static uk.gov.pay.connector.northamericaregion.UsState.MISSOURI;
import static uk.gov.pay.connector.northamericaregion.UsState.MONTANA;
import static uk.gov.pay.connector.northamericaregion.UsState.NEBRASKA;
import static uk.gov.pay.connector.northamericaregion.UsState.NEVADA;
import static uk.gov.pay.connector.northamericaregion.UsState.NEW_HAMPSHIRE;
import static uk.gov.pay.connector.northamericaregion.UsState.NEW_JERSEY;
import static uk.gov.pay.connector.northamericaregion.UsState.NEW_MEXICO;
import static uk.gov.pay.connector.northamericaregion.UsState.NEW_YORK;
import static uk.gov.pay.connector.northamericaregion.UsState.NORTH_CAROLINA;
import static uk.gov.pay.connector.northamericaregion.UsState.NORTH_DAKOTA;
import static uk.gov.pay.connector.northamericaregion.UsState.OHIO;
import static uk.gov.pay.connector.northamericaregion.UsState.OKLAHOMA;
import static uk.gov.pay.connector.northamericaregion.UsState.OREGON;
import static uk.gov.pay.connector.northamericaregion.UsState.PENNSYLVANIA;
import static uk.gov.pay.connector.northamericaregion.UsState.PUERTO_RICO;
import static uk.gov.pay.connector.northamericaregion.UsState.RHODE_ISLAND;
import static uk.gov.pay.connector.northamericaregion.UsState.SOUTH_CAROLINA;
import static uk.gov.pay.connector.northamericaregion.UsState.SOUTH_DAKOTA;
import static uk.gov.pay.connector.northamericaregion.UsState.TENNESSEE;
import static uk.gov.pay.connector.northamericaregion.UsState.TEXAS;
import static uk.gov.pay.connector.northamericaregion.UsState.UTAH;
import static uk.gov.pay.connector.northamericaregion.UsState.VERMONT;
import static uk.gov.pay.connector.northamericaregion.UsState.VIRGINIA;
import static uk.gov.pay.connector.northamericaregion.UsState.VIRGIN_ISLANDS;
import static uk.gov.pay.connector.northamericaregion.UsState.WASHINGTON;
import static uk.gov.pay.connector.northamericaregion.UsState.WASHINGTON_DC;
import static uk.gov.pay.connector.northamericaregion.UsState.WEST_VIRGINIA;
import static uk.gov.pay.connector.northamericaregion.UsState.WISCONSIN;
import static uk.gov.pay.connector.northamericaregion.UsState.WYOMING;

public class UsZipCodeToStateMap {
    
    /**
     * We have to use this initializiation method because using 'Map.ofEntries(...)' with hundreds of arguments is excessively slow to compile.
     * See https://youtrack.jetbrains.com/issue/IDEA-245963
    **/
    public static final Map<String, UsState> ZIP_CODE_TO_US_STATE_ABBREVIATIONS = Map.copyOf(new HashMap<>() {{
        put("005", NEW_YORK);
        put("006", PUERTO_RICO);
        put("007", PUERTO_RICO);
        put("008", VIRGIN_ISLANDS);
        put("009", PUERTO_RICO);
        put("010", MASSACHUSETTS);
        put("011", MASSACHUSETTS);
        put("012", MASSACHUSETTS);
        put("013", MASSACHUSETTS);
        put("014", MASSACHUSETTS);
        put("015", MASSACHUSETTS);
        put("016", MASSACHUSETTS);
        put("017", MASSACHUSETTS);
        put("018", MASSACHUSETTS);
        put("019", MASSACHUSETTS);
        put("020", MASSACHUSETTS);
        put("021", MASSACHUSETTS);
        put("022", MASSACHUSETTS);
        put("023", MASSACHUSETTS);
        put("024", MASSACHUSETTS);
        put("025", MASSACHUSETTS);
        put("026", MASSACHUSETTS);
        put("027", MASSACHUSETTS);
        put("028", RHODE_ISLAND);
        put("029", RHODE_ISLAND);
        put("030", NEW_HAMPSHIRE);
        put("031", NEW_HAMPSHIRE);
        put("032", NEW_HAMPSHIRE);
        put("033", NEW_HAMPSHIRE);
        put("034", NEW_HAMPSHIRE);
        put("035", NEW_HAMPSHIRE);
        put("036", NEW_HAMPSHIRE);
        put("037", NEW_HAMPSHIRE);
        put("038", NEW_HAMPSHIRE);
        put("039", MAINE);
        put("040", MAINE);
        put("041", MAINE);
        put("042", MAINE);
        put("043", MAINE);
        put("044", MAINE);
        put("045", MAINE);
        put("046", MAINE);
        put("047", MAINE);
        put("048", MAINE);
        put("049", MAINE);
        put("050", VERMONT);
        put("051", VERMONT);
        put("052", VERMONT);
        put("053", VERMONT);
        put("054", VERMONT);
        put("055", MASSACHUSETTS);
        put("056", VERMONT);
        put("057", VERMONT);
        put("058", VERMONT);
        put("059", VERMONT);
        put("060", CONNECTICUT);
        put("061", CONNECTICUT);
        put("062", CONNECTICUT);
        put("063", CONNECTICUT);
        put("064", CONNECTICUT);
        put("065", CONNECTICUT);
        put("066", CONNECTICUT);
        put("067", CONNECTICUT);
        put("068", CONNECTICUT);
        put("069", CONNECTICUT);
        put("070", NEW_JERSEY);
        put("071", NEW_JERSEY);
        put("072", NEW_JERSEY);
        put("073", NEW_JERSEY);
        put("074", NEW_JERSEY);
        put("075", NEW_JERSEY);
        put("076", NEW_JERSEY);
        put("077", NEW_JERSEY);
        put("078", NEW_JERSEY);
        put("079", NEW_JERSEY);
        put("080", NEW_JERSEY);
        put("081", NEW_JERSEY);
        put("082", NEW_JERSEY);
        put("083", NEW_JERSEY);
        put("084", NEW_JERSEY);
        put("085", NEW_JERSEY);
        put("086", NEW_JERSEY);
        put("087", NEW_JERSEY);
        put("088", NEW_JERSEY);
        put("089", NEW_JERSEY);
        put("090", ARMED_FORCES_EUROPE);
        put("091", ARMED_FORCES_EUROPE);
        put("092", ARMED_FORCES_EUROPE);
        put("093", ARMED_FORCES_EUROPE);
        put("094", ARMED_FORCES_EUROPE);
        put("095", ARMED_FORCES_EUROPE);
        put("096", ARMED_FORCES_EUROPE);
        put("097", ARMED_FORCES_EUROPE);
        put("098", ARMED_FORCES_EUROPE);
        put("099", ARMED_FORCES_EUROPE);
        put("100",NEW_YORK);
        put("101",NEW_YORK);
        put("102",NEW_YORK);
        put("103",NEW_YORK);
        put("104",NEW_YORK);
        put("105",NEW_YORK);
        put("106",NEW_YORK);
        put("107",NEW_YORK);
        put("108",NEW_YORK);
        put("109",NEW_YORK);
        put("110",NEW_YORK);
        put("111",NEW_YORK);
        put("112",NEW_YORK);
        put("113",NEW_YORK);
        put("114",NEW_YORK);
        put("115",NEW_YORK);
        put("116",NEW_YORK);
        put("117",NEW_YORK);
        put("118",NEW_YORK);
        put("119",NEW_YORK);
        put("120",NEW_YORK);
        put("121",NEW_YORK);
        put("122",NEW_YORK);
        put("123",NEW_YORK);
        put("124",NEW_YORK);
        put("125",NEW_YORK);
        put("126",NEW_YORK);
        put("127",NEW_YORK);
        put("128",NEW_YORK);
        put("129",NEW_YORK);
        put("130",NEW_YORK);
        put("131",NEW_YORK);
        put("132",NEW_YORK);
        put("133",NEW_YORK);
        put("134",NEW_YORK);
        put("135",NEW_YORK);
        put("136",NEW_YORK);
        put("137",NEW_YORK);
        put("138",NEW_YORK);
        put("139",NEW_YORK);
        put("140",NEW_YORK);
        put("141",NEW_YORK);
        put("142",NEW_YORK);
        put("143",NEW_YORK);
        put("144",NEW_YORK);
        put("145",NEW_YORK);
        put("146",NEW_YORK);
        put("147",NEW_YORK);
        put("148",NEW_YORK);
        put("149",NEW_YORK);
        put("150",PENNSYLVANIA);
        put("151",PENNSYLVANIA);
        put("152",PENNSYLVANIA);
        put("153",PENNSYLVANIA);
        put("154",PENNSYLVANIA);
        put("155",PENNSYLVANIA);
        put("156",PENNSYLVANIA);
        put("157",PENNSYLVANIA);
        put("158",PENNSYLVANIA);
        put("159",PENNSYLVANIA);
        put("160",PENNSYLVANIA);
        put("161",PENNSYLVANIA);
        put("162",PENNSYLVANIA);
        put("163",PENNSYLVANIA);
        put("164",PENNSYLVANIA);
        put("165",PENNSYLVANIA);
        put("166",PENNSYLVANIA);
        put("167",PENNSYLVANIA);
        put("168",PENNSYLVANIA);
        put("169",PENNSYLVANIA);
        put("170",PENNSYLVANIA);
        put("171",PENNSYLVANIA);
        put("172",PENNSYLVANIA);
        put("173",PENNSYLVANIA);
        put("174",PENNSYLVANIA);
        put("175",PENNSYLVANIA);
        put("176",PENNSYLVANIA);
        put("177",PENNSYLVANIA);
        put("178",PENNSYLVANIA);
        put("179",PENNSYLVANIA);
        put("180",PENNSYLVANIA);
        put("181",PENNSYLVANIA);
        put("182",PENNSYLVANIA);
        put("183",PENNSYLVANIA);
        put("184",PENNSYLVANIA);
        put("185",PENNSYLVANIA);
        put("186",PENNSYLVANIA);
        put("187",PENNSYLVANIA);
        put("188",PENNSYLVANIA);
        put("189",PENNSYLVANIA);
        put("190",PENNSYLVANIA);
        put("191",PENNSYLVANIA);
        put("192",PENNSYLVANIA);
        put("193",PENNSYLVANIA);
        put("194",PENNSYLVANIA);
        put("195",PENNSYLVANIA);
        put("196",PENNSYLVANIA);
        put("197",DELAWARE);
        put("198",DELAWARE);
        put("199",DELAWARE);
        put("200", WASHINGTON_DC);
        put("201", VIRGINIA);
        put("202", WASHINGTON_DC);
        put("203", WASHINGTON_DC);
        put("204", WASHINGTON_DC);
        put("205", WASHINGTON_DC);
        put("206", MARYLAND);
        put("207", MARYLAND);
        put("208", MARYLAND);
        put("209", MARYLAND);
        put("210", MARYLAND);
        put("211", MARYLAND);
        put("212", MARYLAND);
        put("214", MARYLAND);
        put("215", MARYLAND);
        put("216", MARYLAND);
        put("217", MARYLAND);
        put("218", MARYLAND);
        put("219", MARYLAND);
        put("220", VIRGINIA);
        put("221", VIRGINIA);
        put("222", VIRGINIA);
        put("223", VIRGINIA);
        put("224", VIRGINIA);
        put("225", VIRGINIA);
        put("226", VIRGINIA);
        put("227", VIRGINIA);
        put("228", VIRGINIA);
        put("229", VIRGINIA);
        put("230", VIRGINIA);
        put("231", VIRGINIA);
        put("232", VIRGINIA);
        put("233", VIRGINIA);
        put("234", VIRGINIA);
        put("235", VIRGINIA);
        put("236", VIRGINIA);
        put("237", VIRGINIA);
        put("238", VIRGINIA);
        put("239", VIRGINIA);
        put("240", VIRGINIA);
        put("241", VIRGINIA);
        put("242", VIRGINIA);
        put("243", VIRGINIA);
        put("244", VIRGINIA);
        put("245", VIRGINIA);
        put("246", VIRGINIA);
        put("247", WEST_VIRGINIA);
        put("248", WEST_VIRGINIA);
        put("249", WEST_VIRGINIA);
        put("250", WEST_VIRGINIA);
        put("251", WEST_VIRGINIA);
        put("252", WEST_VIRGINIA);
        put("253", WEST_VIRGINIA);
        put("254", WEST_VIRGINIA);
        put("255", WEST_VIRGINIA);
        put("256", WEST_VIRGINIA);
        put("257", WEST_VIRGINIA);
        put("258", WEST_VIRGINIA);
        put("259", WEST_VIRGINIA);
        put("260", WEST_VIRGINIA);
        put("261", WEST_VIRGINIA);
        put("262", WEST_VIRGINIA);
        put("263", WEST_VIRGINIA);
        put("264", WEST_VIRGINIA);
        put("265", WEST_VIRGINIA);
        put("266", WEST_VIRGINIA);
        put("267", WEST_VIRGINIA);
        put("268", WEST_VIRGINIA);
        put("270", NORTH_CAROLINA);
        put("271", NORTH_CAROLINA);
        put("272", NORTH_CAROLINA);
        put("273", NORTH_CAROLINA);
        put("274", NORTH_CAROLINA);
        put("275", NORTH_CAROLINA);
        put("276", NORTH_CAROLINA);
        put("277", NORTH_CAROLINA);
        put("278", NORTH_CAROLINA);
        put("279", NORTH_CAROLINA);
        put("280", NORTH_CAROLINA);
        put("281", NORTH_CAROLINA);
        put("282", NORTH_CAROLINA);
        put("283", NORTH_CAROLINA);
        put("284", NORTH_CAROLINA);
        put("285", NORTH_CAROLINA);
        put("286", NORTH_CAROLINA);
        put("287", NORTH_CAROLINA);
        put("288", NORTH_CAROLINA);
        put("289", NORTH_CAROLINA);
        put("290", SOUTH_CAROLINA);
        put("291", SOUTH_CAROLINA);
        put("292", SOUTH_CAROLINA);
        put("293", SOUTH_CAROLINA);
        put("294", SOUTH_CAROLINA);
        put("295", SOUTH_CAROLINA);
        put("296", SOUTH_CAROLINA);
        put("297", SOUTH_CAROLINA);
        put("298", SOUTH_CAROLINA);
        put("299", SOUTH_CAROLINA);
        put("300", GEORGIA);
        put("301", GEORGIA);
        put("302", GEORGIA);
        put("303", GEORGIA);
        put("304", GEORGIA);
        put("305", GEORGIA);
        put("306", GEORGIA);
        put("307", GEORGIA);
        put("308", GEORGIA);
        put("309", GEORGIA);
        put("310", GEORGIA);
        put("311", GEORGIA);
        put("312", GEORGIA);
        put("313", GEORGIA);
        put("314", GEORGIA);
        put("315", GEORGIA);
        put("316", GEORGIA);
        put("317", GEORGIA);
        put("318", GEORGIA);
        put("319", GEORGIA);
        put("320", FLORIDA);
        put("321", FLORIDA);
        put("322", FLORIDA);
        put("323", FLORIDA);
        put("324", FLORIDA);
        put("325", FLORIDA);
        put("326", FLORIDA);
        put("327", FLORIDA);
        put("328", FLORIDA);
        put("329", FLORIDA);
        put("330", FLORIDA);
        put("331", FLORIDA);
        put("332", FLORIDA);
        put("333", FLORIDA);
        put("334", FLORIDA);
        put("335", FLORIDA);
        put("336", FLORIDA);
        put("337", FLORIDA);
        put("338", FLORIDA);
        put("339", FLORIDA);
        put("340", ARMED_FORCES_AMERICAS);
        put("341", FLORIDA);
        put("342", FLORIDA);
        put("344", FLORIDA);
        put("346", FLORIDA);
        put("347", FLORIDA);
        put("349", FLORIDA);
        put("350", ALABAMA);
        put("351", ALABAMA);
        put("352", ALABAMA);
        put("354", ALABAMA);
        put("355", ALABAMA);
        put("356", ALABAMA);
        put("357", ALABAMA);
        put("358", ALABAMA);
        put("359", ALABAMA);
        put("360", ALABAMA);
        put("361", ALABAMA);
        put("362", ALABAMA);
        put("363", ALABAMA);
        put("364", ALABAMA);
        put("365", ALABAMA);
        put("366", ALABAMA);
        put("367", ALABAMA);
        put("368", ALABAMA);
        put("369", ALABAMA);
        put("370", TENNESSEE);
        put("371", TENNESSEE);
        put("372", TENNESSEE);
        put("373", TENNESSEE);
        put("374", TENNESSEE);
        put("375", TENNESSEE);
        put("376", TENNESSEE);
        put("377", TENNESSEE);
        put("378", TENNESSEE);
        put("379", TENNESSEE);
        put("380", TENNESSEE);
        put("381", TENNESSEE);
        put("382", TENNESSEE);
        put("383", TENNESSEE);
        put("384", TENNESSEE);
        put("385", TENNESSEE);
        put("386", MISSISSIPPI);
        put("387", MISSISSIPPI);
        put("388", MISSISSIPPI);
        put("389", MISSISSIPPI);
        put("390", MISSISSIPPI);
        put("391", MISSISSIPPI);
        put("392", MISSISSIPPI);
        put("393", MISSISSIPPI);
        put("394", MISSISSIPPI);
        put("395", MISSISSIPPI);
        put("396", MISSISSIPPI);
        put("397", MISSISSIPPI);
        put("398", GEORGIA);
        put("399", GEORGIA);
        put("400", KENTUCKY);
        put("401", KENTUCKY);
        put("402", KENTUCKY);
        put("403", KENTUCKY);
        put("404", KENTUCKY);
        put("405", KENTUCKY);
        put("406", KENTUCKY);
        put("407", KENTUCKY);
        put("408", KENTUCKY);
        put("409", KENTUCKY);
        put("410", KENTUCKY);
        put("411", KENTUCKY);
        put("412", KENTUCKY);
        put("413", KENTUCKY);
        put("414", KENTUCKY);
        put("415", KENTUCKY);
        put("416", KENTUCKY);
        put("417", KENTUCKY);
        put("418", KENTUCKY);
        put("420", KENTUCKY);
        put("421", KENTUCKY);
        put("422", KENTUCKY);
        put("423", KENTUCKY);
        put("424", KENTUCKY);
        put("425", KENTUCKY);
        put("426", KENTUCKY);
        put("427", KENTUCKY);
        put("430", OHIO);
        put("431", OHIO);
        put("432", OHIO);
        put("433", OHIO);
        put("434", OHIO);
        put("435", OHIO);
        put("436", OHIO);
        put("437", OHIO);
        put("438", OHIO);
        put("439", OHIO);
        put("440", OHIO);
        put("441", OHIO);
        put("442", OHIO);
        put("443", OHIO);
        put("444", OHIO);
        put("445", OHIO);
        put("446", OHIO);
        put("447", OHIO);
        put("448", OHIO);
        put("449", OHIO);
        put("450", OHIO);
        put("451", OHIO);
        put("452", OHIO);
        put("453", OHIO);
        put("454", OHIO);
        put("455", OHIO);
        put("456", OHIO);
        put("457", OHIO);
        put("458", OHIO);
        put("459", OHIO);
        put("460", INDIANA);
        put("461", INDIANA);
        put("462", INDIANA);
        put("463", INDIANA);
        put("464", INDIANA);
        put("465", INDIANA);
        put("466", INDIANA);
        put("467", INDIANA);
        put("468", INDIANA);
        put("469", INDIANA);
        put("470", INDIANA);
        put("471", INDIANA);
        put("472", INDIANA);
        put("473", INDIANA);
        put("474", INDIANA);
        put("475", INDIANA);
        put("476", INDIANA);
        put("477", INDIANA);
        put("478", INDIANA);
        put("479", INDIANA);
        put("480", MICHIGAN);
        put("481", MICHIGAN);
        put("482", MICHIGAN);
        put("483", MICHIGAN);
        put("484", MICHIGAN);
        put("485", MICHIGAN);
        put("486", MICHIGAN);
        put("487", MICHIGAN);
        put("488", MICHIGAN);
        put("489", MICHIGAN);
        put("490", MICHIGAN);
        put("491", MICHIGAN);
        put("492", MICHIGAN);
        put("493", MICHIGAN);
        put("494", MICHIGAN);
        put("495", MICHIGAN);
        put("496", MICHIGAN);
        put("497", MICHIGAN);
        put("498", MICHIGAN);
        put("499", MICHIGAN);
        put("500", IOWA);
        put("501", IOWA);
        put("502", IOWA);
        put("503", IOWA);
        put("504", IOWA);
        put("505", IOWA);
        put("506", IOWA);
        put("507", IOWA);
        put("508", IOWA);
        put("509", IOWA);
        put("510", IOWA);
        put("511", IOWA);
        put("512", IOWA);
        put("513", IOWA);
        put("514", IOWA);
        put("515", IOWA);
        put("516", IOWA);
        put("520", IOWA);
        put("521", IOWA);
        put("522", IOWA);
        put("523", IOWA);
        put("524", IOWA);
        put("525", IOWA);
        put("526", IOWA);
        put("527", IOWA);
        put("528", IOWA);
        put("530", WISCONSIN);
        put("531", WISCONSIN);
        put("532", WISCONSIN);
        put("534", WISCONSIN);
        put("535", WISCONSIN);
        put("537", WISCONSIN);
        put("538", WISCONSIN);
        put("539", WISCONSIN);
        put("540", WISCONSIN);
        put("541", WISCONSIN);
        put("542", WISCONSIN);
        put("543", WISCONSIN);
        put("544", WISCONSIN);
        put("545", WISCONSIN);
        put("546", WISCONSIN);
        put("547", WISCONSIN);
        put("548", WISCONSIN);
        put("549", WISCONSIN);
        put("550", MINNESOTA);
        put("551", MINNESOTA);
        put("553", MINNESOTA);
        put("554", MINNESOTA);
        put("555", MINNESOTA);
        put("556", MINNESOTA);
        put("557", MINNESOTA);
        put("558", MINNESOTA);
        put("559", MINNESOTA);
        put("560", MINNESOTA);
        put("561", MINNESOTA);
        put("562", MINNESOTA);
        put("563", MINNESOTA);
        put("564", MINNESOTA);
        put("565", MINNESOTA);
        put("566", MINNESOTA);
        put("567", MINNESOTA);
        put("569", WASHINGTON_DC);
        put("570", SOUTH_DAKOTA);
        put("571", SOUTH_DAKOTA);
        put("572", SOUTH_DAKOTA);
        put("573", SOUTH_DAKOTA);
        put("574", SOUTH_DAKOTA);
        put("575", SOUTH_DAKOTA);
        put("576", SOUTH_DAKOTA);
        put("577", SOUTH_DAKOTA);
        put("580", NORTH_DAKOTA);
        put("581", NORTH_DAKOTA);
        put("582", NORTH_DAKOTA);
        put("583", NORTH_DAKOTA);
        put("584", NORTH_DAKOTA);
        put("585", NORTH_DAKOTA);
        put("586", NORTH_DAKOTA);
        put("587", NORTH_DAKOTA);
        put("588", NORTH_DAKOTA);
        put("590", MONTANA);
        put("591", MONTANA);
        put("592", MONTANA);
        put("593", MONTANA);
        put("594", MONTANA);
        put("595", MONTANA);
        put("596", MONTANA);
        put("597", MONTANA);
        put("598", MONTANA);
        put("599", MONTANA);
        put("600", ILLINOIS);
        put("601", ILLINOIS);
        put("602", ILLINOIS);
        put("603", ILLINOIS);
        put("604", ILLINOIS);
        put("605", ILLINOIS);
        put("606", ILLINOIS);
        put("607", ILLINOIS);
        put("608", ILLINOIS);
        put("609", ILLINOIS);
        put("610", ILLINOIS);
        put("611", ILLINOIS);
        put("612", ILLINOIS);
        put("613", ILLINOIS);
        put("614", ILLINOIS);
        put("615", ILLINOIS);
        put("616", ILLINOIS);
        put("617", ILLINOIS);
        put("618", ILLINOIS);
        put("619", ILLINOIS);
        put("620", ILLINOIS);
        put("622", ILLINOIS);
        put("623", ILLINOIS);
        put("624", ILLINOIS);
        put("625", ILLINOIS);
        put("626", ILLINOIS);
        put("627", ILLINOIS);
        put("628", ILLINOIS);
        put("629", ILLINOIS);
        put("630", MISSOURI);
        put("631", MISSOURI);
        put("633", MISSOURI);
        put("634", MISSOURI);
        put("635", MISSOURI);
        put("636", MISSOURI);
        put("637", MISSOURI);
        put("638", MISSOURI);
        put("639", MISSOURI);
        put("640", MISSOURI);
        put("641", MISSOURI);
        put("644", MISSOURI);
        put("645", MISSOURI);
        put("646", MISSOURI);
        put("647", MISSOURI);
        put("648", MISSOURI);
        put("649", MISSOURI);
        put("650", MISSOURI);
        put("651", MISSOURI);
        put("652", MISSOURI);
        put("653", MISSOURI);
        put("654", MISSOURI);
        put("655", MISSOURI);
        put("656", MISSOURI);
        put("657", MISSOURI);
        put("658", MISSOURI);
        put("660", KANSAS);
        put("661", KANSAS);
        put("662", KANSAS);
        put("664", KANSAS);
        put("665", KANSAS);
        put("666", KANSAS);
        put("667", KANSAS);
        put("668", KANSAS);
        put("669", KANSAS);
        put("670", KANSAS);
        put("671", KANSAS);
        put("672", KANSAS);
        put("673", KANSAS);
        put("674", KANSAS);
        put("675", KANSAS);
        put("676", KANSAS);
        put("677", KANSAS);
        put("678", KANSAS);
        put("679", KANSAS);
        put("680", NEBRASKA);
        put("681", NEBRASKA);
        put("683", NEBRASKA);
        put("684", NEBRASKA);
        put("685", NEBRASKA);
        put("686", NEBRASKA);
        put("687", NEBRASKA);
        put("688", NEBRASKA);
        put("689", NEBRASKA);
        put("690", NEBRASKA);
        put("691", NEBRASKA);
        put("692", NEBRASKA);
        put("693", NEBRASKA);
        put("700", LOUISIANA);
        put("701", LOUISIANA);
        put("703", LOUISIANA);
        put("704", LOUISIANA);
        put("705", LOUISIANA);
        put("706", LOUISIANA);
        put("707", LOUISIANA);
        put("708", LOUISIANA);
        put("710", LOUISIANA);
        put("711", LOUISIANA);
        put("712", LOUISIANA);
        put("713", LOUISIANA);
        put("714", LOUISIANA);
        put("716", ARKANSAS);
        put("717", ARKANSAS);
        put("718", ARKANSAS);
        put("719", ARKANSAS);
        put("720", ARKANSAS);
        put("721", ARKANSAS);
        put("722", ARKANSAS);
        put("723", ARKANSAS);
        put("724", ARKANSAS);
        put("725", ARKANSAS);
        put("726", ARKANSAS);
        put("727", ARKANSAS);
        put("728", ARKANSAS);
        put("729", ARKANSAS);
        put("730", OKLAHOMA);
        put("731", OKLAHOMA);
        put("733", TEXAS);
        put("734", OKLAHOMA);
        put("735", OKLAHOMA);
        put("736", OKLAHOMA);
        put("737", OKLAHOMA);
        put("738", OKLAHOMA);
        put("739", OKLAHOMA);
        put("740", OKLAHOMA);
        put("741", OKLAHOMA);
        put("743", OKLAHOMA);
        put("744", OKLAHOMA);
        put("745", OKLAHOMA);
        put("746", OKLAHOMA);
        put("747", OKLAHOMA);
        put("748", OKLAHOMA);
        put("749", OKLAHOMA);
        put("750", TEXAS);
        put("751", TEXAS);
        put("752", TEXAS);
        put("753", TEXAS);
        put("754", TEXAS);
        put("755", TEXAS);
        put("756", TEXAS);
        put("757", TEXAS);
        put("758", TEXAS);
        put("759", TEXAS);
        put("760", TEXAS);
        put("761", TEXAS);
        put("762", TEXAS);
        put("763", TEXAS);
        put("764", TEXAS);
        put("765", TEXAS);
        put("766", TEXAS);
        put("767", TEXAS);
        put("768", TEXAS);
        put("769", TEXAS);
        put("770", TEXAS);
        put("771", TEXAS);
        put("772", TEXAS);
        put("773", TEXAS);
        put("774", TEXAS);
        put("775", TEXAS);
        put("776", TEXAS);
        put("777", TEXAS);
        put("778", TEXAS);
        put("779", TEXAS);
        put("780", TEXAS);
        put("781", TEXAS);
        put("782", TEXAS);
        put("783", TEXAS);
        put("784", TEXAS);
        put("785", TEXAS);
        put("786", TEXAS);
        put("787", TEXAS);
        put("788", TEXAS);
        put("789", TEXAS);
        put("790", TEXAS);
        put("791", TEXAS);
        put("792", TEXAS);
        put("793", TEXAS);
        put("794", TEXAS);
        put("795", TEXAS);
        put("796", TEXAS);
        put("797", TEXAS);
        put("798", TEXAS);
        put("799", TEXAS);
        put("800", COLORADO);
        put("801", COLORADO);
        put("802", COLORADO);
        put("803", COLORADO);
        put("804", COLORADO);
        put("805", COLORADO);
        put("806", COLORADO);
        put("807", COLORADO);
        put("808", COLORADO);
        put("809", COLORADO);
        put("810", COLORADO);
        put("811", COLORADO);
        put("812", COLORADO);
        put("813", COLORADO);
        put("814", COLORADO);
        put("815", COLORADO);
        put("816", COLORADO);
        put("820", WYOMING);
        put("821", WYOMING);
        put("822", WYOMING);
        put("823", WYOMING);
        put("824", WYOMING);
        put("825", WYOMING);
        put("826", WYOMING);
        put("827", WYOMING);
        put("828", WYOMING);
        put("829", WYOMING);
        put("830", WYOMING);
        put("831", WYOMING);
        put("832", IDAHO);
        put("833", IDAHO);
        put("834", IDAHO);
        put("835", IDAHO);
        put("836", IDAHO);
        put("837", IDAHO);
        put("838", IDAHO);
        put("840", UTAH);
        put("841", UTAH);
        put("842", UTAH);
        put("843", UTAH);
        put("844", UTAH);
        put("845", UTAH);
        put("846", UTAH);
        put("847", UTAH);
        put("850", ARIZONA);
        put("851", ARIZONA);
        put("852", ARIZONA);
        put("853", ARIZONA);
        put("855", ARIZONA);
        put("856", ARIZONA);
        put("857", ARIZONA);
        put("859", ARIZONA);
        put("860", ARIZONA);
        put("863", ARIZONA);
        put("864", ARIZONA);
        put("865", ARIZONA);
        put("870", NEW_MEXICO);
        put("871", NEW_MEXICO);
        put("873", NEW_MEXICO);
        put("874", NEW_MEXICO);
        put("875", NEW_MEXICO);
        put("877", NEW_MEXICO);
        put("878", NEW_MEXICO);
        put("879", NEW_MEXICO);
        put("880", NEW_MEXICO);
        put("881", NEW_MEXICO);
        put("882", NEW_MEXICO);
        put("883", NEW_MEXICO);
        put("884", NEW_MEXICO);
        put("885", TEXAS);
        put("889", NEVADA);
        put("890", NEVADA);
        put("891", NEVADA);
        put("893", NEVADA);
        put("894", NEVADA);
        put("895", NEVADA);
        put("897", NEVADA);
        put("898", NEVADA);
        put("900", CALIFORNIA);
        put("901", CALIFORNIA);
        put("902", CALIFORNIA);
        put("903", CALIFORNIA);
        put("904", CALIFORNIA);
        put("905", CALIFORNIA);
        put("906", CALIFORNIA);
        put("907", CALIFORNIA);
        put("908", CALIFORNIA);
        put("910", CALIFORNIA);
        put("911", CALIFORNIA);
        put("912", CALIFORNIA);
        put("913", CALIFORNIA);
        put("914", CALIFORNIA);
        put("915", CALIFORNIA);
        put("916", CALIFORNIA);
        put("917", CALIFORNIA);
        put("918", CALIFORNIA);
        put("919", CALIFORNIA);
        put("920", CALIFORNIA);
        put("921", CALIFORNIA);
        put("922", CALIFORNIA);
        put("923", CALIFORNIA);
        put("924", CALIFORNIA);
        put("925", CALIFORNIA);
        put("926", CALIFORNIA);
        put("927", CALIFORNIA);
        put("928", CALIFORNIA);
        put("930", CALIFORNIA);
        put("931", CALIFORNIA);
        put("932", CALIFORNIA);
        put("933", CALIFORNIA);
        put("934", CALIFORNIA);
        put("935", CALIFORNIA);
        put("936", CALIFORNIA);
        put("937", CALIFORNIA);
        put("938", CALIFORNIA);
        put("939", CALIFORNIA);
        put("940", CALIFORNIA);
        put("941", CALIFORNIA);
        put("942", CALIFORNIA);
        put("943", CALIFORNIA);
        put("944", CALIFORNIA);
        put("945", CALIFORNIA);
        put("946", CALIFORNIA);
        put("947", CALIFORNIA);
        put("948", CALIFORNIA);
        put("949", CALIFORNIA);
        put("950", CALIFORNIA);
        put("951", CALIFORNIA);
        put("952", CALIFORNIA);
        put("953", CALIFORNIA);
        put("954", CALIFORNIA);
        put("955", CALIFORNIA);
        put("956", CALIFORNIA);
        put("957", CALIFORNIA);
        put("958", CALIFORNIA);
        put("959", CALIFORNIA);
        put("960", CALIFORNIA);
        put("961", CALIFORNIA);
        put("962", ARMED_FORCES_PACIFIC);
        put("963", ARMED_FORCES_PACIFIC);
        put("964", ARMED_FORCES_PACIFIC);
        put("965", ARMED_FORCES_PACIFIC);
        put("966", ARMED_FORCES_PACIFIC);
        put("967", HAWAII);
        put("968", HAWAII);
        put("969", GUAM);
        put("970", OREGON);
        put("971", OREGON);
        put("972", OREGON);
        put("973", OREGON);
        put("974", OREGON);
        put("975", OREGON);
        put("976", OREGON);
        put("977", OREGON);
        put("978", OREGON);
        put("979", OREGON);
        put("980", WASHINGTON);
        put("981", WASHINGTON);
        put("982", WASHINGTON);
        put("983", WASHINGTON);
        put("984", WASHINGTON);
        put("985", WASHINGTON);
        put("986", WASHINGTON);
        put("988", WASHINGTON);
        put("989", WASHINGTON);
        put("990", WASHINGTON);
        put("991", WASHINGTON);
        put("992", WASHINGTON);
        put("993", WASHINGTON);
        put("994", WASHINGTON);
        put("995", ALASKA);
        put("996", ALASKA);
        put("997", ALASKA);
        put("998", ALASKA);
        put("999", ALASKA);
    }});
}
