package com.gladysz.kidspartymanagerui.view;

import com.gladysz.kidspartymanagerui.view.layout.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Animators")
@Route(layout = MainLayout.class)
public class AnimatorsView extends VerticalLayout {

    public AnimatorsView() {

        H1 title = new H1("List of animators");
        title.getStyle()
                .set("text-align", "center")
                .set("font-size", "30px")
                .set("font-weight", "bold");

        add(title);
    }
}
