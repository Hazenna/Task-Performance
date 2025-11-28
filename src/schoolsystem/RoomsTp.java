/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schoolsystem;

import java.util.*;

/**
 *
 * @author Xion
 */
class Room {

    private int roomId;
    private String name;

    public Room(int roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public String getInfo() {
        return "Room " + roomId + ": " + name;
    }

    public String isAvailable() {
        return "yes";
    }

    public int getRoomId() {
        return roomId;
    }

    public String getName() {
        return name;
    }
}

class School {

    private HashMap<Integer, Room> rooms;

    public School() {
        this.rooms = new HashMap<>();
        Rooms();
    }

    private void Rooms() {
        rooms.put(1, new Room(1, "Room 101"));
        rooms.put(2, new Room(2, "Room 102"));
        rooms.put(3, new Room(3, "Room 103"));
        rooms.put(4, new Room(4, "Room 104"));
        rooms.put(5, new Room(5, "Room 105"));
    }

    public Room getRoom(int roomId) {
        return rooms.get(roomId);
    }

    public HashMap<Integer, Room> getAllRooms() {
        return rooms;
    }
}

public class RoomsTp {

    static Scanner s = new Scanner(System.in);
    static Teachers t = new Teachers();
    static School school = new School();

    public static void menuView() {

        System.out.println("All Rooms:");
        for (Room r : school.getAllRooms().values()) {
            System.out.println(r.getInfo());
        }

        System.out.println("\nAvailable Rooms:");
        for (Room r : school.getAllRooms().values()) {
            if ("yes".equals(r.isAvailable())) {
                System.out.println(r.getInfo());
            }
        }

        boolean sentinel = true;

        while (sentinel) {
            System.out.print("Press B to go back or E to exit: ");
            String back = s.nextLine().trim();

            if (back.equalsIgnoreCase("b")) {
                t.teacherUserMenu();
                sentinel = false;
            } else if (back.equalsIgnoreCase("e")) {
                System.exit(0);
            } else {
                System.out.println("Unknown input");
            }
        }
    }

    public static void menuBooking() {

        System.out.println("All Rooms:");
        for (Room r : school.getAllRooms().values()) {
            System.out.println(r.getInfo());
        }

        System.out.println("\nAvailable Rooms:");
        for (Room r : school.getAllRooms().values()) {
            if ("yes".equals(r.isAvailable())) {
                System.out.println(r.getInfo());
            }
        }

    }
}
