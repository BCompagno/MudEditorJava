package mudEditor;


import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExitData {
	
	ExitData revExit;
	RoomData toRoom;
	String keyword;
	String description;
	List<eExitFlags> flags = new ArrayList<>();
	int key;
	eExitTypes direction;
	
	
	public ExitData ( RoomData to, eExitTypes dir) {
		this.revExit = null;
		this.toRoom = to;
		this.direction = dir;
		this.keyword = null;
		this.description = null;
		int key = 0;   //change this to an ObjectData
		flags.clear();
		
	}

	public ExitData() {
		
	}

	public RoomData getToRoom() {
		return this.toRoom;
	}

	public eExitTypes getDirection() {
		return this.direction;
	}

	public ExitData getRevExit() {
		return this.revExit;
	}

	public void setToRoom(RoomData room) {
		this.toRoom = room;		
	}

	public void setRevExit(ExitData exit) {
        this.revExit = exit;		
	}
	
	public static eExitTypes getExitType( String type )
	{
		   for( eExitTypes x : eExitTypes.values()  ) {
		      if( type.compareToIgnoreCase( Constants.ConstDirection[x.ordinal()]) == 0 ) {
		         return x;
		      }
		   }
		   return eExitTypes.eSOMEWHERE;
	}
	
	public static List<ExitData> loadExits(Scanner scanner) throws IOException {
		
        List<ExitData> exitList = new ArrayList<>();
        ExitData ex = new ExitData();
        
        
        while (scanner.hasNext()) {
        	String word = scanner.next().trim();
            switch (word) {
            
                case "Description":
                    ex.description = loadLines(scanner);
                    break;
                    
                case "Direction":
                    ex.direction = eExitTypes.valueOf(scanner.next().trim());
                    System.out.println("ExitData load direction: " + ex.direction.toString() );
                    break;
                    
                case "END":
                	exitList.add(ex);
                	break;
                	
                case "ENDEXITS":
                	System.out.println("ExitList:" + exitList);
                	return exitList;
                	
                case "Flags":
                    ex.loadFlags(scanner);
                    break;
                    
                case "Keyword":
                    ex.keyword = scanner.next().trim();
                    break;
                    
                case "Key":
                    ex.key = Integer.parseInt(scanner.next());
                    break;
                    
                default:
                    System.out.println("ExitData.load format error " + word );
                    
            }
        }
        return exitList;
    }

	 private static String loadLines(Scanner scanner) {
	        StringBuilder lines = new StringBuilder();
	        String line;
	        while ((line = scanner.nextLine()) != null && !line.equals("ENDOFDESC")) {
	            lines.append(line).append("\n");
	        }
	        return lines.toString().trim();
	    }


	private List<eExitFlags> loadFlags(Scanner scanner) {
		List<eExitFlags> flags = new ArrayList<>();
		
		String[] flagTokens = scanner.nextLine().split("\\s+");
		for(String flagToken : flagTokens) {
				flags.add(eExitFlags.valueOf(flagToken));
			}
		return flags;
	}

	public void save(BufferedWriter writer) throws IOException {
        writer.write("Direction    " + direction.toString() + "\n");

        if (keyword != null && !keyword.isEmpty()) {
            writer.write("Keyword      " + keyword + "\n");
        }
        if (description != null && !description.isEmpty()) {
            writer.write("Description  " + description + "\nENDOFDESC");
        }
        if (!flags.isEmpty() ) {
            writer.write("Flags        "); for(eExitFlags flag : flags) {
            									 writer.write(flag.toString() + " ");
            							  }
            							  writer.write("\n");
        }
        if (key != 0 ) {
            writer.write("Key          " + key + "\n");
        }
        writer.write("END\n");
    }

}
