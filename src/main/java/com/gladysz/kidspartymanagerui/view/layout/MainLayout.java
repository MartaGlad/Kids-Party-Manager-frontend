package com.gladysz.kidspartymanagerui.view.layout;

import com.gladysz.kidspartymanagerui.view.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

import java.util.Arrays;
import java.util.List;


public class MainLayout extends AppLayout {

    public MainLayout() {

        frameHeader();
        frameDrawer();
    }


    private void frameHeader() {

        H1 title = new H1("Kids Party Manager");
        title.getStyle()
                .set("font-size", "1.125rem")
                .set("font-weight", "bold")
                .set("margin", "0");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), title);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setWidthFull();

        addToNavbar(header);
    }


    private void frameDrawer() {

        VerticalLayout drawer = new VerticalLayout();
        
        RouterLink dashboard = new RouterLink("Dashboard", DashboardView.class);
        RouterLink animators = new RouterLink("Animators", AnimatorsView.class);
        RouterLink orderers = new RouterLink("Orderers", OrderersView.class);
        RouterLink eventPackages = new RouterLink("Event packages", EventPackagesView.class);
        RouterLink reservations = new RouterLink("Reservations", ReservationsView.class);

        List<RouterLink> links = Arrays.asList(dashboard, animators, orderers, eventPackages, reservations);

        for (RouterLink link : links) {
            link.getStyle()
                    .set("display", "block")
                    .set("padding", "12px")
                    .setFontSize("20px")
                    .setFontWeight("bold")
                    .set("color", "#333");
        }

        drawer.add(dashboard, animators, orderers, eventPackages, reservations);

        addToDrawer(drawer);
    }
}
