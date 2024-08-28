import java.time.LocalDate;
import java.util.*;

public class ServiceManager {
    public static void addService(Scanner scanner, List<Service> services, List<AutoPart> parts, List<User> users) {
        System.out.print("Enter service details (ID, Date (yyyy-mm-dd), Client ID, Mechanic ID, Service Type, Cost, Notes): ");
        String serviceDetails = scanner.nextLine();
        String[] serviceData = serviceDetails.split(", ");

        // Check if the Service ID already exists
        for (Service service : services) {
            if (service.getServiceID().equals(serviceData[0])) {
                System.out.println("Service ID " + serviceData[0] + " already exists");
                return;
            }
        }

        System.out.print("Enter replaced parts: ");
        String partsInput = scanner.nextLine();

        List<AutoPart> replacedParts = new ArrayList<>();
        boolean invalidPartFound = false;

        // Handle replaced parts stored as a list
        for (String partID : partsInput.split("\\s*,\\s*")) {
            boolean partFound = false;
            for (AutoPart part : parts) {
                if (part.getPartID().equals(partID)) {
                    replacedParts.add(part);
                    partFound = true;
                    break;
                }
            }
            if (!partFound) {
                System.out.println("Part ID " + partID + " does not exist");
                invalidPartFound = true;
            }
        }

        // If any invalid parts were found, stop the process
        if (invalidPartFound) {
            System.out.println("Invalid part IDs");
            return;
        }

        // Check if the user's account is still active or existing
        Client client = (Client) users.stream()
                .filter(user -> user.getUserID().equals(serviceData[2]) && user instanceof Client)
                .findFirst()
                .orElse(null);

        if (client == null) {
            System.out.println("Client not found");
            return;
        }

        if (!client.isActive()) {
            System.out.println("Client is currently set to inactive and can not request any services");
            return;
        }

        // Check if employee's account is still active or existing
        Mechanic mechanic = (Mechanic) users.stream()
                .filter(user -> user.getUserID().equals(serviceData[3]) && user instanceof Mechanic)
                .findFirst()
                .orElse(null);

        if (mechanic == null) {
            System.out.println("There is no Mechanic has this user ID");
            return;
        }

        if (!mechanic.isActive()) {
            System.out.println("Mechanic is currently set to inactive");
            return;
        }

        double serviceCost = Double.parseDouble(serviceData[5]); // Original service cost

        double discount = 0.0;
        if (client != null) {
            double discountRate = client.getDiscountRate();
            discount = serviceCost * discountRate; // Calculate discount
            serviceCost = serviceCost - discount; // Apply the discount
            client.updateTotalSpending(serviceCost); // Update the client’s total spending
        }

        // Create the service object
        Service newService = new Service(
                serviceData[0], // serviceID
                LocalDate.parse(serviceData[1]), // serviceDate
                serviceData[2], // clientID
                serviceData[3], // mechanicID
                serviceData[4], // serviceType
                replacedParts, // List of replaced parts
                serviceCost, // serviceCost after applying membership discount
                serviceData[6] // notes
        );

        services.add(newService);

        services.sort(Comparator.comparing(Service::getServiceID));

        System.out.println("Applied discount: " + discount);
        System.out.println("Service added successfully");
    }

    public static void removeService(Scanner scanner, List<Service> services) {
        System.out.print("Enter Service ID to remove: ");
        String serviceID = scanner.nextLine();

        // Check if the Service ID exists
        boolean serviceExists = false;
        for (Service service : services) {
            if (service.getServiceID().equals(serviceID)) {
                serviceExists = true;
                break;
            }
        }

        if (!serviceExists) {
            System.out.println("Service ID " + serviceID + " does not exist");
            return;
        }

        services.removeIf(service -> service.getServiceID().equals(serviceID));
        System.out.println("Service removed successfully");
    }
}