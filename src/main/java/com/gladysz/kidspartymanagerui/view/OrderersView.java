package com.gladysz.kidspartymanagerui.view;

import com.gladysz.kidspartymanagerui.dto.OrdererCreateDto;
import com.gladysz.kidspartymanagerui.dto.OrdererResponseDto;
import com.gladysz.kidspartymanagerui.service.OrdererService;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PageTitle("Orderers")
@Route(layout = MainLayout.class)
public class OrderersView extends VerticalLayout {

    private final OrdererService ordererService;
    private Grid<OrdererResponseDto> grid;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderersView.class);


    public OrderersView(OrdererService ordererService) {

        this.ordererService = ordererService;

        frameHeader();
        frameMainContent();
    }


    private void frameHeader() {

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(FlexComponent.Alignment.CENTER);

        H1 title = new H1("Orderers");
        title.getStyle()
                .set("text-align", "center")
                .set("font-size", "30px")
                .set("font-weight", "bold");

        Button addOrdererButton = new Button("Add orderer",
                e -> showAddOrdererDialog());

        header.add(title, addOrdererButton);

        add(header);
    }


    private void frameMainContent() {

        Div mainContent = new Div();
        mainContent.setSizeFull();

        grid = new Grid<>(OrdererResponseDto.class, false);

        grid.addColumn(OrdererResponseDto::id).setHeader("Orderer ID");
        grid.addColumn(OrdererResponseDto::firstName).setHeader("First name");
        grid.addColumn(OrdererResponseDto::lastName).setHeader("Last name");
        grid.addColumn(OrdererResponseDto::email).setHeader("Email");
        grid.addColumn(OrdererResponseDto::phone).setHeader("Phone");

        grid.addComponentColumn(item -> new Button("Edit",
                        e -> showEditOrdererDialog(item)))
                .setHeader("Edit");

        grid.addComponentColumn(item -> new Button("Delete",
                        ev -> showDeleteConfirmationDialog(item)))
                .setHeader("Delete");

        refreshGrid();
        grid.setAllRowsVisible(true);
        grid.setEmptyStateText("No orderers found.");

        mainContent.add(grid);

        add(mainContent);
    }


    private void showDeleteConfirmationDialog(OrdererResponseDto item) {

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Confirm deletion");
        dialog.setDraggable(true);

        Span ask = new Span("Do you really want to delete orderer: " + item.firstName()
                + " " + item.lastName() + "?");

        VerticalLayout dialogLayout = new VerticalLayout(ask);
        dialog.add(dialogLayout);

        Button cancelButton = new Button("Cancel", e -> dialog.close());

        Button deleteButton = new Button("Delete", ev -> {
            try {
                ordererService.deleteOrderer(item.id());
                Notification.show("Orderer deleted.");
                dialog.close();
                refreshGrid();
            } catch (Exception e) {
                Notification.show("Could not delete orderer.");
                LOGGER.error("Could not delete orderer ", e);
            }
        });
        dialog.getFooter().add(cancelButton, deleteButton);
        dialog.open();
    }


    private void refreshGrid() {

        try {
            grid.setItems(ordererService.getAllOrderers());
        } catch (Exception e) {
            Notification.show("Could not load orderers from backend.");
            LOGGER.error("Could not load orderers ", e);
        }
    }


    private void showAddOrdererDialog() {

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Add orderer");
        dialog.setDraggable(true);

        TextField firstNameField = new TextField("First name");
        firstNameField.setRequired(true);

        TextField lastNameField = new TextField("Last name");
        lastNameField.setRequired(true);

        TextField emailField = new TextField("Email");
        emailField.setRequired(true);
        emailField.setPattern("^[A-Za-z0-9+_.-]+@(.+)$");
        emailField.setErrorMessage("Please enter a valid email address");

        TextField phoneField = new TextField("Phone");
        phoneField.setRequired(true);

        VerticalLayout dialogLayout = new VerticalLayout(
                firstNameField, lastNameField, emailField, phoneField
        );

        dialog.add(dialogLayout);

        Button saveButton = new Button("Save", ev -> {

            if (!validateInput(firstNameField, lastNameField, emailField, phoneField)) {
                return;
            }
            try {
                ordererService.createOrderer(new OrdererCreateDto(
                                firstNameField.getValue(), lastNameField.getValue(),
                                emailField.getValue(), phoneField.getValue()
                        )
                );
                Notification.show("Orderer created.");
                dialog.close();
                refreshGrid();

            } catch (Exception e) {
                Notification.show("Could not create orderer.");
                LOGGER.error("Could not create orderer ", e);
            }
        }
        );

        Button cancelButton = new Button("Cancel", ev -> dialog.close());

        dialog.getFooter().add(saveButton);
        dialog.getFooter().add(cancelButton);
        dialog.open();
    }


    private boolean validateInput(TextField firstNameField, TextField lastnameField,
                                  TextField emailField, TextField phoneField
    ) {

        if (firstNameField.getValue().isBlank()) {
            Notification.show("Please enter first name.");
            return false;
        }

        if (lastnameField.getValue().isBlank()) {
            Notification.show("Please enter last name.");
            return false;
        }

        if (emailField.getValue().isBlank()) {
            Notification.show("Please enter email.");
            return false;
        }

        if (phoneField.getValue().isBlank()) {
            Notification.show("Please enter phone number.");
            return false;
        }

        return true;
    }


    private void showEditOrdererDialog(OrdererResponseDto OrdererResponseDto) {

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Edit orderer");
        dialog.setDraggable(true);

        TextField firstNameField = new TextField("First name");
        firstNameField.setValue(OrdererResponseDto.firstName());
        firstNameField.setRequired(true);

        TextField lastNameField = new TextField("Last name");
        lastNameField.setValue(OrdererResponseDto.lastName());
        lastNameField.setRequired(true);

        TextField emailField = new TextField("Email");
        emailField.setValue(OrdererResponseDto.email());
        emailField.setRequired(true);
        emailField.setPattern("^[A-Za-z0-9+_.-]+@(.+)$");
        emailField.setErrorMessage("Please enter a valid email address");

        TextField phoneField = new TextField("Phone");
        phoneField.setValue(OrdererResponseDto.phone());
        phoneField.setRequired(true);

        VerticalLayout dialogLayout = new VerticalLayout(
                firstNameField, lastNameField, emailField, phoneField
        );

        dialog.add(dialogLayout);

        Button saveButton = new Button("Save", ev -> {

            if (!validateInput(firstNameField, lastNameField, emailField, phoneField)) {
                return;
            }
            try {
                ordererService.updateOrderer(OrdererResponseDto.id(),
                        new OrdererCreateDto(
                                firstNameField.getValue(), lastNameField.getValue(),
                                emailField.getValue(), phoneField.getValue())
                );
                Notification.show("Orderer updated.");
                dialog.close();
                refreshGrid();

            } catch (Exception e) {
                Notification.show("Could not update orderer.");
                LOGGER.error("Could not update orderer ", e);
            }
        }
        );

        Button cancelButton = new Button("Cancel", ev -> dialog.close());

        dialog.getFooter().add(saveButton);
        dialog.getFooter().add(cancelButton);
        dialog.open();
    }
}

