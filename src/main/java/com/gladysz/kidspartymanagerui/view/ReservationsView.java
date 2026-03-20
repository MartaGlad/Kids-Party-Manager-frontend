package com.gladysz.kidspartymanagerui.view;

import com.gladysz.kidspartymanagerui.dto.ReservationListItemDto;
import com.gladysz.kidspartymanagerui.dto.Status;
import com.gladysz.kidspartymanagerui.service.ReservationService;
import com.gladysz.kidspartymanagerui.view.layout.MainLayout;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@PageTitle("Reservations")
@Route(layout = MainLayout.class)
public class ReservationsView extends VerticalLayout {

    private final List<ReservationListItemDto> allReservations;
    private Grid<ReservationListItemDto> grid;
    private ComboBox<Status> statusFilter;
    private DatePicker fromFilter;
    private DatePicker toFilter;


    public ReservationsView(ReservationService reservationService) {

        allReservations = reservationService.getReservations();

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

        Button addReservation = new Button("Add reservation");

        header.add(title, addReservation);

        add(header);
    }


    private void frameControlPanel() {

        HorizontalLayout panel = new HorizontalLayout();

        statusFilter = new ComboBox<>("Status");
        statusFilter.setItems(Status.values());

        fromFilter = new DatePicker("From date");
        toFilter = new DatePicker("To date");

        Button searchButton = new Button("Search", e -> applyFilters());

        Button clearButton = new Button("Clear", e -> {
            statusFilter.clear();
            fromFilter.clear();
            toFilter.clear();
            grid.setItems(allReservations);
        });

        panel.add(statusFilter, fromFilter, toFilter, searchButton, clearButton);
        panel.setAlignItems(Alignment.END);

        add(panel);
    }


    private void frameMainContent() {

        Div mainContent = new Div();
        mainContent.setSizeFull();

        grid = new Grid<>(ReservationListItemDto.class, false);

        grid.addColumn(ReservationListItemDto::getId).setHeader("Reservation ID");
        grid.addColumn(ReservationListItemDto::getEventPackageName).setHeader("Event package");
        grid.addColumn(ReservationListItemDto::getAnimatorName).setHeader("Animator name");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        grid.addColumn(item ->
                item.getEventDateTime().format(formatter))
                .setHeader("Event date");

        grid.addColumn(ReservationListItemDto::getChildrenCount).setHeader("Children count");
        grid.addColumn(ReservationListItemDto::getStatus).setHeader("Status");

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("pl","PL"));

        grid.addColumn(item ->
                currencyFormatter.format(item.getPrice()))
                .setHeader("Price");

        grid.setItems(allReservations);
        grid.setAllRowsVisible(true);
        grid.setEmptyStateText("No reservations found.");

        mainContent.add(grid);

        add(mainContent);
    }


    private void applyFilters() {
        List<ReservationListItemDto> filterResult = new ArrayList<>(allReservations);

        if (statusFilter.getValue() != null) {
            filterResult = filterResult.stream()
                    .filter(reservation -> reservation.getStatus().equals(statusFilter.getValue()))
                    .collect(Collectors.toList());
        }

        if (fromFilter.getValue() != null) {
            filterResult = filterResult.stream()
                    .filter(reservation -> {
                        LocalDate reservationDate = reservation.getEventDateTime().toLocalDate();

                        return !reservationDate.isBefore(fromFilter.getValue());
                    })
                    .collect(Collectors.toList());
        }

        if (toFilter.getValue() != null) {
            filterResult = filterResult.stream()
                    .filter (reservation -> {

                        LocalDate reservationDate = reservation.getEventDateTime().toLocalDate();

                        return !reservationDate.isAfter(toFilter.getValue());
                    })
                    .collect(Collectors.toList());
        }
        grid.setItems(filterResult);
    }
}

