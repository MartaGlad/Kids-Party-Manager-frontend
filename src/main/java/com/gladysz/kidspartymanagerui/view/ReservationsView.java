package com.gladysz.kidspartymanagerui.view;

import com.gladysz.kidspartymanagerui.dto.ReservationSummaryDto;
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
import java.time.format.DateTimeFormatter;
import java.util.Locale;


@PageTitle("Reservations")
@Route(layout = MainLayout.class)
public class ReservationsView extends VerticalLayout {

    private Grid<ReservationSummaryDto> grid;
    private ComboBox<Status> statusFilter;
    private DatePicker fromFilter;
    private DatePicker toFilter;
    private final ReservationService reservationService;

    public ReservationsView(ReservationService reservationService) {

        this.reservationService = reservationService;

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

