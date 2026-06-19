package mapper;

import com.campuslf.models.ItemReport;
import model.Item;

public class ItemMapper {
    public static Item toItem(ItemReport report) {

        Item item = new Item();

        item.setId(report.getReportId());
        item.setName(report.getItemName());
        item.setLocation(report.getLocationFound());

        item.setDate(
                report.getDatePosted() != null
                        ? report.getDatePosted().toString()
                        : ""
        );

        item.setStudentId(report.getFinderStudentId());
        item.setContactNumber(report.getFinderContactNum());
        item.setImagePath(report.getImageUrl());

        item.setColor(cleanDescription(report.getDescription()));

        item.setCategory(categoryName(report.getCategoryId()));

        item.setReporterName(finderName(report.getDescription()));

        if ("Claimed".equalsIgnoreCase(report.getReportStatus())) {
            item.setStatus(Item.Status.FOUND);
        } else {
            item.setStatus(Item.Status.LOST);
        }

        return item;
    }

    private static String categoryName(int categoryId) {
        return switch (categoryId) {
            case 1 -> "Electronics";
            case 2 -> "Bags & Wallets";
            case 3 -> "IDs & Documents";
            case 4 -> "Clothing";
            default -> "Others";
        };
    }

    private static String finderName(String description) {
        if (description == null || !description.startsWith("Finder:")) {
            return "";
        }

        int lineEnd = description.indexOf(System.lineSeparator());
        if (lineEnd < 0) {
            lineEnd = description.indexOf('\n');
        }

        String firstLine = lineEnd >= 0 ? description.substring(0, lineEnd) : description;
        return firstLine.replaceFirst("Finder:\\s*", "").trim();
    }

    private static String cleanDescription(String description) {
        if (description == null || !description.startsWith("Finder:")) {
            return description;
        }

        int lineEnd = description.indexOf(System.lineSeparator());
        if (lineEnd < 0) {
            lineEnd = description.indexOf('\n');
        }

        return lineEnd >= 0 ? description.substring(lineEnd).trim() : "";
    }
}
