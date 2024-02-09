package mudEditor;

public enum eRoomSector {
	
	eINSIDE("inside", Constants.ANSI_DGREY + "#"),
	eDIRT("dirt", Constants.ANSI_ORANGE + "."),
	eFIELD("field", Constants.ANSI_GREEN + "."),
	eFOREST("forest", Constants.ANSI_GREEN + "@"),
	eHILLS("hills", Constants.ANSI_GREEN + "^"),
	eMOUNTAIN("mountain", Constants.ANSI_DGREY + "^"),
	eWATERSWIM("waterswim", Constants.ANSI_BLUE + "~"), 
	eWATERNOSWIM("waternoswim", Constants.ANSI_DBLUE + "~"),
	eUNDERWATER("underwater", Constants.ANSI_BLUE + "O"),
	eAIR("air", Constants.ANSI_WHITE + "@"),
	eDESERT("desert", Constants.ANSI_YELLOW + "."),
	eMYSTERY("mystery", Constants.ANSI_RED + "?"),
	eOCEANFLOOR("oceanfloor", Constants.ANSI_BLUE + "-"),
	eUNDERGROUND("underground", Constants.ANSI_DGREY + "."),
	eLAVA("lava", Constants.ANSI_RED + "~"),
	eSWAMP("swamp", Constants.ANSI_DGREY + "~"),
	eDOCK("dock", Constants.ANSI_YELLOW + "="),
	eKINGDOM("kingdom", Constants.ANSI_RED + "#"),
	eCITY("city", Constants.ANSI_BLUE + "#"),
	eTOWN("town", Constants.ANSI_YELLOW + "#"),
	eSTREET("street", Constants.ANSI_BLUE + "."),
	eBLDSITE("bldsite", Constants.ANSI_CYAN + "%%"),
	eBLDRUBBLE("bldrubble", Constants.ANSI_GREY + "%%"),
	eWALLNS("wallNS", Constants.ANSI_GREY + "-"),
	eWALLEW("wallEW", Constants.ANSI_GREY + "|"),
	eWALLNESW("wallNESW", Constants.ANSI_GREY + "\\"),
	eWALLNWSE("wallNWSE", Constants.ANSI_GREY + "/"),
	eSECTORPROB("sectorprob", Constants.ANSI_DRED + "?"),
	eSECTINROOM("sectinroom", Constants.ANSI_RED + "*"),
	   
	eMAXROOMSECTOR("maxroomsector", Constants.BLINK_YELLOW + "?");
	
	String constant;
	String sectorDisplay;

	private eRoomSector( String constant, String sectorConst) {
		this.constant = constant;
		this.sectorDisplay = sectorConst;
	}
	
	
};
