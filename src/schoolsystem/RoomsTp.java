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
    private boolean booked = false;

    public Room(int roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public String getInfo() {
        return "Room " + roomId + ": " + name + " (" + (booked ? "Booked" : "Available") + ")";
    }

    public String isAvailable() {
        return booked ? "no" : "yes";
    }

    public int getRoomId() {
        return roomId;
    }

    public String getName() {
        return name;
    }
    
    public boolean isBooked() {
        return booked;
    }
    
    public void book() {
        booked = true;
    }
    
    public void unbook() {
        booked = false;
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

        System.out.println("1 - Book room\n2 - Unbook room\n3 - Back");
        int choice = SchoolSystem.choice(1, 3);
        switch (choice) {
            case 1 -> bookRoom();
            case 2 -> unbookRoom();
            case 3 -> SchoolSystem.teacher.teacherAdminMenu();
        }
    }
    
    private static void bookRoom() {
        System.out.print("Enter room ID to book: ");
        try {
            int roomId = Integer.parseInt(s.nextLine().trim());
            Room room = school.getRoom(roomId);
            if (room != null && "yes".equals(room.isAvailable())) {
                room.book();
                System.out.println("Room " + roomId + " booked successfully.");
            } else {
                System.out.println("Room not available or invalid ID.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
        menuBooking();
    }
    
    private static void unbookRoom() {
        System.out.print("Enter room ID to unbook: ");
        try {
            int roomId = Integer.parseInt(s.nextLine().trim());
            Room room = school.getRoom(roomId);
            if (room != null && room.isBooked()) {
                room.unbook();
                System.out.println("Room " + roomId + " unbooked successfully.");
            } else {
                System.out.println("Room not booked or invalid ID.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
        menuBooking();
    }
}
