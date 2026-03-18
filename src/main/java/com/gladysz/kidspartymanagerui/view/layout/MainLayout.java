package com.gladysz.kidspartymanagerui.view.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;


public class MainLayout extends AppLayout {

    public MainLayout() {

        DrawerToggle toggle = new DrawerToggle();

        H1 title = new H1("Kids Party Manager");
        title.getStyle().set("font-size", "1.125rem")
                .set("font-weight", "bold").set("margin", "0");

        SideNav sideNav = new SideNav();
        sideNav.getStyle().set("margin", "var(--vaadin-gap-s)");

        Scroller scroller = new Scroller(sideNav);
        addToDrawer(scroller);

        addToNavbar(toggle, title);
    }
}
