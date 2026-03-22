package com.gladysz.kidspartymanagerui.view;

import com.gladysz.kidspartymanagerui.dto.*;
import com.gladysz.kidspartymanagerui.service.AnimatorService;
import com.gladysz.kidspartymanagerui.service.EventPackageService;
import com.gladysz.kidspartymanagerui.service.OrdererService;
import com.gladysz.kidspartymanagerui.service.ReservationService;
import com.gladysz.kidspartymanagerui.view.layout.MainLayout;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.Collectors;

import com.vaadin.flow.component.notification.Notification;

@PageTitle("Reservations")
@Route(layout = MainLayout.class)
public class ReservationsView extends VerticalLayout {

    private Grid<ReservationSummaryDto> grid;
    private ComboBox<Status> statusFilter;
    private DatePicker fromFilter;
    private DatePicker toFilter;
    private final ReservationService reservationService;
    private final EventPackageService eventPackageService;
    private final AnimatorService animatorService;
    private final OrdererService ordererService;

    public ReservationsView(ReservationService reservationService,
                            EventPackageService eventPackageService,
                            AnimatorService animatorService,
                            OrdererService ordererService) {

        this.reservationService = reservationService;
        this.eventPackageService = eventPackageService;
        this.animatorService = animatorService;
        this.ordererService = ordererService;

        frameHeader();
        frameControlPanel();
        frameMainContent();
    }


    private void frameHeader() {

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setAlignItems(FlexComponent.Alignment.CENTER);

        H1 title = new H1("Reservations");
        title.getStyle()
                .set("text-align", "center")
                .set("font-size", "30px")
                .set("font-weight", "bold");


        Button addReservation = new Button("Add reservation", e -> {

            Dialog dialog = new Dialog();
            dialog.setHeaderTitle("Add reservation");

            IntegerField childrenCountField = new IntegerField("Children count");
            IntegerField birthdayChildAgeField = new IntegerField("Birthday child age");

            DatePicker eventDateField = new DatePicker("Event date");
            TimePicker eventTimeField = new TimePicker("Event time");

            ComboBox<EventPackageResponseDto> packageComboBox = new ComboBox<>("Event package");
            packageComboBox.setItems(eventPackageService.getEventPackages());
            packageComboBox.setItemLabelGenerator(EventPackageResponseDto::name);

            ComboBox<AnimatorResponseDto> animatorComboBox = new ComboBox<>("Animator");
            animatorComboBox.setItems(animatorService.getAnimators().stream()
                    .filter(AnimatorResponseDto::active)
                    .collect(Collectors.toList()));

            animatorComboBox.setItemLabelGenerator(a -> a.firstName() + " " + a.lastName());

            ComboBox<OrdererResponseDto> ordererComboBox = new ComboBox<>("Orderer");
            ordererComboBox.setItems(ordererService.getOrderers());

            ordererComboBox.setItemLabelGenerator(o -> o.firstName() + " " + o.lastName());

            VerticalLayout dialogLayout = new VerticalLayout(
                    packageComboBox, animatorComboBox, ordererComboBox,
                    childrenCountField, birthdayChildAgeField,
                    eventDateField, eventTimeField);

            dialog.add(dialogLayout);

            Button saveButton = new Button("Save", ev -> {

                if (packageComboBox.getValue() == null) {
                    Notification.show("Please choose event package.");
                    return;
                }

                if (animatorComboBox.getValue() == null) {
                    Notification.show("Please choose animator.");
                    return;
                }

                if (ordererComboBox.getValue() == null) {
                    Notification.show("Please choose orderer.");
                    return;
                }

                if (childrenCountField.getValue() == null) {
                    Notification.show("Please enter children count.");
                    return;
                }

                if (birthdayChildAgeField.getValue() == null) {
                    Notification.show("Please enter birthday child age.");
                    return;
                }

                if (eventDateField.getValue() == null) {
                    Notification.show("Please enter event date.");
                    return;
                }

                if (eventTimeField.getValue() == null) {
                    Notification.show("Please enter event time");
                    return;
                }

                LocalDateTime eventDateTime = LocalDateTime.of(eventDateField.getValue(), eventTimeField.getValue());

                reservationService.createReservation(new ReservationCreateDto(
                        packageComboBox.getValue().id(), animatorComboBox.getValue().id(),
                        ordererComboBox.getValue().id(), eventDateTime,
                        childrenCountField.getValue(), birthdayChildAgeField.getValue()));

                Notification.show("Reservation created");

                dialog.close();

                grid.setItems(reservationService.getReservations(null, null, null));
            }
            );

            Button cancelButton = new Button("Cancel", ev -> dialog.close());

            dialog.getFooter().add(saveButton);
            dialog.getFooter().add(cancelButton);
            dialog.open();
        });


        header.add(title, addReservation);

        add(header);
    }


    private void frameControlPanel() {

        HorizontalLayout panel = new HorizontalLayout();

        statusFilter = new ComboBox<>("Status");
        statusFilter.setItems(Status.values());

        fromFilter = new DatePicker("From date");
        toFilter = new DatePicker("To date");

        Button searchButton = new Button("Search", e ->
                grid.setItems(reservationService.getReservations(
                        statusFilter.getValue(), fromFilter.getValue(), toFilter.getValue())));

        Button clearButton = new Button("Clear", e -> {
            statusFilter.clear();
            fromFilter.clear();
            toFilter.clear();
            grid.setItems(reservationService.getReservations(null, null, null));
        });

        panel.add(statusFilter, fromFilter, toFilter, searchButton, clearButton);
        panel.setAlignItems(Alignment.END);

        add(panel);
    }


    private void frameMainContent() {

        Div mainContent = new Div();
        mainContent.setSizeFull();

        grid = new Grid<>(ReservationSummaryDto.class, false);

        grid.addColumn(ReservationSummaryDto::id).setHeader("Reservation ID");
        grid.addColumn(ReservationSummaryDto::eventPackageName).setHeader("Event package");
        grid.addColumn(ReservationSummaryDto::animatorName).setHeader("Animator name");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        grid.addColumn(item ->
                item.eventDateTime().format(formatter))
                .setHeader("Event date");

        grid.addColumn(ReservationSummaryDto::childrenCount).setHeader("Children count");
        grid.addColumn(ReservationSummaryDto::status).setHeader("Status");

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("pl","PL"));

        grid.addColumn(item ->
                currencyFormatter.format(item.price()))
                .setHeader("Price");

        grid.setItems(reservationService.getReservations(null, null, null));
        grid.setAllRowsVisible(true);
        grid.setEmptyStateText("No reservations found.");

        mainContent.add(grid);

        add(mainContent);
    }
}

