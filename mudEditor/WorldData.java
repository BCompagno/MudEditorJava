package mudEditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class WorldData {

	private static List<String> areas = new ArrayList<>();
	public static List<WorldData> worlds = new ArrayList<>();

	// map of height to map of xAxis to map of yAxis to map of rooms
	private Map<Integer, NorthSouth> hXYMap = new HashMap<>();
	private Map<Integer, WestEast> xAxis = new HashMap<>();
	private Map<Integer, RoomData> yAxis = new HashMap<>();

	private String author;
	private String name;
	private int north; // Always a positive number
	private int east; // always a positive number
	private int south; // always negative
	private int west; // always negative
	private int totalRooms;

	/*
	 * //constructor for author creation public WorldData( AuthorData author, String
	 * str ) { this author = author.name; this.name = str; this.initialize();
	 * CreateFirstRoom( author ); }
	 */
	// constructor for loadWorlds() on startup
	public WorldData(BufferedReader reader) {
		initialize();
		try {
			load(reader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//constructor that has not been initialized yet
	public WorldData() {
		this.name = "NewWorld";
	}
	
	//constructor used for creation
	public WorldData( String name) {
		this.name = name;
		initialize();
		createFirstRoom();
	}

	public static List<String> getAreas() {
		return areas;
	}

	public static void setAreas(List<String> areas) {
		WorldData.areas = areas;
	}

	public static List<WorldData> getWorlds() {
		return worlds;
	}

	public static void setWorlds(List<WorldData> worlds) {
		WorldData.worlds = worlds;
	}

	public Map<Integer, NorthSouth> gethXYMap() {
		return hXYMap;
	}

	public void sethXYMap(Map<Integer, NorthSouth> hXYMap) {
		this.hXYMap = hXYMap;
	}

	public Map<Integer, WestEast> getxAxis() {
		return xAxis;
	}

	public void setxAxis(Map<Integer, WestEast> xAxis) {
		this.xAxis = xAxis;
	}

	public Map<Integer, RoomData> getyAxis() {
		return yAxis;
	}

	public void setyAxis(Map<Integer, RoomData> yAxis) {
		this.yAxis = yAxis;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getNorth() {
		return north;
	}

	public void setNorth(int north) {
		if (north > 0) {
			this.north = north;
		}
	}

	public int getEast() {
		return east;
	}

	public void setEast(int east) {
		if (east > 0) {
			this.east = east;
		}
	}

	public int getSouth() {
		return south;
	}

	public void setSouth(int south) {
		if (south < 0) {
			this.south = south;
		}
	}

	public int getWest() {
		return west;
	}

	public void setWest(int west) {
		if (west < 0) {
			this.west = west;
		}
	}

	public int getTotalRooms() {
		return totalRooms;
	}

	public void setTotalRooms(int totalRooms) {
		this.totalRooms = totalRooms;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	public void load(BufferedReader reader) throws IOException {
		System.out.println("Loading world data...");
		
		try (Scanner scanner = new Scanner(reader)) {
			while (scanner.hasNext()) {
				
				String word = scanner.next();
				
				if (!word.isEmpty()) {
					
					switch (word.charAt(0)) {
					/*case 'A':
						if (word.equals("Areas")) {
							// loadList(reader, areas);
						}
						break;*/

					case 'E':
						if (word.equals("East")) {
							east = Integer.parseInt(scanner.next().trim());
						}
						break;

					case 'N':
						if (word.equals("Name")) {
							name = scanner.next().trim(); 
						} else if (word.equals("North")) {
							north = Integer.parseInt(scanner.next().trim());
						}
						break;

					case 'R':
						if (word.equals("ROOMS")) {
							int count = 0;
							String roomWord;

							while (!(roomWord = scanner.next()).equals("ENDROOMS")) {
								// Assuming ROOM_DIR is a constant representing the directory path
								String roomFilePath = Constants.ROOM_DIR + name + "_" + roomWord + ".room";
								try (BufferedReader rfp = new BufferedReader(new FileReader(roomFilePath))) {
									count++;
									RoomData room = new RoomData(this);
									room.load(rfp);

									if (!insertRoom(room)) {
										// Note: log this
										System.out.println("ERROR: Room already exists at these coordinates:");
										System.out.println("Height: " + room.getHeight());
										System.out.println("XAxis: " + room.getXAxis());
										System.out.println("YAxis: " + room.getYAxis());
										System.out.println("Deleting new room.");

										count--;
									}
								} catch (IOException e) {
									System.out.println("ERROR: No room file: " + name + "_" + roomWord);
								}
							}
							if (count != totalRooms) {
								// Note: log this
								System.out.println("ERROR: world totalRooms not equal to rooms loaded count: " + count);
							}
						}
						break;

					case 'S':
						if (word.equals("South")) {
							south = Integer.parseInt(scanner.next().trim());
						}
						break;

					case 'T':
						if (word.equals("TotalRooms")) {
							totalRooms = Integer.parseInt(scanner.next().trim());
						}
						break;

					case 'W':
						if (word.equals("West")) {
							west = Integer.parseInt(scanner.next().trim());
						}
						break;

					default:
						// Note: add log of this
						System.out.println("WorldData::Load() missing word: " + word);
						break;
					}
				}
			}
			scanner.close();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (!hXYMap.isEmpty()) {
			fixExits();
			System.out.println("Fixing exits completed");
		}
	}

	private boolean insertRoom(RoomData room) {
	    Map<Integer, WestEast> xAxisMap = hXYMap.computeIfAbsent(room.getHeight(), k -> new NorthSouth()).rows;
	    
	    if (!xAxisMap.containsKey(room.getXAxis())) {
	        WestEast yAxisMap = new WestEast();
	        yAxisMap.rooms.put(room.getYAxis(), room);
	        xAxisMap.put(room.getXAxis(), yAxisMap);
	        return true;
	    } else {
	        WestEast yAxisMap = xAxisMap.get(room.getXAxis());

	        if (!yAxisMap.rooms.containsKey(room.getYAxis())) {
	            yAxisMap.rooms.put(room.getYAxis(), room);
	            return true;
	        } else {
	            return false;
	        }
	    }
	}


	void initialize() {
		north = 0;
		east = 0;
		south = 0;
		west = 0;
		areas.clear();
		hXYMap.clear();
		totalRooms = 0;// Note: make sure this is always initialized
		worlds.add(this);
	}

	public void save() {
		

			// Write world details to a new file
			try (BufferedWriter worldWriter = new BufferedWriter(
					new FileWriter(Constants.WORLD_DIR + name + Constants.WORLD_EXT))) {
				worldWriter.write("Name           " + name + "\n");
				worldWriter.write("North          " + north + "\n");
				worldWriter.write("East           " + east + "\n");
				worldWriter.write("South          " + south + "\n");
				worldWriter.write("West           " + west + "\n");
				worldWriter.write("TotalRooms     " + totalRooms + "\n");

				/*worldWriter.write("Areas          ");
				if (areas == null || areas.isEmpty()) {
					worldWriter.write("none");
				} else {
					for (String area : areas) {
						worldWriter.write(area + " ");
					}
				}*/
				worldWriter.write("\n\n");

				worldWriter.write("ROOMS\n");
				for (Map.Entry<Integer, NorthSouth> heightEntry : hXYMap.entrySet()) {
		            for (Map.Entry<Integer, WestEast> xAxisEntry : heightEntry.getValue().rows.entrySet()) {
		                for (Map.Entry<Integer, RoomData> roomEntry : xAxisEntry.getValue().rooms.entrySet()) {
		                    RoomData room = roomEntry.getValue();

		                    // Build the coordinate string
		                    String coordinates = heightEntry.getKey() + "_" + xAxisEntry.getKey() + "_" + roomEntry.getKey();

		                    // Write the coordinates to the file
		                    worldWriter.write(coordinates + "\n");

		                    // Save the room
		                    room.save(name + "_" + coordinates);
		                }
		            }
		        }
				worldWriter.write("ENDROOMS\n\n");

				worldWriter.write("ENDWORLD\n");
			} catch (IOException e) {
			e.printStackTrace();
			}
		System.out.println("WorldData saved");
	}
	
	public void writeList() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.WORLD_LIST))) {
			// Iterate over the worlds in the ArrayList and write each world name to the file
	        for (WorldData world : WorldData.worlds) {
	            writer.write(world.getName() + "\n");
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void fixExits() {
		//System.out.println("Fixing exits...");
		for (Map.Entry<Integer, NorthSouth> heightEntry : hXYMap.entrySet()) {
			for (Map.Entry<Integer, WestEast> xAxisEntry : xAxis.entrySet()) {
				for (Map.Entry<Integer, RoomData> yAxisEntry : yAxis.entrySet()) {
					for (ExitData exit : yAxisEntry.getValue().getExits()) {
						if (exit.getToRoom() == null) {
							int x = xAxisEntry.getKey();
							int y = yAxisEntry.getKey();
							int height = heightEntry.getKey();

							switch (exit.getDirection()) {
							case eNORTH:
								exit.setToRoom(findRoomMap(height, x + 1, y));
								break;
							case eSOUTH:
								exit.setToRoom(findRoomMap(height, x - 1, y));
								break;
							case eEAST:
								exit.setToRoom(findRoomMap(height, x, y + 1));
								break;
							case eWEST:
								exit.setToRoom(findRoomMap(height, x, y - 1));
								break;
							case eNORTHEAST:
								exit.setToRoom(findRoomMap(height, x + 1, y + 1));
								break;
							case eNORTHWEST:
								exit.setToRoom(findRoomMap(height, x + 1, y - 1));
								break;
							case eSOUTHEAST:
								exit.setToRoom(findRoomMap(height, x - 1, y + 1));
								break;
							case eSOUTHWEST:
								exit.setToRoom(findRoomMap(height, x - 1, y - 1));
								break;
							case eUP:
								exit.setToRoom(findRoomMap(height + 1, x, y));
								break;
							case eDOWN:
								exit.setToRoom(findRoomMap(height - 1, x, y));
								break;
							case eSOMEWHERE:
								// NOTE: need to add this method here or in room and exit loading
							}
						}
						if (exit.getRevExit() == null) {
							exit.setRevExit(exit.getToRoom().getExit(exit.direction.reverse()));
						}
					}
				}
			}
		}
	}
	
	


	/*
	 * private RoomData findRoomMap(Integer h, Integer x, Integer y) {
	 * 
	 * // Get the NorthSouth map using the height key NorthSouth northSouth =
	 * hXYMap.get(h);
	 * 
	 * if (northSouth != null) { // Get the WestEast map using the xAxisKey WestEast
	 * westEast = northSouth.get(x);
	 * 
	 * if (westEast != null) { // Get the RoomData using the yAxisKey return
	 * westEast.get(y); } }
	 * 
	 * // Return null if any of the maps is not found or if RoomData is not found
	 * return null; }
	 * 
	 */

	public static WorldData findWorld(String name) {
		for (WorldData w : worlds) {
			if (w.getName().equals(name)) {
				return w;
			}
		}
		return null;
	}

	public RoomData findRoomMap(Integer h, Integer x, Integer y) {
		// Debug print for troubleshooting
		//System.out.println("Finding room for h=" + h + ", x=" + x + ", y=" + y);

		// Get the NorthSouth map using the height key
		NorthSouth northSouth = hXYMap.get(h);
		if (northSouth != null) {
			// Debug print for troubleshooting
			//System.out.println("NorthSouth map found");

			// Get the WestEast map using the xAxisKey
			WestEast westEast = northSouth.getWestEast(x);

			if (westEast != null) {
				// Debug print for troubleshooting
				//System.out.println("WestEast map found");

				// Get the RoomData using the yAxisKey
				RoomData room = westEast.getRoomData(y);

				if (room != null) {
					// Debug print for troubleshooting
					//System.out.println("Room found: " + room.getName());

					return room;
				} else {
					// Debug print for troubleshooting
					//System.out.println("Room not found for y=" + y);
				}
			} else {
				// Debug print for troubleshooting
				System.out.println("WestEast map not found");
			}
		} else {
			// Debug print for troubleshooting
			System.out.println("NorthSouth map not found");
		}

		// Return null if any of the maps is not found or if RoomData is not found
		return null;
	}
	
	public static void removeWorld(String worldName) {
        WorldData worldToRemove = findWorld(worldName);
        if (worldToRemove != null) {
            worlds.remove(worldToRemove);
        }
    }
	
	public void createFirstRoom() {
	    
	    WestEast y = new WestEast();
	    NorthSouth x = new NorthSouth();
	    
	    RoomData room = new RoomData(this, Constants.SKY_LEVEL, 0, 0);
	    
	    
	    y.rooms.put(0, room);
	    x.rows.put(0, y);
	    hXYMap.put(Constants.SKY_LEVEL, x);
	}
	
	public void incrementTotalRooms() {
		totalRooms++;
	}
	
	//method for synchronizing and adding rooms and linking all the exits
	public boolean pushNorth(int rows) {
		//make sure the world is not shrinking, since this is meant to build, not delete
		//also put a limit for now
	    if (rows <= north || rows >=10000) {
	        return false;
	    }
	    //find the xAxis map in the hXYMap at the sky limit since this is the world overview
	    NorthSouth northSouth = hXYMap.get(Constants.SKY_LEVEL);
	    //first row is an int also used as a bool for determining the first row and location
	    int firstRowPointer = north;
	    boolean firstRow = true;
	    
	    //while we increment to rows north
	    while (north < rows) {
	        north++;
	        //using cp as a column pointer
	        int cp = west; //starts at the west
	        WestEast newRow = new WestEast();
	        
	        //while the column pointer has not reached the east limit
	        while (cp <= east) {
	        	//found the position in the map needed to create and insert a new room
	            RoomData room = new RoomData(this, Constants.SKY_LEVEL, north, cp);
	            //run a check to see if we are at the outer edges of the map for exit linking
	            if (firstRow ) {
	                RoomData toRoom = findRoomMap(Constants.SKY_LEVEL, firstRowPointer, cp);
	                //if there is a room, link the exit
	                if (toRoom != null ) {
	                    if (cp > west) { //if there are rooms west, add exit
	                        ExitData exit = new ExitData(null, eExitTypes.eNORTHWEST);
	                        toRoom.addExit(exit);
	                    }
	                    if (cp < east) { //if there are rooms east, add exit
	                        ExitData exit = new ExitData(null, eExitTypes.eNORTHEAST);
	                        toRoom.addExit(exit);
	                    }
	                    //add the north exit because if here, there is definitily a room south
	                    ExitData exit = new ExitData(null, eExitTypes.eNORTH);
                        toRoom.addExit(exit);
                        System.out.println("First room exit made" );
	                }
	            }
	            //create all the exits needed in the new room
	            if (north > 0 || south < 0) {  //if there is a room south of this room
	                ExitData exit = new ExitData(null, eExitTypes.eSOUTH);
	                room.addExit(exit);

	                if (cp > west) {  //if there are rooms west, add a southwest
	                    ExitData ex = new ExitData(null, eExitTypes.eSOUTHWEST);
	                    room.addExit(ex);
	                }
	                if (cp < east) {   //if there are rooms east, add a southeast
	                    ExitData ex = new ExitData(null, eExitTypes.eSOUTHEAST);
	                    room.addExit(ex);
	                }
	            }

	            if (north < rows) {   //if there are still rooms to be added to north
	                ExitData exit = new ExitData(null, eExitTypes.eNORTH);
	                room.addExit(exit);

	                if (cp > west) {   //if there are rooms west, add a northwest
	                    ExitData ex = new ExitData(null, eExitTypes.eNORTHWEST);
	                    room.addExit(ex);
	                }
	                if (cp < east) {   //if there are rooms to the east, add a northeast
	                    ExitData ex = new ExitData(null, eExitTypes.eNORTHEAST);
	                    room.addExit(ex);
	                }
	            }

	            if (cp > west) {    //if there are rooms west, add a west exit
	                ExitData exit = new ExitData(null, eExitTypes.eWEST);
	                room.addExit(exit);
	            }

	            if (cp < east) {   //if there are rooms to the east, add a east exit
	                ExitData exit = new ExitData(null, eExitTypes.eEAST);
	                room.addExit(exit);
	            }

	            newRow.rooms.put(cp, room); //insert the room into the row

	            cp++;   //increment the colmn pointer one step closer east
	        }

	        firstRow = false;  //we won't need the firstRow int marker after the first row has been iterated
	        				//a negative, since north should always be positive
	        northSouth.rows.put(north, newRow); //insert the row into the columns
	    }

	    fixExits();
	    return true;
	}

	public boolean pushSouth(int rows) {
	    // Make sure the world is not shrinking, as this is meant to build, not delete
	    // Also, put a limit for now
	    if (rows >= south || rows <= -10000) {
	        return false;
	    }

	    // Find the xAxis map in the hXYMap at the sky limit since this is the world overview
	    NorthSouth northSouth = hXYMap.get(Constants.SKY_LEVEL);
	    // First row is an int also a bool for determining the first row and location
	    boolean firstRow = true;
	    int firstRowPointer = south;
	    
	    // While we increment to rows south
	    while (south > rows) {
	        south--; // Decrement south for negative coordinates
	        // Using cp as a column pointer
	        int cp = west; // Starts at the west
	        WestEast newRow = new WestEast();

	        // While the column pointer has not reached the east limit
	        while (cp <= east) {
	            // Found the position in the map needed to create and insert a new room
	            RoomData room = new RoomData(this, Constants.SKY_LEVEL, south, cp);
	            // Run a check to see if we are at the outer edges of the map for exit linking
	            if (firstRow ) {
	                RoomData toRoom = findRoomMap(Constants.SKY_LEVEL, firstRowPointer, cp);
	                // If there is a room, link the exit
	                if (toRoom != null) {
	                    if (cp > west) { // If there are rooms west, add exit
	                        ExitData exit = new ExitData(null, eExitTypes.eSOUTHWEST);
	                        toRoom.addExit(exit);
	                    }
	                    if (cp < east) { // If there are rooms east, add exit
	                        ExitData exit = new ExitData(null, eExitTypes.eSOUTHEAST);
	                        toRoom.addExit(exit);
	                    }
	                    // Add the south exit
	                    ExitData exit = new ExitData(null, eExitTypes.eSOUTH);
	                    toRoom.addExit(exit);
	                }
	            }
	            // Create all the exits needed in the new room
	            if (south < 0 || north > 0) { // If there is a room north of this room
	                ExitData exit = new ExitData(null, eExitTypes.eNORTH);
	                room.addExit(exit);

	                if (cp > west) { // If there are rooms west, add a northwest
	                    ExitData ex = new ExitData(null, eExitTypes.eNORTHWEST);
	                    room.addExit(ex);
	                }
	                if (cp < east) { // If there are rooms east, add a northeast
	                    ExitData ex = new ExitData(null, eExitTypes.eNORTHEAST);
	                    room.addExit(ex);
	                }
	            }

	            if (south > rows) { // If there are still rooms to be added to south
	                ExitData exit = new ExitData(null, eExitTypes.eSOUTH);
	                room.addExit(exit);

	                if (cp > west) { // If there are rooms west, add a southwest
	                    ExitData ex = new ExitData(null, eExitTypes.eSOUTHWEST);
	                    room.addExit(ex);
	                }
	                if (cp < east) { // If there are rooms east, add a southeast
	                    ExitData ex = new ExitData(null, eExitTypes.eSOUTHEAST);
	                    room.addExit(ex);
	                }
	            }

	            if (cp > west) { // If there are rooms west, add a west exit
	                ExitData exit = new ExitData(null, eExitTypes.eWEST);
	                room.addExit(exit);
	            }

	            if (cp < east) { // If there are rooms to the east, add an east exit
	                ExitData exit = new ExitData(null, eExitTypes.eEAST);
	                room.addExit(exit);
	            }

	            newRow.rooms.put(cp, room); // Insert the room into the row

	            cp++; // Increment the column pointer one step closer east
	        }

	        firstRow = false; // We won't need the firstRow bool after the first row iterated
	        northSouth.rows.put(south, newRow); // Insert the row into the columns
	    }

	    fixExits();
	    return true;
	}

	public boolean pushEast(int columns) {
	    // Make sure the world is not shrinking since this is meant to build, not delete
	    // Also, put a limit for now
	    if (columns <= east || columns >= 10000) {
	        return false;
	    }
	    
	    // First column is an int also used as a boolean for determining the first column and location
	    int firstColumn = east;
	    int rp = south;  //column pointer
	    int cp = east;   //row pointer
	    
        // Find the yAxis map in the hXYMap at the sky limit since this is the world overview
	    NorthSouth columnMap = hXYMap.get(Constants.SKY_LEVEL);
	    

	    // While we increment the rows starting at the south until the north limit
	    while (rp <= north) {
	    	 
	    	//grab a row from the columns so we can add rooms
	     	WestEast rowMap = columnMap.getWestEast(rp);
  
	    	//we increment the columns from west to east
	    	while( cp < columns) {
 
	            // check to see if there are columns created to link up the exits
	            if (firstColumn == cp ) {
	                RoomData toRoom = findRoomMap(Constants.SKY_LEVEL, rp, firstColumn);
	                // If there is a room, link the exit
	                if (toRoom != null) {
	                    if (rp > south) { // If there are rooms south, add exit
	                        ExitData exit = new ExitData(null, eExitTypes.eSOUTHEAST);
	                        toRoom.addExit(exit);
	                    }
	                    if (rp < north) { // If there are rooms north, add exit
	                        ExitData exit = new ExitData(null, eExitTypes.eNORTHEAST);
	                        toRoom.addExit(exit);
	                    }
	                    // Add the east exit
	                    ExitData exit = new ExitData(null, eExitTypes.eEAST);
	                    toRoom.addExit(exit);
	                }
	            }
	          
	            cp++;
	            // Found the position in the map needed to create and insert a new room
	            RoomData room = new RoomData(this, Constants.SKY_LEVEL, rp, cp);
	            // Create all the exits needed in the new room
	            if (true) { // If there is a room west of this room
	                ExitData exit = new ExitData(null, eExitTypes.eWEST);
	                room.addExit(exit);

	                if (rp > south) { // If there are rooms south, add a southeast
	                    ExitData ex = new ExitData(null, eExitTypes.eSOUTHWEST);
	                    room.addExit(ex);
	                }
	                if (rp < north) { // If there are rooms north, add a northeast
	                    ExitData ex = new ExitData(null, eExitTypes.eNORTHWEST);
	                    room.addExit(ex);
	                }
	            }
	            if (cp < columns) { // If there are still rooms to be added to east
	                ExitData exit = new ExitData(null, eExitTypes.eEAST);
	                room.addExit(exit);

	                if (rp > south) { // If there are rooms south, add a southwest
	                    ExitData ex = new ExitData(null, eExitTypes.eSOUTHEAST);
	                    room.addExit(ex);
	                }
	                if (rp < north) { // If there are rooms north, add a northwest
	                    ExitData ex = new ExitData(null, eExitTypes.eNORTHEAST);
	                    room.addExit(ex);
	                }
	            }

	            if (rp > south) { // If there are rooms south, add a south exit
	                ExitData exit = new ExitData(null, eExitTypes.eSOUTH);
	                room.addExit(exit);
	            }

	            if (rp < north) { // If there are rooms north, add a north exit
	                ExitData exit = new ExitData(null, eExitTypes.eNORTH);
	                room.addExit(exit);
	            }

	            rowMap.rooms.put(cp, room); // Insert the room into the column

	        }
	        cp = firstColumn; //reset thr column pointer back to the   
	        ++rp; // Increment the row pointer one step closer north
	    }
	    east = columns;
	    fixExits();
	    return true;
	    
	}//End of PushEast()
	
	
	public boolean pushWest(int columns) {
	    // Make sure the world is not shrinking since this is meant to build, not delete
	    // Also, put a limit for now
	    if (columns >= west || columns < -10000) {
	        return false;
	    }
	    
	    // First column is an int also used as a boolean for determining the first column and location
	    int firstColumn = west;
	    int rp = south;  //column pointer
	    int cp = west;   //row pointer
	    
        // Find the yAxis map in the hXYMap at the sky limit since this is the world overview
	    NorthSouth columnMap = hXYMap.get(Constants.SKY_LEVEL);
	    

	    // While we increment the rows starting at the south until the north limit
	    while (rp <= north) {
	    	 
	    	//grab a row from the columns so we can add rooms
	     	WestEast rowMap = columnMap.getWestEast(rp);
  
	    	//we increment the columns from west to east
	    	while( cp > columns) {
	            // check to see if there are columns created to link up the exits
	            if (firstColumn == cp ) {
	            	
	                RoomData toRoom = findRoomMap(Constants.SKY_LEVEL, rp, firstColumn);
	                // If there is a room, link the exit
	                if (toRoom != null) {
	                    if (rp > south) { // If there are rooms south, add exit
	                        ExitData exit = new ExitData(null, eExitTypes.eSOUTHWEST);
	                        toRoom.addExit(exit);
	                    }
	                    if (rp < north) { // If there are rooms north, add exit
	                        ExitData exit = new ExitData(null, eExitTypes.eNORTHWEST);
	                        toRoom.addExit(exit);
	                    }
	                    // Add the east exit
	                    ExitData exit = new ExitData(null, eExitTypes.eWEST);
	                    toRoom.addExit(exit);
	                }
	            }
	          
	            cp--;
	            // Found the position in the map needed to create and insert a new room
	            RoomData room = new RoomData(this, Constants.SKY_LEVEL, rp, cp);
	            
	            // Create all the exits needed in the new room
	            if (east > 0 || west < 0) { // If there is a room west of this room
	                ExitData exit = new ExitData(null, eExitTypes.eEAST);
	                room.addExit(exit);

	                if (rp > south) { // If there are rooms south, add a southeast
	                    ExitData ex = new ExitData(null, eExitTypes.eSOUTHEAST);
	                    room.addExit(ex);
	                }
	                if (rp < north) { // If there are rooms north, add a northeast
	                    ExitData ex = new ExitData(null, eExitTypes.eNORTHEAST);
	                    room.addExit(ex);
	                }
	            }
	            if (cp > columns) { // If there are still rooms to be added to east
	                ExitData exit = new ExitData(null, eExitTypes.eWEST);
	                room.addExit(exit);

	                if (rp > south) { // If there are rooms south, add a southwest
	                    ExitData ex = new ExitData(null, eExitTypes.eSOUTHWEST);
	                    room.addExit(ex);
	                }
	                if (rp < north) { // If there are rooms north, add a northwest
	                    ExitData ex = new ExitData(null, eExitTypes.eNORTHWEST);
	                    room.addExit(ex);
	                }
	            }

	            if (rp > south) { // If there are rooms south, add a south exit
	                ExitData exit = new ExitData(null, eExitTypes.eSOUTH);
	                room.addExit(exit);
	            }

	            if (rp < north) { // If there are rooms north, add a north exit
	                ExitData exit = new ExitData(null, eExitTypes.eNORTH);
	                room.addExit(exit);
	            }

	            rowMap.rooms.put(cp, room); // Insert the room into the column

	        }
	        cp = firstColumn; //reset the column pointer back to the   
	        ++rp; // Increment the row pointer one step closer north
	    }
	    west = columns;
	    fixExits();
	    return true;
	    
	}//End of PushEast()
	
}



//Used in the static maps of maps for all the rooms in a 3d grid

class NorthSouth {
	Map<Integer, WestEast> rows = new HashMap<>();

	public WestEast getWestEast(Integer x) {

		return rows.get(x);
	}

}

class WestEast {
	Map<Integer, RoomData> rooms = new HashMap<>();

	public RoomData getRoomData(Integer y) {
		return rooms.get(y);
	}

}
