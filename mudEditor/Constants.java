package mudEditor;

import java.util.List;

public class Constants {

	public static final String WORLD_LIST = "src/mudEditor/worlds/worlds.txt";
	public static final String WORLD_DIR = "src/mudEditor/worlds/";
	public static final String ROOM_DIR = "src/mudEditor/worlds/rooms/";
	
	public static final String ROOM_EXT = ".room";
	public static final String PLAYER_EXT = ".player";
	public static final String WORLD_EXT = ".world";

			
			
    public static final Integer SEA_LEVEL = 0;     // room heights
	public static final Integer GROUND_LEVEL = 1;
	public static final Integer SKY_LEVEL = 200;   //Max height   //Note: make this so that can be changed in system files, and in doing so, will adjust and save room files

	public static final String ConstDirection[] = {
		"north",
		"south",
	    "east",
		"west",
		"northeast",
	    "northwest",
		"southwest",
		"southeast",
		"down",
		"up",
		"somewhere"
	};
	
	public static final String ConstLight[] = {
		"pitchblack",
		"verydark",
		"dark",
		"dim",
		"candlelit",
		"torchlit",
		"normal",
		"sunlit",
		"bright",
		"verybright",
	    "blinding"
	};	
	
//COLORS---------------------------
	//ANSI codes for foreground text colors
	public static final String ANSI_BLACK   = 	"\033[0;30m";
	public static final String ANSI_DRED    =	"\033[0;31m";
	public static final String ANSI_DGREEN  =  "\033[0;32m";
	public static final String ANSI_ORANGE  =  	"\033[0;33m";
	public static final String ANSI_DBLUE   = 	"\033[0;34m";
	public static final String ANSI_PURPLE  =  	"\033[0;35m";
	public static final String ANSI_CYAN	=  	   "\033[0;36m";
	public static final String ANSI_GREY	=	   "\033[0;37m";
	public static final String ANSI_DGREY	=   "\033[1;30m";
	public static final String ANSI_RED		=   "\033[1;31m";
	public static final String ANSI_GREEN	=   "\033[1;32m";
	public static final String ANSI_YELLOW  = 	"\033[1;33m";
	public static final String ANSI_BLUE	=	   "\033[1;34m";
	public static final String ANSI_PINK	=	   "\033[1;35m";
	public static final String ANSI_LBLUE   =	"\033[1;36m";
	public static final String ANSI_WHITE   =	"\033[1;37m";

	//ANSI codes for blinking foreground text colors
	public static final String BLINK_BLACK	=	"\033[0;5;30m";
	public static final String BLINK_DRED	=	"\033[0;5;31m";
	public static final String BLINK_DGREEN	=	"\033[0;5;32m";
	public static final String BLINK_ORANGE	=	"\033[0;5;33m";
	public static final String BLINK_DBLUE	=	"\033[0;5;34m";
	public static final String BLINK_PURPLE	=	"\033[0;5;35m";
	public static final String BLINK_CYAN	=	"\033[0;5;36m";
	public static final String BLINK_GREY	=	"\033[0;5;37m";
	public static final String BLINK_DGREY	=	"\033[1;5;30m";
	public static final String BLINK_RED	=		"\033[1;5;31m";
	public static final String BLINK_GREEN	=	"\033[1;5;32m";
	public static final String BLINK_YELLOW	=	"\033[1;5;33m";
	public static final String BLINK_BLUE	=	"\033[1;5;34m";
	public static final String BLINK_PINK	=	"\033[1;5;35m";
	public static final String BLINK_LBLUE	=	"\033[1;5;36m";
	public static final String BLINK_WHITE	=	"\033[1;5;37m";

	//ANSI codes for background colors
	public static final String BACK_BLACK 	=   "\033[40m";
	public static final String BACK_DRED  	=   "\033[41m";
	public static final String BACK_DGREEN	=   "\033[42m";
	public static final String BACK_ORANGE  =   "\033[43m";
	public static final String BACK_DBLUE   =   "\033[44m";
	public static final String BACK_PURPLE  =   "\033[45m";
	public static final String BACK_CYAN    =   "\033[46m";
	public static final String BACK_GREY    =   "\033[47m";
	public static final String BACK_DGREY   = 	"\033[50m";
	public static final String BACK_RED     =  	"\033[51m";
	public static final String BACK_GREEN   = 	"\033[52m";
	public static final String BACK_YELLOW  =  	"\033[53m";
	public static final String BACK_BLUE    =	"\033[54m";
	public static final String BACK_PINK   	=   "\033[55m";
	public static final String BACK_LBLUE   = 	"\033[56m";
	public static final String BACK_WHITE   = 	"\033[57m";

	//ANSI codes for miscelaneous
	public static final String ANSI_RESET	=  "\033[0m";   // Reset to terminal default
	public static final String ANSI_BOLD	=   "\033[1m";   // For bright color stuff
	public static final String ANSI_ITALIC	=   "\033[3m";   // Italic text
	public static final String ANSI_UNDERLINE = "\033[4m";  // Underline text
	public static final String ANSI_BLINK	=   "\033[5m";   // Blinking text
	public static final String ANSI_REVERSE =   "\033[7m";  // Reverse colors
	public static final String ANSI_STRIKEOUT = "\033[9m";   // Overstrike line
	
	public static String roomFlagsToBinary(List<eRoomFlags> flags) {
        StringBuilder binaryFlags = new StringBuilder();
        for (eRoomFlags flag : flags) {
            binaryFlags.append(flag.ordinal() == 1 ? '1' : '0');
        }
        return binaryFlags.toString();
    }
	
	public static String exitFlagsToBinary(List<eExitFlags> flags) {
        StringBuilder binaryFlags = new StringBuilder();
        for (eExitFlags flag : flags) {
            binaryFlags.append(flag.ordinal() == 1 ? '1' : '0');
        }
        return binaryFlags.toString();
    }
	

}
