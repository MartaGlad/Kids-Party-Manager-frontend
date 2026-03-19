package com.gladysz.kidspartymanagerui.view;

import com.gladysz.kidspartymanagerui.dto.ReservationListItemDto;
import com.gladysz.kidspartymanagerui.dto.Status;
import com.gladysz.kidspartymanagerui.view.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.math.BigDecimal;
import java.time.LocalDateTime;



@PageTitle("Reservations")
@Route(layout = MainLayout.class)
public class ReservationsView extends VerticalLayout {

    public ReservationsView() {

        frameHeader();
        frameControlPanel();
        frameMainContent();
    }


    private void frameHeader() {

        HorizontalLayout header = new HorizontalLayout();

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

        ComboBox<Status> status = new ComboBox<>("Status");
        status.setItems(Status.values());

        DatePicker from = new DatePicker("Start date");
        DatePicker to = new DatePicker("End date");
        Button search = new Button("Search");

        Button clear = new Button("Clear");

        panel.add(status, from, to, search, clear);
        panel.setAlignItems(Alignment.END);

        add(panel);
    }


    private void frameMainContent() {

        HorizontalLayout mainContent = new HorizontalLayout();
        mainContent.setSizeFull();

        Grid<ReservationListItemDto> grid = new Grid<>(ReservationListItemDto.class);
        grid.setColumns("id", "eventPackageName", "animatorName", "eventDateTime", "childrenCount", "status", "price");
        grid.setItems(
                new ReservationListItemDto(1L, "Package1", "Peter Paper",
                        LocalDateTime.now(), 4, Status.NEW, BigDecimal.valueOf(700)),
                new ReservationListItemDto(2L, "Package5", "Alex Cat",
                        LocalDateTime.now(), 6, Status.CONFIRMED, BigDecimal.valueOf(1100)),
                new ReservationListItemDto(3L, "Package2", "Peter Paper",
                        LocalDateTime.now(), 5, Status.NEW, BigDecimal.valueOf(800)));

        grid.setAllRowsVisible(true);
        mainContent.add(grid);
        add(mainContent);
    }
}

