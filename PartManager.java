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
        System.out.println("Part removed successfully!");
    }
}