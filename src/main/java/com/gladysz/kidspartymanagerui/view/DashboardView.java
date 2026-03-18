package com.gladysz.kidspartymanagerui.view;

import com.gladysz.kidspartymanagerui.view.layout.MainLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


@Route(value = "", layout = MainLayout.class)
public class DashboardView extends VerticalLayout {

    private final Span welcome  = new Span("Welcome to Kids Party Manager!");

    public DashboardView() {

        welcome.getStyle().set("text-align", "center");
        welcome.getStyle().set("font-size", "30px");
        welcome.getStyle().set("font-weight", "bold");

        HorizontalLayout header = new HorizontalLayout(welcome);
        add(header);
    }
}
