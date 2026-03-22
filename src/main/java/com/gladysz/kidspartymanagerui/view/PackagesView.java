package com.gladysz.kidspartymanagerui.view;

import com.gladysz.kidspartymanagerui.dto.EventPackageResponseDto;
import com.gladysz.kidspartymanagerui.service.EventPackageService;
import com.gladysz.kidspartymanagerui.view.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.util.Locale;


@PageTitle("Packages")
@Route(layout = MainLayout.class)
public class PackagesView extends VerticalLayout {

    private final EventPackageService eventPackageService;
    private Grid<EventPackageResponseDto> grid;
    private static final Logger LOGGER = LoggerFactory.getLogger(PackagesView.class);


    public PackagesView(EventPackageService eventPackageService) {

        this.eventPackageService = eventPackageService;

        frameHeader();
        frameControlPanel();
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

        Button addEventPackageButton = new Button("Add event package");

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

        grid.addColumn(EventPackageResponseDto::maxChildrenCount).setHeader("Maximum number of children");
        grid.addColumn(EventPackageResponseDto::durationHr).setHeader("Duration hours");

        refreshGrid();
        grid.setAllRowsVisible(true);
        grid.setEmptyStateText("No event packages found.");

        mainContent.add(grid);

        add(mainContent);

    }


    private void refreshGrid() {

        try {
            grid.setItems(eventPackageService.getAllEventPackages());
        } catch (Exception e) {
            Notification.show("Could not load event packages from backend.");
            LOGGER.error("Could not load event packages ", e);
        }

    }

    private void frameControlPanel() {
    }
}
