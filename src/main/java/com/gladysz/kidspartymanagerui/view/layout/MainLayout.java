package com.gladysz.kidspartymanagerui.view.layout;

import com.gladysz.kidspartymanagerui.view.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;


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
        drawer.add(
                new RouterLink("Dashboard", DashboardView.class),
                new RouterLink("Animators", AnimatorsView.class),
                new RouterLink("Orderers", OrderersView.class),
                new RouterLink("Event packages", EventPackagesView.class),
                new RouterLink("Reservations", ReservationsView.class)
        );
        drawer.getStyle().set("margin", "var(--vaadin-gap-s)");
        addToDrawer(drawer);
    }
}
