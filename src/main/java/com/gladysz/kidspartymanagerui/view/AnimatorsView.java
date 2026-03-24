package com.gladysz.kidspartymanagerui.view;

import com.gladysz.kidspartymanagerui.dto.AnimatorCreateDto;
import com.gladysz.kidspartymanagerui.dto.AnimatorResponseDto;
import com.gladysz.kidspartymanagerui.service.AnimatorService;
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

@PageTitle("Animators")
@Route(layout = MainLayout.class)
public class AnimatorsView extends VerticalLayout {

    private final AnimatorService animatorService;
    private Grid<AnimatorResponseDto> grid;
    private static final Logger LOGGER = LoggerFactory.getLogger(AnimatorsView.class);


    public AnimatorsView(AnimatorService animatorService) {

        this.animatorService = animatorService;

        frameHeader();
        frameMainContent();
    }


    private void frameHeader() {

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(FlexComponent.Alignment.CENTER);

        H1 title = new H1("Animators");
        title.getStyle()
                .set("text-align", "center")
                .set("font-size", "30px")
                .set("font-weight", "bold");

        Button addAnimatorButton = new Button("Add animator",
                e -> showAddAnimatorDialog());

        header.add(title, addAnimatorButton);

        add(header);
    }


    private void frameMainContent() {

        Div mainContent = new Div();
        mainContent.setSizeFull();

        grid = new Grid<>(AnimatorResponseDto.class, false);

        grid.addColumn(AnimatorResponseDto::id).setHeader("Animator ID");
        grid.addColumn(AnimatorResponseDto::firstName).setHeader("First name");
        grid.addColumn(AnimatorResponseDto::lastName).setHeader("Last name");
        grid.addColumn(AnimatorResponseDto::email).setHeader("Email");
        grid.addColumn(AnimatorResponseDto::phone).setHeader("Phone");
        grid.addColumn(AnimatorResponseDto::active).setHeader("Active");

        grid.addComponentColumn(item -> new Button("Edit",
                        e -> showEditAnimatorDialog(item)))
                .setHeader("Edit");

        grid.addComponentColumn(item -> new Button("Delete",
                        ev -> showDeleteConfirmationDialog(item)))
                .setHeader("Delete");

        refreshGrid();
        grid.setAllRowsVisible(true);
        grid.setEmptyStateText("No animators found.");

        mainContent.add(grid);

        add(mainContent);
    }


    private void showDeleteConfirmationDialog(AnimatorResponseDto item) {

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Confirm deletion");
        dialog.setDraggable(true);

        Span ask = new Span("Do you really want to delete animator: " + item.firstName()
                + " " + item.lastName() + "?");

        VerticalLayout dialogLayout = new VerticalLayout(ask);
        dialog.add(dialogLayout);

        Button cancelButton = new Button("Cancel", e -> dialog.close());

        Button deleteButton = new Button("Delete", ev -> {
            try {
                animatorService.deleteAnimator(item.id());
                Notification.show("Animator deleted.");
                dialog.close();
                refreshGrid();
            } catch (Exception e) {
                Notification.show("Could not delete animator.");
                LOGGER.error("Could not delete animator ", e);
            }
        });
        dialog.getFooter().add(cancelButton, deleteButton);
        dialog.open();
    }


    private void refreshGrid() {

        try {
            grid.setItems(animatorService.getAllAnimators());
        } catch (Exception e) {
            Notification.show("Could not load animators from backend.");
            LOGGER.error("Could not load animators ", e);
        }
    }


    private void showAddAnimatorDialog() {

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Add animator");
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
                animatorService.createAnimator(new AnimatorCreateDto(
                        firstNameField.getValue(), lastNameField.getValue(),
                        emailField.getValue(), phoneField.getValue()
                        )
                );
                Notification.show("Animator created.");
                dialog.close();
                refreshGrid();

            } catch (Exception e) {
                Notification.show("Could not create animator.");
                LOGGER.error("Could not create animator ", e);
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


    private void showEditAnimatorDialog(AnimatorResponseDto animatorResponseDto) {

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Edit animator");
        dialog.setDraggable(true);

        TextField firstNameField = new TextField("First name");
        firstNameField.setValue(animatorResponseDto.firstName());
        firstNameField.setRequired(true);

        TextField lastNameField = new TextField("Last name");
        lastNameField.setValue(animatorResponseDto.lastName());
        lastNameField.setRequired(true);

        TextField emailField = new TextField("Email");
        emailField.setValue(animatorResponseDto.email());
        emailField.setRequired(true);
        emailField.setPattern("^[A-Za-z0-9+_.-]+@(.+)$");
        emailField.setErrorMessage("Please enter a valid email address");

        TextField phoneField = new TextField("Phone");
        phoneField.setValue(animatorResponseDto.phone());
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
                animatorService.updateAnimator(animatorResponseDto.id(),
                        new AnimatorCreateDto(
                                firstNameField.getValue(), lastNameField.getValue(),
                                emailField.getValue(), phoneField.getValue())
                );
                Notification.show("Animator updated.");
                dialog.close();
                refreshGrid();

            } catch (Exception e) {
                Notification.show("Could not update animator.");
                LOGGER.error("Could not update animator ", e);
            }
        }
        );

        Button cancelButton = new Button("Cancel", ev -> dialog.close());

        dialog.getFooter().add(saveButton);
        dialog.getFooter().add(cancelButton);
        dialog.open();
    }
}
