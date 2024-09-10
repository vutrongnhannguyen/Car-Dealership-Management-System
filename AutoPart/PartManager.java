package AutoPart;

import FileHandler.FileManager;

import java.util.*;

public class PartManager {
    public static void addPart(Scanner scanner, List<AutoPart> parts) {
        System.out.print("Enter part details (Part ID, Name, Manufacturer, Part Number, Condition, Warranty, Cost, Notes): ");
        String partDetails = scanner.nextLine();
        String[] partData = partDetails.split(", ");

        // Check if the Part ID already exists
        for (AutoPart part : parts) {
            if (part.getPartID().equals(partData[0])) {
                System.out.println("Part ID " + partData[0] + " already exists. Please use a unique Part ID");
                return;
            }
        }

        AutoPart newPart = new AutoPart(
                partData[0], // partID
                partData[1], // name
                partData[2], // manufacturer
                partData[3], // partNumber
                partData[4], // condition
                partData[5], // warranty
                Double.parseDouble(partData[6]), // cost
                partData[7] // notes
        );

        parts.add(newPart);

        // Sort the parts by their ID
        parts.sort(Comparator.comparing(AutoPart::getPartID));

        System.out.println("Part added successfully");
    }

    public static void removePart(Scanner scanner, List<AutoPart> parts) {
        System.out.print("Enter Part ID to remove: ");
        String partID = scanner.nextLine();

        // Check if the Part ID exists
        boolean partExists = false;
        for (AutoPart part : parts) {
            if (part.getPartID().equals(partID)) {
                partExists = true;
                break;
            }
        }

        if (!partExists) {
            System.out.println("Error: Part ID " + partID + " does not exist");
            return;
        }

        parts.removeIf(part -> part.getPartID().equals(partID));
        System.out.println("Part removed successfully");
    }

    public static void updatePart(Scanner scanner, List<AutoPart> parts) {
        System.out.print("Enter the Part ID to update: ");
        String partID = scanner.nextLine();

        // Check if the Part ID exists
        AutoPart partToUpdate = null;
        for (AutoPart part : parts) {
            if (part.getPartID().equals(partID)) {
                partToUpdate = part;
                break;
            }
        }

        if (partToUpdate == null) {
            System.out.println("Error: Part ID " + partID + " does not exist");
            return;
        }

        boolean keepUpdating = true;

        while (keepUpdating) {
            // Menu for updating parts
            System.out.println("\nWhat would you like to update?");
            System.out.println("1. Name");
            System.out.println("2. Manufacturer");
            System.out.println("3. Part Number");
            System.out.println("4. Condition");
            System.out.println("5. Warranty");
            System.out.println("6. Notes");
            System.out.println("7. Exit");


            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();
                    partToUpdate.setName(newName);
                    System.out.println("Name updated successfully");
                }
                case 2 -> {
                    System.out.print("Enter new manufacturer: ");
                    String newManufacturer = scanner.nextLine();
                    partToUpdate.setManufacturer(newManufacturer);
                    System.out.println("Manufacturer updated successfully");
                }
                case 3 -> {
                    System.out.print("Enter new part number: ");
                    String newPartNumber = scanner.nextLine();
                    partToUpdate.setPartNumber(newPartNumber);
                    System.out.println("Part number updated successfully");
                }
                case 4 -> {
                    System.out.print("Enter new condition: ");
                    String newCondition = scanner.nextLine();
                    partToUpdate.setCondition(newCondition);
                    System.out.println("Condition updated successfully");
                }
                case 5 -> {
                    System.out.print("Enter new warranty: ");
                    String newWarranty = scanner.nextLine();
                    partToUpdate.setWarranty(newWarranty);
                    System.out.println("Warranty updated successfully");
                }
                case 6 -> {
                    System.out.print("Enter new notes: ");
                    String newNotes = scanner.nextLine();
                    partToUpdate.setNotes(newNotes);
                    System.out.println("Notes updated successfully");
                }
                case 7 -> {
                    keepUpdating = false;
                    System.out.println("Exiting.....");
                }
                default -> System.out.println("Invalid option");
            }
        }

        // Write the updated parts data to the CSV file
        FileManager.writeParts(parts, "Database/parts.csv");
        System.out.println("Part data saved successfully");
    }

    public static void readPartByID(Scanner scanner, List<AutoPart> parts) {
        System.out.print("Enter the Part ID to access: ");
        String partID = scanner.nextLine();

        // Check Auto Part ID
        AutoPart partToRead = null;
        for (AutoPart part : parts) {
            if (part.getPartID().equals(partID)) {
                partToRead = part;
                break;
            }
        }

        if (partToRead == null) {
            System.out.println("Part ID " + partID + " does not exist");
        } else {
            System.out.println(partToRead);
        }
    }
}