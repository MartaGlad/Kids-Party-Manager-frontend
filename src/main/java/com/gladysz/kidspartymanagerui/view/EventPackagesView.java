package com.gladysz.kidspartymanagerui.view;

import com.gladysz.kidspartymanagerui.dto.*;
import com.gladysz.kidspartymanagerui.service.EventPackageService;
import com.gladysz.kidspartymanagerui.view.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;


@PageTitle("Event packages")
@Route(layout = MainLayout.class)
public class EventPackagesView extends VerticalLayout {

    private final EventPackageService eventPackageService;
    private Grid<EventPackageResponseDto> grid;
    private static final Logger LOGGER = LoggerFactory.getLogger(EventPackagesView.class);


    public EventPackagesView(EventPackageService eventPackageService) {

        this.eventPackageService = eventPackageService;

        frameHeader();
        frameMainContent();
    }


    public void frameHeader() {

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(FlexComponent.Alignment.CENTER);

        H1 title = new H1("Event packages");
        title.getStyle()
                .set("text-align", "center")
                .set("font-size", "30px")
                .set("font-weight", "bold");

        Button addEventPackageButton = new Button("Add event package",
                e -> showAddEventPackageDialog());

        header.add(title, addEventPackageButton);

        add(header);
    }


    public void frameMainContent() {

        Div mainContent = new Div();
        mainContent.setSizeFull();

        grid = new Grid<>(EventPackageResponseDto.class, false);

        grid.addColumn(EventPackageResponseDto::id).setHeader("Event package ID");
        grid.addColumn(EventPackageResponseDto::name).setHeader("Name");
        grid.addColumn(EventPackageResponseDto::description).setHeader("Description");

        NumberFormat currencyFormatter = NumberFormat
                .getCurrencyInstance(Locale.of("pl","PL"));
        grid.addColumn(item ->
                currencyFormatter.format(item.basePrice())).setHeader("Base price");

        grid.addColumn(EventPackageResponseDto::maxChildrenCount)
                .setHeader("Max children");
        grid.addColumn(EventPackageResponseDto::durationHr).setHeader("Duration (h)");

        grid.addComponentColumn(item -> new Button("Edit",
                        e -> showEditEventPackageDialog(item)))
                .setHeader("Edit data");

        grid.addComponentColumn(item -> new Button("Delete",
                        ev -> showDeleteConfirmationDialog(item)))
                .setHeader("Delete data");

        refreshGrid();
        grid.setAllRowsVisible(true);
        grid.setEmptyStateText("No event packages found.");

        mainContent.add(grid);

        add(mainContent);
    }


    private void showDeleteConfirmationDialog(EventPackageResponseDto item) {

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Confirm deletion");
        dialog.setDraggable(true);

        Span ask = new Span("Do you really want to delete package: " + item.name() + "?");

        VerticalLayout dialogLayout = new VerticalLayout(ask);
        dialog.add(dialogLayout);

        Button cancelButton = new Button("Cancel", e -> dialog.close());

        Button deleteButton = new Button("Delete", ev -> {
            try {
                eventPackageService.deleteEventPackage(item.id());
                Notification.show("Event package deleted.");
                dialog.close();
                refreshGrid();
            } catch (Exception e) {
                Notification.show("Could not delete event package.");
                LOGGER.error("Could not delete event package ", e);
            }
        });
        dialog.getFooter().add(cancelButton, deleteButton);
        dialog.open();
    }


    private void refreshGrid() {

        try {
            grid.setItems(eventPackageService.getAllEventPackages());
        } catch (Exception e) {
            Notification.show("Could not load event packages from backend.");
            LOGGER.error("Could not load event packages ", e);
        }
    }


