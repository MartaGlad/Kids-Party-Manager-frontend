package com.gladysz.kidspartymanagerui.view;

import com.gladysz.kidspartymanagerui.dto.AnimatorResponseDto;
import com.gladysz.kidspartymanagerui.dto.Status;
import com.gladysz.kidspartymanagerui.service.AnimatorService;
import com.gladysz.kidspartymanagerui.service.EventPackageService;
import com.gladysz.kidspartymanagerui.service.OrdererService;
import com.gladysz.kidspartymanagerui.service.ReservationService;
import com.gladysz.kidspartymanagerui.view.layout.MainLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@PageTitle("Kids Party Manager")
@Route(value = "", layout = MainLayout.class)
public class DashboardView extends VerticalLayout {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardView.class);


    public DashboardView(
            AnimatorService animatorService,
            OrdererService ordererService,
            EventPackageService eventPackageService,
            ReservationService reservationService) {

        H2 welcome = new H2("Welcome to Kids Party Manager!");
        welcome.getStyle().set("font-weight", "bold");

        Span reservationsOverviewSpan = new Span("Reservations overview");
        reservationsOverviewSpan.getStyle()
                .set("font-size", "20px")
                .set("font-weight", "bold");

        Span resourcesSpan = new Span("Resources");
        resourcesSpan.getStyle()
                .set("font-size", "20px")
                .set("font-weight", "bold");

        int reservationsCount = 0, newReservationsCount = 0, confirmedReservationsCount = 0,
            cancelledReservationsCount = 0, animatorsCount = 0, activeAnimatorsCount = 0,
            eventPackagesCount = 0, orderersCount = 0;

        try {
            reservationsCount = reservationService.getReservations(null, null, null).size();
            newReservationsCount = reservationService.getReservations(Status.NEW, null, null).size();
            confirmedReservationsCount = reservationService.getReservations(Status.CONFIRMED, null, null).size();
            cancelledReservationsCount = reservationService.getReservations(Status.CANCELLED, null, null).size();
            animatorsCount = animatorService.getAllAnimators().size();
            activeAnimatorsCount = animatorService.getAllAnimators()
                    .stream()
                    .filter(AnimatorResponseDto::active)
                    .toList().size();
            eventPackagesCount = eventPackageService.getAllEventPackages().size();
            orderersCount = ordererService.getAllOrderers().size();

        } catch (Exception e) {
            Notification.show("Could not load data from backend.");
            LOGGER.error("Could not load dashboard statistics ", e);
        }

        VerticalLayout reservationsLayout = createStatCard("Reservations", String.valueOf(reservationsCount));
        VerticalLayout newReservationsLayout = createStatCard("New reservations", String.valueOf(newReservationsCount));
        VerticalLayout confirmedReservationsLayout = createStatCard("Confirmed reservations", String.valueOf(confirmedReservationsCount));
        VerticalLayout cancelledReservationsLayout = createStatCard("Cancelled reservations", String.valueOf(cancelledReservationsCount));

        VerticalLayout reservationsStatisticsLayout = new VerticalLayout();
        reservationsStatisticsLayout.add(reservationsOverviewSpan, reservationsLayout, newReservationsLayout,
                confirmedReservationsLayout, cancelledReservationsLayout);


        VerticalLayout animatorsLayout = createStatCard("Animators", String.valueOf(animatorsCount));
        VerticalLayout activeAnimatorsLayout = createStatCard("Active animators", String.valueOf(activeAnimatorsCount));
        VerticalLayout packagesLayout = createStatCard("Event packages", String.valueOf(eventPackagesCount));
        VerticalLayout orderersLayout = createStatCard("Orderers", String.valueOf(orderersCount));

        VerticalLayout resourcesStatisticsLayout = new VerticalLayout();
        resourcesStatisticsLayout.add(resourcesSpan, animatorsLayout, activeAnimatorsLayout, packagesLayout, orderersLayout);

        HorizontalLayout stats = new HorizontalLayout();
        stats.setWidthFull();

        stats.add(reservationsStatisticsLayout, resourcesStatisticsLayout);

        add(welcome, stats);
    }


    private VerticalLayout createStatCard(String label, String value) {

        Span spanLabel = new Span(label);
        H3 headerValue = new H3(value);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setAlignItems(Alignment.CENTER);
        verticalLayout.getStyle()
                .set("border", "1px solid #ddd")
                .set("border-radius", "15px")
                .set("min-width", "180px")
                .set("background-color", "#f5f5f5");

        verticalLayout.add(spanLabel, headerValue);

        return verticalLayout;
    }
}
