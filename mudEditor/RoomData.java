package mudEditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RoomData {
	
	WorldData world;   //the world this room exists in
	Integer vnum;
	Integer height;   //0 equals continent
	Integer xAxis;
	Integer yAxis;
	String name;
	String description;
	
	
	List<ExitData> exits;
	
	eLightTypes light;
	eRoomSector sector;
	
	List<eRoomFlags> flags;
	List<CharacterData> mobs;  
	
	
	//Constructor for loading
	public RoomData(WorldData world) {
		this.world = world;
		this.vnum = 0;
		this.height = 0;
		this.xAxis = 0;
		this.yAxis = 0;
		this.name = "";
		this.description = "";
		
		exits = new ArrayList<>();
		mobs = new ArrayList<>();
		flags = new ArrayList<>();
	}
	
	//constructor for creating rooms and inserting them into the worldmap
	public RoomData(WorldData w, Integer h, Integer x, Integer y) {
		this.world = w;
		this.height = h;
		this.xAxis = x;
		this.yAxis = y;
		this.name = "notset";
		this.description = "notset";
		this.sector = eRoomSector.eWATERNOSWIM;
		this.light = eLightTypes.eNORMAL;
		
		w.incrementTotalRooms();
		
		exits = new ArrayList<>();
		mobs = new ArrayList<>();
		flags = new ArrayList<>();
		flags.add(eRoomFlags.eROOMNOTBUILT);
		
	}
	
	public WorldData getWorld() {
		return this.world;
	}

	public Integer getHeight() {
		return this.height;
	}
	
	public Integer getXAxis() {
		return xAxis;
	}

	public Integer getYAxis() {
		return yAxis;
	}
	
	public void load(BufferedReader reader) {

        try {
    		Scanner scanner = new Scanner(reader);

			while (scanner.hasNext()) {
				String word = scanner.next();

			    switch (word.charAt(0)) {
			        case 'D':
			        	if (word.equals("Description")) {
			        		StringBuilder stringBuilder = new StringBuilder();
			                while (scanner.hasNextLine()) {
			                    String line = scanner.nextLine().trim();
			                    if (line.equals("ENDOFDESC")) {
			                        break;
			                    }
			                    stringBuilder.append(line).append("\n");
			                }
			                description = stringBuilder.toString().trim();
			            }
			            break;

			        case 'E':
			            if (word.equals("ENDROOM")) {
			                return;
			            }
			            break;

			        case 'F':
			            if (word.equals("Flags")) {
			            	String flagsLine = scanner.nextLine().trim();
			                // Split the flagsLine based on whitespace to get individual flags
			                String[] flagTokens = flagsLine.split("\\s+");

			                // Process each flag
			                for (String flagToken : flagTokens) {
			                    // Assuming eRoomFlags is an enum type
			                    eRoomFlags flag = eRoomFlags.valueOf(flagToken);
			                    if (flag != null) {
			                        flags.add(flag);
			                    } else {
			                        System.out.println("Invalid flag: " + flagToken);
			                    }
			                }
			            }
			            break;

			        case 'L':
			            if (word.equals("Light")) {
			                light = eLightTypes.valueOf(scanner.next());
			                
			            }
			            break;

			        case 'N':
			            if (word.equals("Name")) {
			            	 String line = scanner.nextLine().trim();
			            	 name = line.toString().trim();
			            	 if(!scanner.next().equals("~")) {
			            		 System.out.println("RoomData.load: Name not formated properly." );
			            	 } 
			            }
			            break;

			        case 'R':
			            if (word.equals("ROOMEXITS")) {
			                    exits = ExitData.loadExits(scanner);
			                }
			            if (word.equals("ROOMNPCS")) {
			                while (!scanner.nextLine().trim().equals("ENDNPCS")) {
			                    // Note: load npcs
			                }
			            }
			            break;

			        case 'S':
			            if (word.equals("Sector")) {
			                sector = eRoomSector.valueOf(scanner.next());
			                
			            }
			            break;

			        case 'V':
			            if (word.equals("Vnum")) {
			                vnum = Integer.parseInt(scanner.next());
			            }
			            break;

			        case 'W':
			            if (word.equals("WorldHXY")) {
			                world = WorldData.findWorld(scanner.next() );
			                if (world == null) {
			                    System.out.println("World does not exist RoomData::Load(): " + scanner.toString());
			                }
			                height = Integer.parseInt(scanner.next());
			                xAxis = Integer.parseInt(scanner.next());
			                yAxis = Integer.parseInt(scanner.next());
			            }
			            break;

			        default:
			            // Note: add log of this
			            System.out.println("RoomData::Load( fp ) missing word: " + word);
			            break;
			    }
			    // Note: create a boolean to check if everything is ok, if not, delete this room
			}
			scanner.close();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	


	public void save(String path) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.ROOM_DIR + path + Constants.ROOM_EXT))) {
            writer.write("WorldHXY       " + world.getName() + " " + height + " " + xAxis + " " + yAxis + "\n");
            writer.write("Name           " + name + "\n~\n");
            writer.write("Description    " + description + "\nENDOFDESC\n");
            writer.write("Sector         " + sector.toString() + "\n");
            writer.write("Light          " + light.toString() + "\n");
            writer.write("Flags          "); for(eRoomFlags flag : flags) {
            										writer.write(flag.toString() + " ");
            								}
            								writer.write("\n");

            if (!exits.isEmpty()) {
                writer.write("ROOMEXITS\n");
                for (ExitData exit : exits) {
                    exit.save(writer);
                }
                writer.write("ENDEXITS\n");
            }

            if (!mobs.isEmpty()) {
                writer.write("ROOMNPCS\n");
                // for (NpcData npc : mobs) {
                //     npc.save(writer); // Note: Save the character, not the index. Overload for two ways to save.
                // }
                writer.write("ENDNPCS\n");
            }

            writer.write("ENDROOM\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public List<ExitData> getExits() {
		
		return exits;
	}

	public ExitData getExit(eExitTypes dir) {
		for( ExitData ex : exits)
			if(ex.direction.equals(dir)) {
				return ex;
			}
		return null; 
	}
	
	public static eLightTypes GetLightType( String type )
	{
		   for( eLightTypes x : eLightTypes.values()  ) {
		      if( type.equalsIgnoreCase( Constants.ConstDirection[x.ordinal()]) ) {
		         return x;
		      }
		   }
		   return null;
	}

	public Integer getVnum() {
		return vnum;
	}

	public void setVnum(Integer vnum) {
		this.vnum = vnum;
	}

	public Integer getxAxis() {
		return xAxis;
	}

	public void setxAxis(Integer xAxis) {
		this.xAxis = xAxis;
	}

	public Integer getyAxis() {
		return yAxis;
	}

	public void setyAxis(Integer yAxis) {
		this.yAxis = yAxis;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public eLightTypes getLight() {
		return light;
	}

	public void setLight(eLightTypes light) {
		this.light = light;
	}

	public eRoomSector getSector() {
		return sector;
	}

	public void setSector(eRoomSector sector) {
		this.sector = sector;
	}

	public List<eRoomFlags> getFlags() {
		return flags;
	}

	public void setFlags(List<eRoomFlags> flags) {
		this.flags = flags;
	}

	public List<CharacterData> getMobs() {
		return mobs;
	}

	public void setMobs(List<CharacterData> mobs) {
		this.mobs = mobs;
	}

	public void setWorld(WorldData world) {
		this.world = world;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public void setExits(List<ExitData> exits) {
		this.exits = exits;
	}
	
	public void addExit(ExitData exit) {
		exits.add(exit);
	}

}