    private void showAddEventPackageDialog() {

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Add event package");
        dialog.setDraggable(true);

        TextField nameField = new TextField("Name");
        nameField.setRequired(true);

        TextField descriptionField = new TextField("Description");
        descriptionField.setRequired(true);

        TextField priceField = new TextField("Price");
        priceField.setRequired(true);

        IntegerField maxChildrenCountField = new IntegerField("Maximum number of children");
        maxChildrenCountField.setRequired(true);
        maxChildrenCountField.setMin(1);

        IntegerField durationInHrField = new IntegerField("Duration hours");
        durationInHrField.setRequired(true);
        durationInHrField.setMin(1);

        VerticalLayout dialogLayout = new VerticalLayout(
               nameField, descriptionField, priceField,
                maxChildrenCountField, durationInHrField
        );

        dialog.add(dialogLayout);

        Button saveButton = new Button("Save", ev -> {

            if (!validateInput(nameField, descriptionField, priceField, maxChildrenCountField, durationInHrField)) {
                return;
            }
            try {
                eventPackageService.createEventPackage(new EventPackageCreateDto(
                        nameField.getValue(), descriptionField.getValue(),
                        new BigDecimal(priceField.getValue()),
                        maxChildrenCountField.getValue(), durationInHrField.getValue())
                );
                Notification.show("Event package created.");
                dialog.close();
                refreshGrid();

            } catch (Exception e) {
                Notification.show("Could not create event package.");
                LOGGER.error("Could not create event package ", e);
            }
        }
        );

        Button cancelButton = new Button("Cancel", ev -> dialog.close());

        dialog.getFooter().add(saveButton);
        dialog.getFooter().add(cancelButton);
        dialog.open();
    }


    private boolean validateInput(TextField nameField, TextField descriptionField,
                                   TextField priceField, IntegerField maxChildrenCountField,
                                   IntegerField durationInHrField
    ) {

        if (nameField.getValue().isBlank()) {
            Notification.show("Please enter a name.");
            return false;
        }

        if (descriptionField.getValue().isBlank()) {
            Notification.show("Please enter a description.");
            return false;
        }

        if (priceField.getValue().isBlank()) {
            Notification.show("Please enter a price.");
            return false;
        }

        try {

            new BigDecimal(priceField.getValue());

        } catch (NumberFormatException e) {

            Notification.show("Base price must be a number!");
            LOGGER.warn("Could not parse value ", e);
            priceField.clear();

            return false;
        }

        if (new BigDecimal(priceField.getValue()).compareTo(BigDecimal.valueOf(500.00)) < 0) {
            Notification.show("Base price must be at least 500.00 zl.");
            return false;
        }

        if (maxChildrenCountField.getValue() == null) {
            Notification.show("Please enter the maximum number of children.");
            return false;
        }

        if (maxChildrenCountField.getValue() < 1) {
            Notification.show("Maximum number of children must be greater than 0.");
            return false;
        }

        if (durationInHrField.getValue() == null) {
            Notification.show("Please enter the duration hours.");
            return false;
        }

        if (durationInHrField.getValue() <= 0) {
            Notification.show("Duration must be greater than 0.");
            return false;
        }

        return true;
    }


    private void showEditEventPackageDialog(EventPackageResponseDto eventPackageResponseDto) {

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Edit event package");
        dialog.setDraggable(true);

        TextField nameField = new TextField("Name");
        nameField.setValue(eventPackageResponseDto.name());

        TextField descriptionField = new TextField("Description");
        descriptionField.setValue(eventPackageResponseDto.description());

        TextField priceField = new TextField("Price");
        priceField.setValue(eventPackageResponseDto.basePrice().toString());

        IntegerField maxChildrenCountField = new IntegerField("Maximum number of children");
        maxChildrenCountField.setValue(eventPackageResponseDto.maxChildrenCount());

        IntegerField durationInHrField = new IntegerField("Duration hours");
        durationInHrField.setValue(eventPackageResponseDto.durationHr());

        VerticalLayout dialogLayout = new VerticalLayout(
                nameField, descriptionField, priceField,
                maxChildrenCountField, durationInHrField);

        dialog.add(dialogLayout);

        Button saveButton = new Button("Save", ev -> {

            if (!validateInput(nameField, descriptionField, priceField, maxChildrenCountField, durationInHrField)) {
                return;
            }
            try {
                eventPackageService.updateEventPackage(eventPackageResponseDto.id(),
                        new EventPackageCreateDto(
                        nameField.getValue(), descriptionField.getValue(),
                        new BigDecimal(priceField.getValue()),
                        maxChildrenCountField.getValue(), durationInHrField.getValue())
                );
                Notification.show("Event package updated.");
                dialog.close();
                refreshGrid();

            } catch (Exception e) {
                Notification.show("Could not update event package.");
                LOGGER.error("Could not update event package ", e);
            }
        }
        );

        Button cancelButton = new Button("Cancel", ev -> dialog.close());

        dialog.getFooter().add(saveButton);
        dialog.getFooter().add(cancelButton);
        dialog.open();
    }
}
